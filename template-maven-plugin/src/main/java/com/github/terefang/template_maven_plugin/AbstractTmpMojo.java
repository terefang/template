package com.github.terefang.template_maven_plugin;

import com.github.terefang.jmelange.gfx.GfxInterface;
import com.github.terefang.jmelange.gfx.impl.PixelImage;
import com.github.terefang.jmelange.gfx.impl.SvgImage;
import com.github.terefang.template_maven_plugin.util.ContextHelper;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.github.terefang.template_maven_plugin.util.ProcessingUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractTmpMojo extends AbstractMojo
{
    public static final String[] DEFAULT_DATA_FILTER = {
            /* ContextUtil.EXTENSIONS */
            "**/*.props",
            "**/*.properties",
            "**/*.yaml",
            "**/*.yml",
            "**/*.json",
            "**/*.hson",
            "**/*.hjson",
            "**/*.tml",
            "**/*.toml",
            "**/*.ini",
            "**/*.pdx",
            "**/*.pdata",
            "**/*.sqlite.csv",
            "**/*.list",
            "**/*.scsv",
            "**/*.csv",
            "**/*.tsv",
            "**/*.txt" };
    public static final String[] DEFAULT_SINGLE_DATA_FILTER = { "**/*.props", "**/*.hson", "**/*.pdata" };

    @Parameter(defaultValue = "text")
    protected String outputType;

    @Parameter(defaultValue = "")
    List<String> includePath;

    List<String> arguments;

    /**
     * additional global context
     */
    @Parameter(defaultValue = "${project.build.directory}/standard.yaml")
    protected File additionalContext;

    /**
     * additional global context key
     */
    @Parameter(defaultValue = "context")
    protected String additionalContextRoot;

    /**
     * additional global variable
     */
    @Parameter(defaultValue = "")
    protected String additionalVariables;

    @Parameter(defaultValue = "")
    protected String contextRoot;

    @Parameter
    protected String jdbcUrl;
    @Parameter
    protected String jdbcUsername;
    @Parameter
    protected String jdbcPassword;
    @Parameter
    protected String jdbcDriver;

    @SneakyThrows
    public void prepareAdditionalContext(Map<String, Object> context) {
        if(additionalContext!=null && additionalContext.exists())
        {
            getLog().info(MessageFormat.format("loading additional context from {1} into <{0}>", additionalContextRoot, additionalContext.getName()));
            context.put(additionalContextRoot, ContextUtil.loadContextFrom(additionalContext));
        }

        if(StringUtils.isNotEmpty(additionalVariables))
        {
            String[] entries = StringUtils.split(additionalVariables);
            for(String entry : entries)
            {
                String[] keyValue = StringUtils.split(entry, "=",2);
                context.put(keyValue[0], keyValue[1]);
            }
        }

    }

    public void prepareStandardContext(Map<String, Object> stdContext)
    {
        try {
            Map<String, Object> context = new HashMap<>();
            Model model = null;
            FileReader reader = null;
            MavenXpp3Reader mavenreader = new MavenXpp3Reader();
            try {
                Map<String, Object> _maven = new HashMap<>();
                reader = new FileReader(new File("pom.xml"));
                model = mavenreader.read(reader);
                model.setPomFile(new File("pom.xml"));

                Map<String, String> details = new HashMap<String, String>();
                MavenProject mavenProject = new MavenProject(model);
                details.put("groupId", mavenProject.getModel().getGroupId());
                details.put("artifactId", mavenProject.getModel().getArtifactId());
                details.put("description", mavenProject.getModel().getDescription());
                details.put("name", mavenProject.getModel().getName());
                _maven.put("project", details);

                List<Map<String, String>> dependencies = new ArrayList<Map<String, String>>();
                for (Dependency dependency : mavenProject.getDependencies()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("groupId", dependency.getGroupId());
                    map.put("artifactId", dependency.getArtifactId());
                    map.put("type", dependency.getType());
                    map.put("version", dependency.getVersion());
                    dependencies.add(map);
                }
                _maven.put("dependencies", dependencies);

                Map<String, String> properties = new HashMap<String, String>();
                for (Map.Entry<Object, Object> property : mavenProject.getProperties().entrySet()) {
                    properties.put(property.getKey().toString(), property.getValue().toString());
                }
                _maven.put("properties", properties);
                context.put("_maven", _maven);
            } catch (Exception _xe) {
                getLog().warn("error setting pom.xml in config.");
                model=null;
            }

            if(StringUtils.isNotEmpty(jdbcUrl))
            {
                context.put("_dao", ContextUtil.daoFromJdbc(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword));
            }

            if(StringUtils.isNotEmpty(this.contextRoot))
            {
                stdContext.put(this.contextRoot, context);
            }
            else
            {
                stdContext.putAll(context);
            }
        } catch (Exception _xe) {
            getLog().error("error preparing context.", _xe);
        }


    }

    public TemplateContext prepareTemplateContext(File _template, Map<String, Object> _context, File _dest)
    {
        TemplateContext _tc = new TemplateContext();
        _tc.processFile = _template;
        _tc.processDest = _dest;
        _tc.processParent = _template.getParentFile();
        _tc.processContext = _context;
        _tc.processContextHelper = ContextHelper.from(_tc);
        return _tc;
    }

    public final boolean process(File _template, Map<String, Object> _context, File _dest)
    {
        try {
            return process(prepareTemplateContext(_template, _context, _dest));
        } catch (Exception _xe) {
            getLog().error(MessageFormat.format("error processing template {0}.", _template.getAbsolutePath()), _xe);
            return false;
        }
    }

    public boolean process(TemplateContext _context)
    {
        try
        {
            _context.setOutputType(this.outputType.toLowerCase());
            if("bin".equalsIgnoreCase(this.outputType)
                    || "csv".equalsIgnoreCase(this.outputType)
                    || "xls".equalsIgnoreCase(this.outputType)
                    || "xlsx".equalsIgnoreCase(this.outputType))
            {
                return this.processToFile(_context);
            }
            else
            {
                FileUtils.write(_context.processDest, processToString(_context), StandardCharsets.UTF_8);
            }
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    public abstract String getEngine();


    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return ProcessingUtil.processTemplate(this.getEngine(), _context);
    }

    public boolean processToFile(TemplateContext _context) {
        return ProcessingUtil.processFileScript(this.getEngine(), _context, this.getIncludePath(), this.getArguments());
    }

}

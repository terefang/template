package com.github.terefang.template_maven_plugin;

import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.imageutil.PixelImage;
import com.github.terefang.imageutil.SvgImage;
import com.github.terefang.template_maven_plugin.util.ContextHelper;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
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
                String[] keyValue = StringUtils.split(entry, "=");
                context.put(keyValue[0], keyValue[1]);
            }
        }

    }

    public void prepareStandardContext(Map<String, Object> context)
    {
        try {
            Model model = null;
            FileReader reader = null;
            MavenXpp3Reader mavenreader = new MavenXpp3Reader();
            try {
                reader = new FileReader(new File("pom.xml"));
                model = mavenreader.read(reader);
                model.setPomFile(new File("pom.xml"));

                Map<String, String> details = new HashMap<String, String>();
                MavenProject mavenProject = new MavenProject(model);
                details.put("groupId", mavenProject.getModel().getGroupId());
                details.put("artifactId", mavenProject.getModel().getArtifactId());
                details.put("description", mavenProject.getModel().getDescription());
                details.put("name", mavenProject.getModel().getName());
                context.put("project", details);

                List<Map<String, String>> dependencies = new ArrayList<Map<String, String>>();
                for (Dependency dependency : mavenProject.getDependencies()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("groupId", dependency.getGroupId());
                    map.put("artifactId", dependency.getArtifactId());
                    map.put("type", dependency.getType());
                    map.put("version", dependency.getVersion());
                    dependencies.add(map);
                }
                context.put("dependencies", dependencies);

                Map<String, String> properties = new HashMap<String, String>();
                for (Map.Entry<Object, Object> property : mavenProject.getProperties().entrySet()) {
                    properties.put(property.getKey().toString(), property.getValue().toString());
                }
                context.put("properties", properties);

            } catch (Exception _xe) {
                getLog().warn("error setting pom.xml in config.");
                model=null;
            }

        } catch (Exception _xe) {
            getLog().error("error preparing context.", _xe);
        }

        if(StringUtils.isNotEmpty(jdbcUrl))
        {
            context.put("_dao", ContextUtil.daoFromJdbc(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword));
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
            if("bin".equalsIgnoreCase(this.outputType)
                    || "csv".equalsIgnoreCase(this.outputType)
                    || "xls".equalsIgnoreCase(this.outputType)
                    || "xlsx".equalsIgnoreCase(this.outputType))
            {
                _context.processContext.put("_output_type", this.outputType.toLowerCase());
                return this.processToFile(_context);
            }
            else
            if("pdf".equalsIgnoreCase(this.outputType)
                    || "svg".equalsIgnoreCase(this.outputType)
                    || "png".equalsIgnoreCase(this.outputType))
            {
                _context.processContext.put("_output_type", this.outputType.toLowerCase());
                GfxInterface _img = this.processToImage(_context);
                _img.save(_context.processDest);
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

    public boolean processToFile(TemplateContext _context) { return false; }

    public abstract GfxInterface processToImage(TemplateContext _context);

    public abstract String processToString(TemplateContext _context);
}

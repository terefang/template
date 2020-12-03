package com.github.terefang.template_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextHelper;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractTmpMojo extends AbstractMojo
{
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

    @SneakyThrows
    public void prepareAdditionalContext(Map<String, Object> context) {
        if(additionalContext!=null && additionalContext.exists())
        {
            getLog().info(MessageFormat.format("loading context {0} from {1}", additionalContextRoot, additionalContext.getName()));
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
            } catch (Exception _xe) {
                getLog().warn("error setting pom.xml in config.", _xe);
                model=null;
            }

            Map<String, String> details = new HashMap<String, String>();

            if(model!=null)
            {
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
            }
        } catch (Exception _xe) {
            getLog().error("error preparing context.", _xe);
        }
    }

    public final String process(File _template, Map<String, Object> _context)
    {
        try {
            TemplateContext _tc = new TemplateContext();
            _tc.processFile = _template;
            _tc.processParent = _template.getParentFile();
            _tc.processContext = _context;
            _tc.processContextHelper = ContextHelper.from(_tc);
            return process(_tc);
        } catch (Exception _xe) {
            getLog().error(MessageFormat.format("error processing template {0}.", _template.getAbsolutePath()), _xe);
            throw _xe;
        }
    }

    public abstract String process(TemplateContext _context);


}

package com.github.terefang.script_maven_plugin;

import com.github.terefang.template_maven_plugin.TemplateContext;
import com.github.terefang.template_maven_plugin.util.ContextHelper;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.github.terefang.template_maven_plugin.util.ProcessingUtil;
import com.google.common.collect.Maps;

import lombok.Data;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;

@Data
@Mojo(name = "exec-script", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ScriptMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}/script.sh")
    protected File scriptFile;

    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}/script.data")
    protected File dataFile;

    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File outputDirectory;

    @Parameter(defaultValue = "data")
    protected String dataContextRoot;

    @Parameter(defaultValue = "")
    protected String additionalVariables;

    @Override
    public void execute() throws MojoExecutionException
    {
        Map<String, Object> context = Maps.newHashMap();

        prepareStandardContext(context);

        //FileUtils.mkdir(resourcesOutput.getPath());
        if((scriptFile != null) && (scriptFile.isFile()))
        {
            try
            {
                getLog().info(MessageFormat.format("start executing script {0}", scriptFile.getName()));

                String _type = null;
                if(scriptFile.getName().endsWith(".jx")
                    ||scriptFile.getName().endsWith(".jexl"))
                {
                    _type = "jexl";
                }
                else
                if(scriptFile.getName().endsWith(".ecma")
                    ||scriptFile.getName().endsWith(".js"))
                {
                    _type = "rhino";
                }
                else
                if(scriptFile.getName().endsWith(".groovy")
                    ||scriptFile.getName().endsWith(".gy"))
                {
                    _type = "groovy";
                }
                else
                if(scriptFile.getName().endsWith(".py")
                    ||scriptFile.getName().endsWith(".jy"))
                {
                    _type = "jython";
                }
                else
                {
                    throw new MojoExecutionException("unknown script language in extension "+scriptFile.getName());
                }

                TemplateContext _tc = prepareTemplateContext(scriptFile, context, outputDirectory);
                _tc.setOutputType(_type);
                if(ProcessingUtil.processFileScript(_type, _tc, Collections.EMPTY_LIST))
                {
                    getLog().info(MessageFormat.format("script {0} returned OK", scriptFile.getName()));
                }
                else
                {
                    getLog().info(MessageFormat.format("script {0} returned NOK", scriptFile.getName()));
                }
            }
            catch (Exception e)
            {
                throw new MojoExecutionException(MessageFormat.format("Unable to execute script {0}", scriptFile.getAbsolutePath()), e);
            }
        }
    }

    public void prepareStandardContext(Map<String, Object> stdContext)
    {
        try {
            Map<String, Object> context = new HashMap<>();

            if(dataFile!=null && dataFile.isFile())
            {
                context = ContextUtil.loadContextFrom(dataFile);
            }


            if(StringUtils.isNotEmpty(this.dataContextRoot))
            {
                stdContext.put(this.dataContextRoot, context);
            }
            else
            {
                stdContext.putAll(context);
            }

            if(StringUtils.isNotEmpty(additionalVariables))
            {
                String[] entries = StringUtils.split(additionalVariables);
                for(String entry : entries)
                {
                    String[] keyValue = StringUtils.split(entry, "=",2);
                    stdContext.put(keyValue[0], keyValue[1]);
                }
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

}

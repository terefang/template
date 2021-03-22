package com.github.terefang.template_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Data
public abstract class AbstractTemplateMojo extends AbstractTmpMojo
{
    /**
     * The directory which contains data files
     */
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    private File resourcesDirectory;

    /**
     * where to place the processed files
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    private File resourcesOutput;

    @Parameter(defaultValue = "false")
    private boolean flattenOutput;

    @Parameter(defaultValue = "")
    protected String globalContextRoot;

    /**
     * the extension used by the destination files
     */
    @Parameter(defaultValue = ".txt")
    private String destinationExtension;

    /**
     * the template to be applied
     */
    @Parameter(defaultValue = "${project.build.directory}/template.tmpl")
    private File templateFile;

    /**
     * list of includes. ant-style/double wildcards.
     */
    @Parameter
    private String includes;

    /**
     * list of excludes. ant-style/double wildcards.
     */
    @Parameter
    private String excludes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        if(!templateFile.exists())
        {
            throw new MojoExecutionException(MessageFormat.format("template {0} not found", templateFile.getAbsolutePath()));
        }

        getLog().info(MessageFormat.format("executing for template {0}.", templateFile.getAbsolutePath()));

        Map<String, Object> context = Maps.newHashMap();

        prepareStandardContext(context);

        prepareAdditionalContext(context);

        //FileUtils.mkdir(resourcesOutput.getPath());

        DirectoryScanner scanner = new DirectoryScanner();

        if( resourcesDirectory.isDirectory())
        {
            scanner.setBasedir(resourcesDirectory);
            if (StringUtils.isNotEmpty(includes)) {
                scanner.setIncludes(StringUtils.split(includes));
            } else {
                scanner.setIncludes(new String[]{"**/*"});
            }

            if (StringUtils.isNotEmpty(excludes)) {
                scanner.setExcludes(StringUtils.split(excludes));
            }

            scanner.addDefaultExcludes();
            scanner.scan();
            process(context,scanner.getIncludedFiles(), false);
        }
        else
        if( resourcesDirectory.isFile())
        {
            process(context, new String[]{ resourcesDirectory.getPath() }, true);
        }
    }

    @SneakyThrows
    public void process(Map<String, Object> context, String[] _files, boolean _outputIsFile)
    {
        for (String key : _files)
        {
            String _id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8)).toString();
            String keybase = key.substring(0, key.lastIndexOf("."));

            File file = new File(resourcesOutput, keybase.concat(destinationExtension));

            if(flattenOutput)
            {
                file = new File(resourcesOutput, file.getName());
            }

            if(_outputIsFile)
            {
                file = resourcesOutput;
            }

            try
            {
                Map<String, Object> _tcontext = Maps.newHashMap();
                _tcontext.putAll(context);
                File localContext = new File(resourcesDirectory, key);
                if(_outputIsFile)
                {
                    localContext = new File(key);
                }

                if(StringUtils.isNotEmpty(this.globalContextRoot))
                {
                    getLog().info(MessageFormat.format("loading data from {0} into <{1}>", localContext.getName(), this.globalContextRoot));
                    _tcontext.put(this.globalContextRoot, ContextUtil.loadContextFrom(localContext));
                }
                else
                {
                    getLog().info(MessageFormat.format("loading data from {0}", localContext.getName()));
                    _tcontext.putAll(ContextUtil.loadContextFrom(localContext));
                }
                _tcontext.put("_id", _id);
                getLog().info(MessageFormat.format("start processing template {0}", templateFile));

                File parentDir = file.getParentFile();
                getLog().info(MessageFormat.format("creating output directory {0}", parentDir.getAbsolutePath()));
                parentDir.mkdirs();

                if(this.process(templateFile, _tcontext, file))
                {
                    getLog().info(MessageFormat.format("finished processed to {0}", file.getAbsolutePath()));
                }
                else
                {
                    getLog().error(MessageFormat.format("Unable to process data file {0}", key));
                }
            }
            catch (Exception e)
            {
                throw new MojoExecutionException(MessageFormat.format("Unable to process data file {0}", key), e);
            }
        }
    }


}

package com.github.terefang.template_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Map;

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
    private String[] includes;

    /**
     * list of excludes. ant-style/double wildcards.
     */
    @Parameter
    private String[] excludes;

    @SneakyThrows
    public void execute() throws MojoExecutionException
    {
        if(!templateFile.exists())
        {
            throw new MojoExecutionException(MessageFormat.format("template {0} not found", templateFile.getAbsolutePath()));
        }

        getLog().info(MessageFormat.format("executing for template {0}.", templateFile.getAbsolutePath()));

        Map<String, Object> context = Maps.newHashMap();

        prepareAdditionalContext(context);

        FileUtils.mkdir(resourcesOutput.getPath());

        DirectoryScanner scanner = new DirectoryScanner();

        if( resourcesDirectory.isDirectory()) {

            scanner.setBasedir(resourcesDirectory);
            if (includes != null && includes.length != 0) {
                scanner.setIncludes(includes);
            } else {
                scanner.setIncludes(new String[]{ "**/*.yaml" });
            }

            if (excludes != null && excludes.length != 0) {
                scanner.setExcludes(excludes);
            }

            scanner.addDefaultExcludes();
            scanner.scan();

            for (String key : scanner.getIncludedFiles())
            {
                String keybase = key.substring(0, key.lastIndexOf("."));

                File file = new File(resourcesOutput, keybase.concat(destinationExtension));

                if(flattenOutput)
                {
                    file = new File(resourcesOutput, file.getName());
                }

                try
                {
                    Map<String, Object> _tcontext = Maps.newHashMap();
                    _tcontext.putAll(context);
                    File localContext = new File(resourcesDirectory, key);
                    getLog().info(MessageFormat.format("loading data from {0}", localContext.getName()));
                    _tcontext.putAll(ContextUtil.loadContextFrom(localContext));

                    getLog().info(MessageFormat.format("start processing template {0}", templateFile));
                    String targetContent = this.process(templateFile, _tcontext);

                    file.getParentFile().mkdirs();

                    PrintWriter out = new PrintWriter(file);
                    out.print(targetContent);
                    out.close();
                    getLog().info(MessageFormat.format("finished processed to {0}", file.getAbsolutePath()));
                }
                catch (IOException e)
                {
                    getLog().error(MessageFormat.format("Unable to process data file {0}", key), e);
                    throw new MojoExecutionException(MessageFormat.format("Unable to process data file {0}", key), e);
                }
            }
        }
    }
}

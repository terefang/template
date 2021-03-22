package com.github.terefang.template_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Data
public abstract class AbstractStandardMojo extends AbstractTmpMojo {

    /**
     * The directory which contains template files
     */
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File resourcesDirectory;

    /**
     * where to place the processed template files
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    protected File resourcesOutput;

    @Parameter(defaultValue = "false")
    private boolean flattenOutput;

    /**
     * process local contexts
     */
    @Parameter(defaultValue = "false")
    protected boolean processLocalContext;

    /**
     * local context extensions
     */
    @Parameter(defaultValue = /* ContextUtil.EXTENSIONS */ ".props .properties .yaml .yml .json .hson .hjson .tml .toml .ini .pdx .pdata .sqlite.csv .list .scsv .csv .tsv .txt" )
    protected String localContextExtensions;

    /**
     * local context key
     */
    @Parameter(defaultValue = "local")
    protected String localContextRoot;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    public abstract String[] getDefaultIncludes();

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
    public void execute() throws MojoExecutionException
    {
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
            process(context, scanner.getIncludedFiles(), false);
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

            File file = new File(resourcesOutput, key.substring(0, key.lastIndexOf(".")));
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
                if(processLocalContext)
                {
                    context.remove(localContextRoot);
                    File localContext = null;
                    for(String _ext : StringUtils.split(localContextExtensions, " "))
                    {
                        File _localContext = new File(resourcesDirectory, key+_ext);
                        if(_localContext.exists())
                        {
                            localContext = _localContext;
                            break;
                        }
                    }

                    if(localContext!=null)
                    {
                        if(StringUtils.isNotEmpty(localContextRoot))
                        {
                            getLog().info(MessageFormat.format("loading local context from {1} into <{0}>", localContextRoot, localContext.getName()));
                            context.put(localContextRoot, ContextUtil.loadContextFrom(localContext));
                        }
                        else
                        {
                            getLog().info(MessageFormat.format("loading local context from {0}", localContext.getName()));
                            context.putAll(ContextUtil.loadContextFrom(localContext));
                        }
                    }
                }
                getLog().info(MessageFormat.format("start processing template {0}", key));

                File sourceFile = new File(resourcesDirectory, key);
                if(_outputIsFile)
                {
                    sourceFile = resourcesDirectory;
                }
                context.put("_id", _id);

                File parentDir = file.getParentFile();
                getLog().info(MessageFormat.format("creating output directory {0}", parentDir.getAbsolutePath()));
                parentDir.mkdirs();

                if(this.process(sourceFile, context, file))
                {
                    getLog().info(MessageFormat.format("finished processed to {0}", file.getAbsolutePath()));
                }
                else
                {
                    getLog().error(MessageFormat.format("Unable to process template file {0}", file.getAbsolutePath()));
                }

            } catch (Exception e)
            {
                throw new MojoExecutionException(MessageFormat.format("Unable to process template file {0}", file.getAbsolutePath()), e);
            }
        }
    }
}

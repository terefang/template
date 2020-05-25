package com.github.terefang.template_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.google.common.collect.Maps;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Map;

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


    /**
     * process local contexts
     */
    @Parameter(defaultValue = "false")
    protected boolean processLocalContext;

    /**
     * local context extensions
     */
    @Parameter(defaultValue = ".yaml .yml .json .hson .hjson .plist .properties")
    protected String localContextExtensions;

    /**
     * local context key
     */
    @Parameter(defaultValue = "local")
    protected String localContextRoot;

    /**
     * list of includes. ant-style/double wildcards.
     */
    @Parameter
    protected String[] includes;

    /**
     * list of excludes. ant-style/double wildcards.
     */
    @Parameter
    protected String[] excludes;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    public abstract String[] getDefaultIncludes();

    public void execute() throws MojoExecutionException
    {

        Map<String, Object> context = Maps.newHashMap();

        prepareStandardContext(context);

        prepareAdditionalContext(context);

        FileUtils.mkdir(resourcesOutput.getPath());

        DirectoryScanner scanner = new DirectoryScanner();

        if( resourcesDirectory.isDirectory()) {

            scanner.setBasedir(resourcesDirectory);
            if (includes != null && includes.length != 0) {
                scanner.setIncludes(includes);
            } else {
                scanner.setIncludes(getDefaultIncludes());
            }

            if (excludes != null && excludes.length != 0) {
                scanner.setExcludes(excludes);
            }

            scanner.addDefaultExcludes();
            scanner.scan();

            for (String key : scanner.getIncludedFiles())
            {
                File file = new File(resourcesOutput, key.substring(0, key.lastIndexOf(".")));
                try
                {
                    if(processLocalContext)
                    {
                        context.remove(localContextRoot);
                        File localContext = null;
                        for(String _ext : StringUtils.split(localContextExtensions, " "))
                        {
                            //getLog().info(MessageFormat.format("checking extension {0}", _ext));
                            File _localContext = new File(resourcesDirectory, key+_ext);
                            if(_localContext.exists())
                            {
                                localContext = _localContext;
                                break;
                            }
                            else
                            {
                                //getLog().info(MessageFormat.format("context not found: {0}", _localContext.getName()));
                            }
                        }

                        if(localContext!=null)
                        {
                            getLog().info(MessageFormat.format("loading context {0} from {1}", localContextRoot, localContext.getName()));
                            context.put(localContextRoot, ContextUtil.loadContextFrom(localContext));
                        }
                    }
                    getLog().info(MessageFormat.format("start processing template {0}", key));

                    String targetContent = this.process(new File(resourcesDirectory, key), context);

                    file.getParentFile().mkdirs();

                    PrintWriter out = new PrintWriter(file);
                    out.print(targetContent);
                    out.close();
                    getLog().info(MessageFormat.format("finished processed to {0}", file.getAbsolutePath()));

                } catch (IOException e) {
                    throw new MojoExecutionException("Unable to process template file", e);
                }
            }
        }
    }
}

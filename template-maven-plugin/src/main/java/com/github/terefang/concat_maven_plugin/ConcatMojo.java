package com.github.terefang.concat_maven_plugin;

import lombok.Data;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;

@Mojo(name = "concat", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class ConcatMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File resourcesDirectory;

    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    protected File resourcesOutput;

    @Parameter
    private String[] includes;

    @Parameter
    private String[] excludes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        DirectoryScanner scanner = new DirectoryScanner();

        if( resourcesDirectory.isDirectory())
        {
            scanner.setBasedir(resourcesDirectory);
            if (includes != null && includes.length != 0) {
                scanner.setIncludes(includes);
            } else {
                scanner.setIncludes(new String[]{"**/*"});
            }

            if (excludes != null && excludes.length != 0) {
                scanner.setExcludes(excludes);
            }

            scanner.addDefaultExcludes();
            scanner.scan();

            process(scanner.getIncludedFiles());
        }
    }

    public void process(String[] includedFiles)
    {
        includedFiles = Arrays.stream(includedFiles)
                .sorted((x,y) -> { return x.compareToIgnoreCase(y); })
                .toArray((x) -> { return new String[x]; });

        File file = null;
        PrintWriter out = null;
        String _current = null;
        try
        {
            file = resourcesOutput;
            file.getParentFile().mkdirs();
            this.getLog().info("concate to: "+resourcesOutput.getPath());
            out = new PrintWriter(file);

            for (String key : includedFiles)
            {
                this.getLog().info("concate from: "+key);
                _current = key;

                File localFile = new File(resourcesDirectory, key);

                out.println(FileUtils.fileRead(localFile));

            }
        }
        catch (Exception _xe)
        {
            this.getLog().error((_current!=null ? _current+": " : "")+_xe.getMessage(), _xe);
        }
        finally
        {
            IOUtil.close(out);
        }
    }

}

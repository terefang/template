package com.github.terefang.convert_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.github.terefang.template_maven_plugin.util.PdxWriter;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

@Mojo(name = "convert-to-pdata", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class ToPdataMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File resourcesDirectory;

    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    protected File resourcesOutput;

    @Parameter(defaultValue = "false")
    private boolean flattenOutput;

    @Parameter(defaultValue = ".pdata")
    private String destinationExtension;

    @Parameter
    private String includes;

    @Parameter
    private String excludes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
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

            process(scanner.getIncludedFiles());
        }
        else
        if( resourcesDirectory.isFile())
        {
            process(new String[]{ resourcesDirectory.getPath() });
        }
    }

    public void process(String[] includedFiles)
    {
        includedFiles = Arrays.stream(includedFiles)
                .sorted((x,y) -> { return x.compareToIgnoreCase(y); })
                .toArray((x) -> { return new String[x]; });
        File file = null;
        String _current = null;
        try
        {
            for (String key : includedFiles)
            {
                this.getLog().info("processing: "+key);
                _current = key;
                String keybase = key.substring(0, key.lastIndexOf("."));
                file = new File(resourcesOutput, keybase.concat(destinationExtension));
                if(flattenOutput)
                {
                    file = new File(resourcesOutput, file.getName());
                }
                file.getParentFile().mkdirs();

                File localFile = new File(resourcesDirectory, key);

                convertToPdata(localFile, file);
            }

        }
        catch (Exception _xe)
        {
            this.getLog().error((_current!=null ? _current+": " : "")+_xe.getMessage(), _xe);
        }
    }

    @SneakyThrows
    public static void convertToPdata(File _from, File _to)
    {
        Map<String, Object> _data = ContextUtil.loadContextFrom(_from);
        if(_to == null)
        {
            PdxWriter.writeTo(_data, new OutputStreamWriter(System.out));
        }
        else
        {
            PdxWriter.writeTo(_data, _to);
        }
    }
}

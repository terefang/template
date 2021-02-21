package com.github.terefang.convert_maven_plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
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
import org.hjson.JsonValue;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

@Mojo(name = "convert-to-hson", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class ToHsonMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File resourcesDirectory;

    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    protected File resourcesOutput;

    @Parameter(defaultValue = "false")
    private boolean flattenOutput;

    @Parameter(defaultValue = ".hson")
    private String destinationExtension;

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

                convertToHson(localFile, file);
            }

        }
        catch (Exception _xe)
        {
            this.getLog().error((_current!=null ? _current+": " : "")+_xe.getMessage(), _xe);
        }
    }

    @SneakyThrows
    public static void convertToHson(File _from, File _to)
    {
        Map<String, Object> _data = ContextUtil.loadContextFrom(_from);
        Writer _out = null;
        if(_to == null)
        {
            _out = new PrintWriter(new OutputStreamWriter(System.out));
        }
        else
        {
            _out = new FileWriter(_to);
        }
        ContextUtil.writeAsHson(false, _out, _data);
        _out.flush();
        IOUtil.close(_out);
    }
}

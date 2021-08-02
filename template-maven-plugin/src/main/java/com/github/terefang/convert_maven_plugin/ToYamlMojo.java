package com.github.terefang.convert_maven_plugin;

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
import org.codehaus.plexus.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Map;

@Mojo(name = "convert-to-yaml", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class ToYamlMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File resourcesDirectory;

    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    protected File resourcesOutput;

    @Parameter(defaultValue = "false")
    private boolean flattenOutput;

    @Parameter(defaultValue = ".yml")
    private String destinationExtension;

    @Parameter
    private String includes;

    @Parameter
    private String excludes;

    @Parameter
    boolean singleFileOutput;

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

                File localFile = new File(resourcesDirectory, key);

                if(resourcesDirectory.isFile())
                {
                    localFile = resourcesDirectory;
                }

                if(singleFileOutput) {
                    file = resourcesOutput;
                }
                file.getParentFile().mkdirs();
                convertToYaml(localFile, file);
            }

        }
        catch (Exception _xe)
        {
            this.getLog().error((_current!=null ? _current+": " : "")+_xe.getMessage(), _xe);
        }
    }

    @SneakyThrows
    public static void convertToYaml(File _from, File _to)
    {
        Map<String, Object> _data = ContextUtil.loadContextFrom(_from);
        Yaml _y = new Yaml();
        if(_to == null)
        {
            _y.dump(_data, new OutputStreamWriter(System.out) {
                @Override
                public void close() throws IOException { super.flush(); }
            });
        }
        else
        {
            FileWriter _fw = new FileWriter(_to);
            try
            {
                _y.dump(_data, _fw);
            }
            finally {
                _fw.flush();
                _fw.close();
            }
        }
    }
}

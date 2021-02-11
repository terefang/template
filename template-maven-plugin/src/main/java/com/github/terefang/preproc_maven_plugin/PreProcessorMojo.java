package com.github.terefang.preproc_maven_plugin;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import com.github.terefang.template_maven_plugin.freemarker.FreeMarkerUtil;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Mojo(name = "pre-processor", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class PreProcessorMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.scriptSourceDirectory}")
    protected File resourcesDirectory;

    @Parameter(defaultValue = "${project.build.directory}/generated-resources")
    protected File resourcesOutput;

    @Parameter(defaultValue = "false")
    private boolean singleFileOutput;

    @Parameter(defaultValue = "false")
    private boolean flattenOutput;

    @Parameter(defaultValue = ".txt")
    private String destinationExtension;

    @Parameter(defaultValue = "false")
    private boolean processSingleLineMarker;

    @Parameter(defaultValue = "false")
    private boolean processIncludes;

    @Parameter(defaultValue = "MARK")
    private String marker;

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
            if(singleFileOutput)
            {
                file = resourcesOutput;
                file.getParentFile().mkdirs();
                out = new PrintWriter(file);
            }

            for (String key : includedFiles)
            {
                this.getLog().info("processing: "+key);
                _current = key;
                String keybase = key.substring(0, key.lastIndexOf("."));
                if(!singleFileOutput)
                {
                    file = new File(resourcesOutput, keybase.concat(destinationExtension));
                    if(flattenOutput)
                    {
                        file = new File(resourcesOutput, file.getName());
                    }
                    file.getParentFile().mkdirs();
                    out = new PrintWriter(file);
                }

                File localFile = new File(resourcesDirectory, key);

                resolveAndWrite(localFile.getParentFile(), localFile, out);

                if(!singleFileOutput)
                {
                    out.close();
                }
            }

            if(singleFileOutput)
            {
                out.close();
            }
        }
        catch (Exception _xe)
        {
            this.getLog().error((_current!=null ? _current+": " : "")+_xe.getMessage(), _xe);
        }
    }

    public void resolveAndWrite(File _parent, File _file, PrintWriter _out) throws IOException
    {
        ArrayDeque<BufferedReader> _queue = new ArrayDeque<>();
        _queue.add(new BufferedReader(new FileReader(_file)));
        ArrayDeque<File> _dirs = new ArrayDeque<>();
        _dirs.add(_parent);

        while(_queue.size()>0)
        {
            BufferedReader _lr = _queue.poll();
            File _bd = _dirs.poll();

            String _line = "";
            boolean _in=false;
            while((_line = _lr.readLine()) != null)
            {
                if(_line.startsWith("%!end "))
                {
                    if(this.marker.equalsIgnoreCase(_line.substring(6).trim()))
                    {
                        _in=false;
                    }
                }
                else
                if(_in)
                {
                    _out.println(_line);
                }
                else
                if(_line.startsWith("%!begin "))
                {
                    if(this.marker.equalsIgnoreCase(_line.substring(8).trim()))
                    {
                        _in=true;
                    }
                }
                else
                if(_line.startsWith("%!include ") && processIncludes)
                {
                    _queue.push(_lr);
                    _dirs.push(_bd);
                    File _next = new File(_bd, _line.substring(10).trim());
                    this.getLog().info("including: "+_next.getName());
                    _bd = _next.getParentFile();
                    _lr = new BufferedReader(new FileReader(_next));
                }
                else
                if(_line.startsWith("%% ") && processSingleLineMarker)
                {
                    _out.println(_line.substring(3));
                }
                else
                if(_line.equalsIgnoreCase("%%") && processSingleLineMarker)
                {
                    _out.println();
                }

            }
            _out.flush();
            IOUtil.close(_lr);
        }
    }
}

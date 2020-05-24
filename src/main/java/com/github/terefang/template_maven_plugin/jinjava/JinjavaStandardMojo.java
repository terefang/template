package com.github.terefang.template_maven_plugin.jinjava;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.hubspot.jinjava.Jinjava;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.util.*;

@Mojo(name = "jinjava-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JinjavaStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.j2"};


    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    Jinjava jinjava = new Jinjava();

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        String sourceContent = FileUtils.fileRead(_template);
        return jinjava.render(sourceContent, _context);
    }
}

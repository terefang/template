package com.github.terefang.template_maven_plugin.freemarker;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.Map;

@Mojo(name = "freemarker-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class FreeMarkerStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.fm"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        return FreeMarkerUtil.process(_template, _context, this.outputType);
    }
}

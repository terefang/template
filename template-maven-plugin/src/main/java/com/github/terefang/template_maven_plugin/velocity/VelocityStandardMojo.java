package com.github.terefang.template_maven_plugin.velocity;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.codehaus.plexus.util.FileUtils;

import java.io.StringWriter;
import java.util.Map;


@Mojo(name = "velocity-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class VelocityStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.vt", "**/*.vm"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    VelocityEngine engine = new VelocityEngine();

    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        String sourceContent = FileUtils.fileRead(_context.processFile);
        VelocityContext _vcontext = new VelocityContext();
        for(Map.Entry<String, Object> _entry : _context.processContext.entrySet())
        {
            _vcontext.put(_entry.getKey(), _entry.getValue());
        }
        StringWriter _writer = new StringWriter();
        engine.evaluate(_vcontext, _writer, "["+_context.getProcessFile().getName()+"]", sourceContent);
        return _writer.getBuffer().toString();
    }
}

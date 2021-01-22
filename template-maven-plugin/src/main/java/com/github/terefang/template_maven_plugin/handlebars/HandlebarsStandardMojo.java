package com.github.terefang.template_maven_plugin.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.FileUtils;

@Mojo(name = "handlebars-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class HandlebarsStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.hbs"};


    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    Handlebars engine = new Handlebars();

    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        String sourceContent = FileUtils.fileRead(_context.processFile);
        return engine.compileInline(sourceContent).apply(_context.processContext);
    }
}

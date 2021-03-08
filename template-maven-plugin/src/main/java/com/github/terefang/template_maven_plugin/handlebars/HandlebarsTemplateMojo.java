package com.github.terefang.template_maven_plugin.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.FileUtils;

@Mojo(name = "handlebars-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class HandlebarsTemplateMojo extends AbstractTemplateMojo
{

    Handlebars engine = new Handlebars();

    @Override
    public GfxInterface processToImage(TemplateContext _context) {
        return null;
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        String sourceContent = FileUtils.fileRead(_context.processFile);
        return engine.compileInline(sourceContent).apply(_context.processContext);
    }
}

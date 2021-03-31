package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.jmelange.image.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import freemarker.log._CommonsLoggingLoggerFactory;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jxlt-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlJxtTemplateMojo extends AbstractTemplateMojo
{
    @Override
    public GfxInterface processToImage(TemplateContext _context)
    {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean processToFile(TemplateContext _context)
    {
        throw new IllegalArgumentException();
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return JexlUtil.processJxlTemplateScript(_context);
    }
}

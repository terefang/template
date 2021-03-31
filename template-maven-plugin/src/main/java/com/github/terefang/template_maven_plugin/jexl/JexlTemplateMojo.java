package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.jmelange.image.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jexl-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlTemplateMojo extends AbstractTemplateMojo
{
    @Override
    public GfxInterface processToImage(TemplateContext _context)
    {
        return JexlUtil.processImageScript(_context);
    }

    @Override
    public boolean processToFile(TemplateContext _context)
    {
        return JexlUtil.processFileScript(_context);
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return JexlUtil.processScript(_context);
    }
}

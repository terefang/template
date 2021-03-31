package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.jmelange.image.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "thymeleaf-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ThymeleafTemplateMojo extends AbstractTemplateMojo {

    @Override
    public GfxInterface processToImage(TemplateContext _context) {
        return null;
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return ThymeleafUtil.process(_context, this.outputType);
    }
}

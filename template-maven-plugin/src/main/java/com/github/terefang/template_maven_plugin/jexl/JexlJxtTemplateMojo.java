package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jxlt-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlJxtTemplateMojo extends AbstractTemplateMojo
{
    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        return JexlJxtUtil.process(_context, "");
    }
}

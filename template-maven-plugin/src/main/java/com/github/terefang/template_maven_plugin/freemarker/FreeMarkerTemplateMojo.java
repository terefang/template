package com.github.terefang.template_maven_plugin.freemarker;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "freemarker-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class FreeMarkerTemplateMojo extends AbstractTemplateMojo {

    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        return FreeMarkerUtil.process(_context, this.outputType);
    }
}

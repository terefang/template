package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.util.Map;

@Mojo(name = "thymeleaf-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ThymeleafTemplateMojo extends AbstractTemplateMojo {

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        return ThymeleafUtil.process(_template, _context, this.outputType);
    }
}

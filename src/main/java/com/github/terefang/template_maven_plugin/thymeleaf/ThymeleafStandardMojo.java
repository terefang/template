package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
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

@Mojo(name = "thymeleaf-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ThymeleafStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.tl"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        return ThymeleafUtil.process(_context, this.outputType);
    }
}

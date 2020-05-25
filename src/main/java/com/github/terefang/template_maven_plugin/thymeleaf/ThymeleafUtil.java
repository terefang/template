package com.github.terefang.template_maven_plugin.thymeleaf;

import lombok.SneakyThrows;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.util.Map;

public class ThymeleafUtil {

    @SneakyThrows
    public static String process(File _template, Map<String, Object> _context, String outputType)
    {
        TemplateEngine templateEngine = new TemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setSuffix("");
        templateResolver.setPrefix("");
        templateResolver.addTemplateAlias("main", _template.getAbsolutePath());
        templateEngine.setTemplateResolver(templateResolver);

        TemplateSpec _ts = new TemplateSpec("main", TemplateMode.valueOf(outputType.toUpperCase()));

        final Context _tc = new Context();
        _tc.setVariables(_context);

        return templateEngine.process(_ts, _tc);
    }
}

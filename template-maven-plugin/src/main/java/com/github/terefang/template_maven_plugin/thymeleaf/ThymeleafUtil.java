package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

public class ThymeleafUtil {

    @SneakyThrows
    public static String process(TemplateContext _context, String outputType)
    {
        TemplateEngine templateEngine = new TemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setSuffix("");
        templateResolver.setPrefix("");
        templateResolver.addTemplateAlias("main", _context.processFile.getAbsolutePath());
        templateEngine.setTemplateResolver(templateResolver);

        TemplateSpec _ts = new TemplateSpec("main", TemplateMode.valueOf(outputType.toUpperCase()));

        final Context _tc = new Context();
        _tc.setVariables(_context.processContext);

        return templateEngine.process(_ts, _tc);
    }
}

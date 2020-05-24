package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
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

    TemplateEngine templateEngine = new TemplateEngine();

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setSuffix(".tl");
        templateResolver.setPrefix(_template.getParent()+"/");
        templateResolver.addTemplateAlias("main", _template.getAbsolutePath());
        templateEngine.setTemplateResolver(templateResolver);

        TemplateSpec _ts = new TemplateSpec("main", TemplateMode.valueOf(this.outputType.toUpperCase()));

        final Context _tc = new Context();
        _tc.setVariables(_context);

        return templateEngine.process(_ts, _tc);
    }
}

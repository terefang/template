package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.template_maven_plugin.TemplateContext;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.StringWriter;

public class GroovyUtil {

    static SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();

    @SneakyThrows
    public static String processSimple(TemplateContext _context)
    {
        Template template = templateEngine.createTemplate(new FileReader(_context.processFile));

        StringWriter _out = new StringWriter();
        template.make(_context.processContext).writeTo(_out);

        return _out.getBuffer().toString();
    }
}

package com.github.terefang.template_maven_plugin.groovy;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Map;

public class GroovyUtil {

    static SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();

    @SneakyThrows
    public static String processSimple(File _template, Map<String, Object> _context)
    {
        Template template = templateEngine.createTemplate(new FileReader(_template));

        StringWriter _out = new StringWriter();
        template.make(_context).writeTo(_out);

        return _out.getBuffer().toString();
    }
}

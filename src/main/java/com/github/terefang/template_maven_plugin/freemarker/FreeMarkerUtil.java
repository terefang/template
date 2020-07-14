package com.github.terefang.template_maven_plugin.freemarker;

import com.github.terefang.template_maven_plugin.TemplateContext;
import freemarker.cache.ConditionalTemplateConfigurationFactory;
import freemarker.cache.FileNameGlobMatcher;
import freemarker.core.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

public class FreeMarkerUtil {

    @SneakyThrows
    public static String process(TemplateContext _context, String outputType)
    {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(_context.processParent);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(true);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        TemplateConfiguration _tcfg = new TemplateConfiguration();
        _tcfg.setEncoding("UTF-8");
        _tcfg.setWhitespaceStripping(true);
        if("text".equalsIgnoreCase(outputType))
        {
            _tcfg.setOutputFormat(PlainTextOutputFormat.INSTANCE);
        }
        else
        if("xml".equalsIgnoreCase(outputType))
        {
            _tcfg.setOutputFormat(XMLOutputFormat.INSTANCE);
        }
        else
        if("html".equalsIgnoreCase(outputType))
        {
            _tcfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
        }
        else
        if("javascript".equalsIgnoreCase(outputType))
        {
            _tcfg.setOutputFormat(JavaScriptOutputFormat.INSTANCE);
        }
        cfg.setTemplateConfigurations(new ConditionalTemplateConfigurationFactory(new FileNameGlobMatcher("*"), _tcfg));
        Template template = cfg.getTemplate(_context.processFile.getName());
        StringWriter _out = new StringWriter();
        template.process(_context.processContext, _out);
        _out.flush();
        return _out.getBuffer().toString();
    }
}

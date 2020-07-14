package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.template_maven_plugin.TemplateContext;

import lombok.SneakyThrows;
import org.apache.commons.jexl3.*;
import org.codehaus.plexus.util.IOUtil;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JexlJxtUtil {

    @SneakyThrows
    public static String process(TemplateContext _context, String outputType)
    {
        JexlInfo _ifo = new JexlInfo(_context.processFile.getAbsolutePath(), 1,1);

        JexlBuilder _builder = new JexlBuilder();
        _builder.charset(StandardCharsets.UTF_8);
        JexlEngine _jexl = _builder.create();

        JxltEngine _jxtl = _jexl.createJxltEngine();
        JxltEngine.Template _tmpl = _jxtl.createTemplate(_ifo, IOUtil.toString(new FileInputStream(_context.processFile), StandardCharsets.UTF_8.name()));

        MapContext _jctx = new MapContext();
        for(Map.Entry<String, Object> _entry : _context.processContext.entrySet())
        {
            _jctx.set(_entry.getKey(), _entry.getValue());
        }

        StringWriter _out = new StringWriter();

        _tmpl.evaluate(_jctx, _out);

        _out.flush();
        return _out.getBuffer().toString();
    }
}

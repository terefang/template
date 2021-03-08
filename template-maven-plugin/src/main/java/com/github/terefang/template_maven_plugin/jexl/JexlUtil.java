package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.template_maven_plugin.TemplateContext;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.jexl3.*;
import org.apache.commons.logging.Log;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
public class JexlUtil<engine> {

    static Log adaptLoggar()
    {
        return new Log() {
            @Override
            public void debug(Object o) {
                log.debug(o.toString());
            }

            @Override
            public void debug(Object o, Throwable throwable) {
                log.debug(o.toString(), throwable);
            }

            @Override
            public void error(Object o) {
                log.error(o.toString());
            }

            @Override
            public void error(Object o, Throwable throwable) {
                log.error(o.toString(), throwable);
            }

            @Override
            public void fatal(Object o) {
                log.error(o.toString());
            }

            @Override
            public void fatal(Object o, Throwable throwable) {
                log.error(o.toString(), throwable);
            }

            @Override
            public void info(Object o) {
                log.info(o.toString());
            }

            @Override
            public void info(Object o, Throwable throwable) {
                log.info(o.toString(), throwable);
            }

            @Override
            public boolean isDebugEnabled() {
                return log.isDebugEnabled();
            }

            @Override
            public boolean isErrorEnabled() {
                return log.isErrorEnabled();
            }

            @Override
            public boolean isFatalEnabled() {
                return log.isErrorEnabled();
            }

            @Override
            public boolean isInfoEnabled() {
                return log.isInfoEnabled();
            }

            @Override
            public boolean isTraceEnabled() {
                return log.isTraceEnabled();
            }

            @Override
            public boolean isWarnEnabled() {
                return log.isWarnEnabled();
            }

            @Override
            public void trace(Object o) {
                log.trace(o.toString());
            }

            @Override
            public void trace(Object o, Throwable throwable) {
                log.trace(o.toString(), throwable);
            }

            @Override
            public void warn(Object o) {
                log.warn(o.toString());
            }

            @Override
            public void warn(Object o, Throwable throwable) {
                log.warn(o.toString(), throwable);
            }
        };
    }

    static JexlEngine engine = new JexlBuilder().cache(512).strict(true).silent(false).logger(adaptLoggar()).create();

    @SneakyThrows
    public static Object processGenericScript(TemplateContext _context, Writer _out)
    {
        _context.processContext.put("out", _out);
        _context.processContext.put("_log", log);
        _context.processContext.put("_file", _context.getProcessDest());
        _context.processContext.put("_filepath", _context.getProcessDest().getAbsolutePath());

        JexlScript _script = engine.createScript(_context.processFile);

        MapContext l_bind = new MapContext();

        for(Map.Entry<String,Object> e : _context.processContext.entrySet())
        {
            l_bind.set(e.getKey(), e.getValue());
        }

        return _script.execute(l_bind);
    }

    public static GfxInterface processImageScript(TemplateContext _context)
    {
        Object _ret = processGenericScript(_context, new Writer() {
            @Override public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override public void flush() throws IOException { }
            @Override public void close() throws IOException { }
        });

        if(_ret instanceof GfxInterface)
        {
            return (GfxInterface) _ret;
        }
        throw new IllegalArgumentException("return type is no image "+_ret.getClass());
    }

    public static boolean processFileScript(TemplateContext _context)
    {
        Object _ret = processGenericScript(_context, new Writer() {
            @Override public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override public void flush() throws IOException { }
            @Override public void close() throws IOException { }
        });

        if(!_context.getProcessContextHelper().checkBoolean(_ret))
        {
            return false;
        }
        return true;
    }

    @SneakyThrows
    public static String processScript(TemplateContext _context)
    {
        StringBuilderWriter _sbw = new StringBuilderWriter();
        PrintWriter _pw = new PrintWriter(_sbw);

        Object _ret = processGenericScript(_context, _pw);

        // check return value if true false
        // if false output is in printwriter
        if(!_context.getProcessContextHelper().checkBoolean(_ret))
        {
            _pw.flush();
            return _sbw.getBuilder().toString();
        }
        return _ret.toString();
    }

    @SneakyThrows
    public static String processJxlTemplateScript(TemplateContext _context)
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

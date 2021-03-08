package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.template_maven_plugin.TemplateContext;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceConnector;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

@Slf4j
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

    public static Object processGenericScript(TemplateContext _context, List<String> _includePath, Writer _out)
    {
        GroovyScriptEngine _gse = createGroovyScriptEngine(_context, _includePath);
        final String scriptUri = _context.getProcessFile().getAbsolutePath();
        final Binding binding = new Binding();
        for(String _key : _context.getProcessContext().keySet())
        {
            binding.setVariable(_key, _context.getProcessContext().get(_key));
        }

        binding.setVariable("out", _out);
        binding.setVariable("_log", log);
        binding.setVariable("_file", _context.getProcessDest());
        binding.setVariable("_filepath", _context.getProcessDest().getAbsolutePath());

        Closure _closure = new Closure(_gse)
        {
            public Object call() {
                try {
                    return ((GroovyScriptEngine) this.getDelegate()).run(scriptUri, binding);
                } catch (ResourceException _re)
                {
                    log.error(_re.getMessage());
                    return null;
                } catch (ScriptException _se) {
                    log.error(_se.getMessage());
                    return null;
                }
            }
        };

        return _closure.call();
    }

    public static boolean processFileScript(TemplateContext _context, List<String> _includePath)
    {
        Object _ret = processGenericScript(_context, _includePath, new Writer() {
            @Override public void write(char[] cbuf, int off, int len) throws IOException { log.info("out off="+off+" len="+len);}
            @Override public void flush() throws IOException { }
            @Override public void close() throws IOException { }
        });

        if(!_context.getProcessContextHelper().checkBoolean(_ret))
        {
            return false;
        }
        return true;
    }

    public static GfxInterface processImageScript(TemplateContext _context, List<String> _includePath)
    {
        Object _ret = processGenericScript(_context, _includePath, new Writer() {
            @Override public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override public void flush() throws IOException { }
            @Override public void close() throws IOException { }
        });

        if(_ret instanceof GfxInterface)
        {
            return (GfxInterface) _ret;
        }
        throw new IllegalArgumentException("return type is no image");
    }

    @SneakyThrows
    public static synchronized String processScript(TemplateContext _context, List<String> _includePath)
    {
        StringBuilderWriter _sbw = new StringBuilderWriter();
        PrintWriter _pw = new PrintWriter(_sbw);

        Object _ret = processGenericScript(_context,_includePath, _pw);

        // check return value if true false
        // if false output is in printwriter
        if(!_context.getProcessContextHelper().checkBoolean(_ret))
        {
            _pw.flush();
            return _sbw.getBuilder().toString();
        }
        return _ret.toString();
    }

    static GroovyScriptEngine createGroovyScriptEngine(TemplateContext context, List<String> includePath) {
        return new GroovyScriptEngine(
                GroovyUtilResourceConnector.create(context, includePath),
                GroovyUtil.class.getClassLoader());
    }


    static class GroovyUtilResourceConnector implements ResourceConnector {

        File scriptFile;
        List<String> includePath;
        public static ResourceConnector create(TemplateContext _context, List<String> _includePath) {
            GroovyUtilResourceConnector _grc = new GroovyUtilResourceConnector();
            _grc.includePath = _includePath;
            _grc.scriptFile = _context.processFile;
            return _grc;
        }

        @Override
        @SneakyThrows
        public URLConnection getResourceConnection(String _resourcePath) throws ResourceException {
            URL _url = null;
            if((_url = GroovyUtil.findResourceUrl(_resourcePath, this.scriptFile, this.includePath)) != null)
            {
                return _url.openConnection();
            }
            throw new ResourceException(_resourcePath);
        }
    }

    @SneakyThrows
    public static final URL findResourceUrl(String _resourcePath, File _scriptFile, List<String> _includePath)
    {
        //log.warn(_resourcePath);
        if(_resourcePath.equalsIgnoreCase(_scriptFile.getAbsolutePath()))
        {
            return _scriptFile.toURL();
        }

        if(_resourcePath.startsWith("file:"))
        {
            URL _url = new URL(_resourcePath);
            if(new File(_url.getPath()).exists())
            {
                return _url;
            }
            return null;
        }

        List<String> _dirs = new Vector<>();
        _dirs.add(_scriptFile.getParent());
        if(_includePath!=null) _dirs.addAll(_includePath);
        for(String _path : _dirs)
        {
            File _test = new File(_path, _resourcePath);
            if(_test.exists())
            {
                return _test.toURL();
            }
        }
        return null;
    }
}

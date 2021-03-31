package com.github.terefang.template_maven_plugin.rhino;

import com.github.terefang.jmelange.image.GfxInterface;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.codehaus.plexus.util.IOUtil;
import org.mozilla.javascript.*;

import java.io.*;
import java.util.List;
import java.util.Map;

@Data @Slf4j
public class RhinoScript
{
    public static GfxInterface processImageScript(TemplateContext _context, List<String> _includePath)
    {
        Object _ret = processGenericScript(_context, _includePath, new Writer() {
            @Override public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override public void flush() throws IOException { }
            @Override public void close() throws IOException { }
        }, false);

        if(_ret instanceof NativeJavaObject)
        {
            _ret = ((NativeJavaObject)_ret).unwrap();
        }

        if(_ret instanceof GfxInterface)
        {
            return (GfxInterface) _ret;
        }
        throw new IllegalArgumentException("return type is no image "+_ret.getClass());
    }

    @SneakyThrows
    public static Object processGenericScript(TemplateContext context, List<String> includePath, Writer _out, boolean isEsp)
    {
        context.processContext.put("out", _out);
        context.processContext.put("_log", log);
        context.processContext.put("_file", context.getProcessDest());
        context.processContext.put("_filepath", context.getProcessDest().getAbsolutePath());

        RhinoScript _rs = new RhinoScript();
        BufferedReader _reader = new BufferedReader(new FileReader(context.getProcessFile()));
        if(isEsp)
        {
            return _rs.runEsp(_reader, context.getProcessFile().getAbsolutePath(), context.processContext);
        }
        else
        {
            return _rs.runScript(_reader, context.getProcessFile().getAbsolutePath(), context.processContext, true);
        }
    }

    public static String processTemplateScript(TemplateContext _context, List<String> _includePath)
    {
        StringBuilderWriter _sbw = new StringBuilderWriter();
        PrintWriter _pw = new PrintWriter(_sbw);

        Object _ret = processGenericScript(_context,_includePath, _pw, true);

        // check return value if true false
        // if false output is in printwriter
        if(!_context.getProcessContextHelper().checkBoolean(_ret))
        {
            _pw.flush();
            return _sbw.getBuilder().toString();
        }
        return _ret.toString();
    }

    public static boolean processFileScript(TemplateContext _context, List<String> _includePath)
    {
        Object _ret = processGenericScript(_context, _includePath, new Writer() {
            @Override public void write(char[] cbuf, int off, int len) throws IOException { log.info("out off="+off+" len="+len);}
            @Override public void flush() throws IOException { }
            @Override public void close() throws IOException { }
        }, false);

        if(!_context.getProcessContextHelper().checkBoolean(_ret))
        {
            return false;
        }
        return true;
    }

    public Object runEsp(Reader scriptCode, String codePath, Map<String, Object> context)
    {
        return runScript(new EspReader(scriptCode), codePath, context, false);
    }

    public Object runScript(Reader scriptCode, String codePath, Map<String, Object> context, boolean callFunction)
    {
        Context l_engine = Context.enter();
        l_engine.setLanguageVersion(Context.VERSION_ES6);
        Scriptable l_bind = l_engine.initStandardObjects();
        try
        {
            for(Map.Entry<String,Object> e : context.entrySet())
            {
                ScriptableObject.putProperty(l_bind, e.getKey(), Context.javaToJS(e.getValue(), l_bind));
            }

            if(callFunction)
            {
                Function func = l_engine.compileFunction(l_bind, IOUtil.toString(scriptCode), codePath, 1, null);

                return func.call(l_engine, l_bind, null, new Object[0]);
            }
            else
            {
                return l_engine.compileReader(l_bind,scriptCode,codePath, 1,null)
                        .exec(l_engine, l_bind);
            }
        }
        catch(Exception xe)
        {
            log.warn("error in file", xe);
            return null;
        }
        finally
        {
            Context.exit();
        }
    }
}

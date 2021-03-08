package com.github.terefang.template_maven_plugin.luaj;


import com.github.terefang.template_maven_plugin.TemplateContext;
import com.github.terefang.template_maven_plugin.util.ContextHelper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Slf4j
@Data
public class LuaScriptContext
{
    TemplateContext context;
    ContextHelper contextHelper;
    List<String> includePath;

    public static LuaScriptContext create(TemplateContext _context, List<String> _includePath)
    {
        LuaScriptContext _c = new LuaScriptContext();
        _c.context = _context;
        _c.contextHelper = _context.getProcessContextHelper();
        _c.includePath = _includePath;
        return _c;
    }

    @SneakyThrows
    public String runLua()
    {
        return runLua(this.getContext().getProcessFile());
    }

    @SneakyThrows
    public String runLua(String _source)
    {
        return runLua(new StringReader(_source));
    }

    @SneakyThrows
    public String runLua(File _source)
    {
        return runLua(new FileReader(_source));
    }

    public String runLua(Reader _source)
    {
        StringBuilderWriter _sbw = new StringBuilderWriter();
        runLuaGeneric(_source,  _sbw);
        return _sbw.getBuilder().toString();
    }

    @SneakyThrows
    public LuaValue runLuaGeneric(Writer _out)
    {
        return runLuaGeneric(this.getContext().getProcessFile(), _out);
    }

    @SneakyThrows
    public LuaValue runLuaGeneric(String _source, Writer _out)
    {
        return runLuaGeneric(new StringReader(_source), _out);
    }

    @SneakyThrows
    public LuaValue runLuaGeneric(File _source, Writer _out)
    {
        return runLuaGeneric(new FileReader(_source), _out);
    }

    @SneakyThrows
    public LuaValue runLuaGeneric(Reader _source, Writer _out)
    {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());

        PackageLib _pkglib = new PackageLib();
        globals.load(_pkglib);
        List<String> pkgSearchPath = new Vector<>();

        pkgSearchPath.add(this.getContext().getProcessParent().getAbsolutePath());
        if(this.includePath!=null)
        {
            pkgSearchPath.addAll(this.includePath);
        }
        String _path = StringUtils.join(pkgSearchPath.iterator(), "/?.lua;")+"/?.lua;?.lua";
        log.info("setting search-path "+_path);
        _pkglib.setLuaPath(_path);

        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
        globals.load(new JseIoLib());
        globals.load(new JseOsLib());
        globals.load(new LuajavaLib());
        globals.load(new JStringLib());
        globals.load(new JDaoLib());
        LoadState.install(globals);
        LuaC.install(globals);

        for(Map.Entry<String, Object> _entry : this.getContext().getProcessContext().entrySet())
        {
            globals.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
        }

        PrintWriter _pw = new PrintWriter(_out);
        LuaValue _lval = null;
        try {
            globals.set("out", CoerceJavaToLua.coerce(_pw));
            globals.set("_log", CoerceJavaToLua.coerce(log));
            globals.set("_file", CoerceJavaToLua.coerce(this.context.processDest));
            globals.set("_filepath", CoerceJavaToLua.coerce(this.context.processDest.getAbsolutePath()));
            LuaValue _chunk = globals.load(_source, this.getContext().getProcessFile().getName());
            _lval = _chunk.call();
        }
        finally {
            _pw.flush();
        }
        return _lval;
    }

    public static LuaValue wrapJava(Object _o)
    {
        return CoerceJavaToLua.coerce(_o);
    }
}

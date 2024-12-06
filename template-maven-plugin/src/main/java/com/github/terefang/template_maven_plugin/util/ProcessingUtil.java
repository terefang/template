package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.jmelange.commons.util.BooleanUtil;
import com.github.terefang.jmelange.commons.util.FileUtil;
import com.github.terefang.jmelange.script.IScriptContext;
import com.github.terefang.jmelange.script.IScriptContextFactory;
import com.github.terefang.jmelange.script.ITemplateContext;
import com.github.terefang.jmelange.script.ITemplateContextFactory;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProcessingUtil {

    @SneakyThrows
    public static String processTemplate(String _type, TemplateContext _context)
    {
        String _fpath = _context.processFile.getAbsolutePath();
        String _dpath = _context.processParent.getAbsolutePath();
        String _file = _context.processFile.getName();
        String _ext = FileUtil.getExtension(_file);
        
        ByteArrayOutputStream _out = new ByteArrayOutputStream();
        
        ITemplateContext _tcf = ITemplateContextFactory.findByName(_type).newInstance(_fpath,_ext, Collections.singletonList(_dpath));
        _tcf.compile(_context.processFile);

        Map<String,Object> _ctx = new HashMap<>();
        for(Map.Entry<String, Object> _entry : _context.processContext.entrySet())
        {
            _ctx.put(_entry.getKey(), _entry.getValue());
        }
        //_ctx.put("_", _context.processContextHelper);

        _tcf.run(_ctx,_out);
        
        return new String(_out.toByteArray(), StandardCharsets.UTF_8);
    }

    public static boolean processFileScript(String _type, TemplateContext _context, List<String> _includePath, List<String> _argv)
    {
        String _fpath = _context.processFile.getAbsolutePath();
        String _dpath = _context.processParent.getAbsolutePath();
        String _file = _context.processFile.getName();
        String _ext = FileUtil.getExtension(_file);
        
        IScriptContext _sc = IScriptContextFactory.findByName(_type)
                .newInstance(_fpath, _ext, Collections.singletonList(_dpath));
        
        _sc.compile(_context.processFile);
        
        Map<String,Object> _ctx = new HashMap<>();
        for(Map.Entry<String, Object> _entry : _context.processContext.entrySet())
        {
            _ctx.put(_entry.getKey(), _entry.getValue());
        }
        //_ctx.put("_", _context.processContextHelper);

        Object _ret = _sc.run(_context.processContext);
        
        if(_ret instanceof Boolean)
        {
            return (Boolean)_ret;
        }
        
        return BooleanUtil.checkBoolean(_ret);
    }
    
}

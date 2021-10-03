package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.jmelange.commons.CommonUtil;
import com.github.terefang.jmelange.gfx.GfxInterface;
import com.github.terefang.jmelange.scripted.AbstractScript;
import com.github.terefang.jmelange.scripted.JMelangeScriptFactoryLoader;
import com.github.terefang.jmelange.scripted.util.SimpleVariableProvider;
import com.github.terefang.jmelange.templating.AbstractTemplateScript;
import com.github.terefang.jmelange.templating.JMelangeTemplateScriptFactoryLoader;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.Marker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProcessingUtil {

    public static AbstractScript setupScript(AbstractScript _script, String _type, TemplateContext _context, List<String> _includePath, List<String> _argv)
    {
        if(_script == null)
        {
            _script = JMelangeScriptFactoryLoader
                    .loadFactoryByName(_type)
                    .createScript();
        }

        _script.setErrorLogger(ProcessingUtil.log);

        //_script.setVars();
        if((_argv!=null) && (_argv.size()>0))
        {
            _script.setArgs((List)_argv);
        }

        _script.setVariableProviders(CommonUtil.toList(SimpleVariableProvider.create(_context.processContext)));

        //_script.setSourceFile();
        //_script.setInputStream();

        _script.setOutputType(_context.getOutputType());
        _script.setDestinationFile(_context.processDest);

        //_script.setOutputStream();

        //_script.setScriptFile();
        if((_includePath!=null) && (_includePath.size()>0))
        {
            List<File> _ilist = new ArrayList<File>(_includePath.size());
            for(String _d : _includePath)
            {
                _ilist.add(new File(_d));
            }
            _script.setIncludeDirectories(_ilist) ;
        }

        return _script;
    }

    @SneakyThrows
    public static String processTemplate(String _type, TemplateContext _context) {
        AbstractTemplateScript _script = JMelangeTemplateScriptFactoryLoader
                .loadFactoryByName(_type)
                .createTemplateScript();

        setupScript(_script, _type, _context, null, null);

        ByteArrayOutputStream _out = new ByteArrayOutputStream();
        _script.setOutputStream(_out);

        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        _script.execute();

        _out.flush();

        return new String(_out.toByteArray(), StandardCharsets.UTF_8);
    }

    public static boolean processFileScript(String _type, TemplateContext _context, List<String> _includePath, List<String> _argv) {
        AbstractScript _script = setupScript(null, _type, _context, _includePath, _argv);

        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        return _script.execute();
    }

    public static GfxInterface processImageScript(String _type, TemplateContext _context, List<String> _includePath, List<String> _argv) {
        AbstractScript _script = setupScript(null, _type, _context, _includePath, _argv);

        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        Object _ret = _script.executeObject(false);

        if (_ret instanceof GfxInterface) {
            return (GfxInterface) _ret;
        }
        throw new IllegalArgumentException("return type is no image");
    }

    @SneakyThrows
    public static synchronized String processScript(String _type, TemplateContext _context, List<String> _includePath, List<String> _argv) {
        AbstractScript _script = setupScript(null, _type, _context, _includePath, _argv);

        ByteArrayOutputStream _out = new ByteArrayOutputStream();
        _script.setOutputStream(_out);

        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        _script.execute();

        _out.flush();

        return new String(_out.toByteArray(), StandardCharsets.UTF_8);
    }
}

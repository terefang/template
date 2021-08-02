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

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class ProcessingUtil {

    @SneakyThrows
    public static String processTemplate(String _type, TemplateContext _context) {
        AbstractTemplateScript _script = JMelangeTemplateScriptFactoryLoader
                .loadFactoryByName(_type)
                .createTemplateScript();
        _script.setErrorLogger(ProcessingUtil.log);

        //_script.setVars();
        //_script.setArgs();
        _script.setVariableProviders(CommonUtil.toList(SimpleVariableProvider.create(_context.processContext)));

        //_script.setSourceFile();
        //_script.setInputStream();

        _script.setOutputType(_context.getOutputType());
        _script.setDestinationFile(_context.processDest);
        ByteArrayOutputStream _out = new ByteArrayOutputStream();
        _script.setOutputStream(_out);

        //_script.setScriptFile();
        //_script.setIncludeDirectories();
        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        _script.execute();

        _out.flush();

        return new String(_out.toByteArray(), StandardCharsets.UTF_8);
    }

    public static boolean processFileScript(String _type, TemplateContext _context, List<String> _includePath) {
        AbstractScript _script = JMelangeScriptFactoryLoader
                .loadFactoryByName(_type)
                .createScript();
        _script.setErrorLogger(ProcessingUtil.log);

        //_script.setVars();
        //_script.setArgs();
        _script.setVariableProviders(CommonUtil.toList(SimpleVariableProvider.create(_context.processContext)));

        //_script.setSourceFile();
        //_script.setInputStream();

        _script.setOutputType(_context.getOutputType());
        _script.setDestinationFile(_context.processDest);
        //_script.setOutputStream();

        //_script.setScriptFile();
        //_script.setIncludeDirectories();
        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        return _script.execute();
    }

    public static GfxInterface processImageScript(String _type, TemplateContext _context, List<String> _includePath) {
        AbstractScript _script = JMelangeScriptFactoryLoader
                .loadFactoryByName(_type)
                .createScript();
        _script.setErrorLogger(ProcessingUtil.log);

        //_script.setVars();
        //_script.setArgs();
        _script.setVariableProviders(CommonUtil.toList(SimpleVariableProvider.create(_context.processContext)));

        //_script.setSourceFile();
        //_script.setInputStream();

        _script.setOutputType(_context.getOutputType());
        _script.setDestinationFile(_context.processDest);
        //_script.setOutputStream();

        //_script.setScriptFile();
        //_script.setIncludeDirectories();
        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        Object _ret = _script.executeObject();

        if (_ret instanceof GfxInterface) {
            return (GfxInterface) _ret;
        }
        throw new IllegalArgumentException("return type is no image");
    }

    @SneakyThrows
    public static synchronized String processScript(String _type, TemplateContext _context, List<String> _includePath) {
        AbstractScript _script = JMelangeScriptFactoryLoader
                .loadFactoryByName(_type)
                .createScript();
        _script.setErrorLogger(ProcessingUtil.log);

        //_script.setVars();
        //_script.setArgs();
        _script.setVariableProviders(CommonUtil.toList(SimpleVariableProvider.create(_context.processContext)));

        //_script.setSourceFile();
        //_script.setInputStream();

        _script.setOutputType(_context.getOutputType());
        _script.setDestinationFile(_context.processDest);
        ByteArrayOutputStream _out = new ByteArrayOutputStream();
        _script.setOutputStream(_out);

        //_script.setScriptFile();
        //_script.setIncludeDirectories();
        _script.init(_context.processFile, CommonUtil.toList(_context.processParent));

        _script.execute();

        _out.flush();

        return new String(_out.toByteArray(), StandardCharsets.UTF_8);
    }
}

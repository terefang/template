package com.github.terefang.template_maven_plugin.jython;

import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.commons.io.output.StringBuilderWriter;
import org.codehaus.plexus.util.IOUtil;
import org.python.core.PyCode;
import org.python.util.PythonInterpreter;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class JythonScriptUtil {

    @SneakyThrows
    public static String process(TemplateContext _context, List<String> _includePath, String _outputType)
    {
        String _scriptCode = IOUtil.toString(new FileReader(_context.getProcessFile()));
        PythonInterpreter _engine = new PythonInterpreter();
        if(_includePath!=null && _includePath.size()>0)
        {
            StringBuilder _sb = new StringBuilder();
            _sb.append("import sys\n");
            for(String _path : _includePath)
            {
                _sb.append("sys.path.append('"+_path+"')\n");
            }
            _sb.append("\n");
            _sb.append(_scriptCode);
            _scriptCode = _sb.toString();
        }
        PyCode _script = _engine.compile(_scriptCode, _context.getProcessFile().getName());
        for(Map.Entry<String, Object> _entry : _context.getProcessContext().entrySet())
        {
            _engine.set(_entry.getKey(), _entry.getValue());
        }
        StringBuilderWriter _out = new StringBuilderWriter();
        _engine.setOut(_out);
        Object _ret = _engine.eval(_script);
        _out.flush();
        if(_ret!=null && _ret instanceof String)
        {
            return _ret.toString();
        }
        return _out.getBuilder().toString();
    }
}

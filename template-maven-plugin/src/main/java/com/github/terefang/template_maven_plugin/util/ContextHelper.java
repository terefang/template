package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;


import java.io.*;
import java.text.MessageFormat;
import java.util.*;

public class ContextHelper extends ContextUtil
{
    TemplateContext context;

    public static ContextHelper from(TemplateContext _tc)
    {
        ContextHelper _h = new ContextHelper();
        _h.context = _tc;
        _tc.processContext.put("__", _h);
        return _h;
    }

    @SneakyThrows
    public final Map<String, Object> loadContextFile(String _file)
    {
        File _fn = new File(_file);
        if(!_fn.exists())
        {
            _fn = new File(this.context.processParent, _file);
        }

        if(!_fn.exists())
        {
            throw new IllegalArgumentException(MessageFormat.format("File '{0}' not found.", _file));
        }

        return loadContextFrom(_fn);
    }

}

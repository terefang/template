package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Map;

public class ContextHelper {
    TemplateContext context;

    public static ContextHelper from(TemplateContext _tc)
    {
        ContextHelper _h = new ContextHelper();
        _h.context = _tc;
        _tc.processContext.put("_", _h);
        return _h;
    }

    @SneakyThrows
    public Map<String, Object> load(String _file)
    {
        return ContextUtil.loadContextFrom(new File(this.context.processParent, _file));
    }
}

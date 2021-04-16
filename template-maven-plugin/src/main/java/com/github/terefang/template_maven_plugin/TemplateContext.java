package com.github.terefang.template_maven_plugin;

import com.github.terefang.template_maven_plugin.util.ContextHelper;
import lombok.Data;

import java.io.File;
import java.util.Map;

@Data
public class TemplateContext {
    public File processFile;
    public File processParent;
    public Map<String, Object> processContext;
    public ContextHelper processContextHelper;
    public File processDest;
    public String outputType;
}

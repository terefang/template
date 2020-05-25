package com.github.terefang.template_maven_plugin.freemarker;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.Map;

@Mojo(name = "freemarker-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class FreeMarkerTemplateMojo extends AbstractTemplateMojo {

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        return FreeMarkerUtil.process(_template, _context, this.outputType);
    }
}

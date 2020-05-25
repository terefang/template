package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.thymeleaf.ThymeleafUtil;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.Map;

@Mojo(name = "gsimple-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GroovySimpleTemplateMojo extends AbstractTemplateMojo {

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        return GroovyUtil.processSimple(_template, _context);
    }
}

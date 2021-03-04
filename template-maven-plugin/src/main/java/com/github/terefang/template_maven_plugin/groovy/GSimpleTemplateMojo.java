package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import com.github.terefang.template_maven_plugin.thymeleaf.ThymeleafUtil;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.Map;

@Mojo(name = "gsimple-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GSimpleTemplateMojo extends AbstractTemplateMojo {

    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        return GroovyUtil.processSimple(_context);
    }
}

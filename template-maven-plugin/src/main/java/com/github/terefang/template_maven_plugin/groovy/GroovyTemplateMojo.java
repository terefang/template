package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

@Mojo(name = "groovy-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class GroovyTemplateMojo extends AbstractTemplateMojo {

    @Parameter(defaultValue = "")
    List<String> includePath;

    @Override
    public GfxInterface processToImage(TemplateContext _context)
    {
        return GroovyUtil.processImageScript(_context, includePath);
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return GroovyUtil.processScript(_context, includePath);
    }

    @Override
    public boolean processToFile(TemplateContext _context)
    {
        return GroovyUtil.processFileScript(_context, includePath);
    }
}

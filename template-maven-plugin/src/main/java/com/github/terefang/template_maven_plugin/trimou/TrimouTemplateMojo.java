package com.github.terefang.template_maven_plugin.trimou;

import com.github.terefang.jmelange.image.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import com.hubspot.jinjava.Jinjava;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.FileUtils;
import org.trimou.engine.MustacheEngine;
import org.trimou.engine.MustacheEngineBuilder;

@Mojo(name = "trimou-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class TrimouTemplateMojo extends AbstractTemplateMojo
{

    MustacheEngine engine = MustacheEngineBuilder
            .newBuilder()
            .build();

    @Override
    public GfxInterface processToImage(TemplateContext _context) {
        return null;
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        String sourceContent = FileUtils.fileRead(_context.processFile);
        return engine.compileMustache(sourceContent).render(_context.processContext);
    }
}

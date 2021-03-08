package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jxlt-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlJxtStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.jxt"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public GfxInterface processToImage(TemplateContext _context)
    {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean processToFile(TemplateContext _context)
    {
        throw new IllegalArgumentException();
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return JexlUtil.processJxlTemplateScript(_context);
    }
}

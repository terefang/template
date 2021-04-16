package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.jmelange.image.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.PrintWriter;

@Mojo(name = "jexl-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.jx"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "jexl";
    }
}

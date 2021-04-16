package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "thymeleaf-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ThymeleafStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.tl"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "thymeleaf";
    }
}

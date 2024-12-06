package com.github.terefang.template_maven_plugin.basictemplate;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "basic-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class BasicStandardMojo
        extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.bt"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "basicTemplate";
    }
}

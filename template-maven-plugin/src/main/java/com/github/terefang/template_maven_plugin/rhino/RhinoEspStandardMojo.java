package com.github.terefang.template_maven_plugin.rhino;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "rhino-esp-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RhinoEspStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.ejs", "**/*.esp"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "rhino-esp";
    }
}

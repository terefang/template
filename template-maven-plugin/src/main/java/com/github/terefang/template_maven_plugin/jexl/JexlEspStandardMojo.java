package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jexl-esp-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlEspStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.ejx","**/*.jxp"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "jexl-esp";
    }
}

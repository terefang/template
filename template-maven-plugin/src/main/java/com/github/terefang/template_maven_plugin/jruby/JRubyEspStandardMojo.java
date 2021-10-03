package com.github.terefang.template_maven_plugin.jruby;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "jruby-esp-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JRubyEspStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.ejrb", "**/*.erb"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "jruby-esp";
    }
}

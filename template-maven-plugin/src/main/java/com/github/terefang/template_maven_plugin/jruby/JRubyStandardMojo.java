package com.github.terefang.template_maven_plugin.jruby;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "jruby-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JRubyStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.rb", "**/*.jrb"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "jruby";
    }
}

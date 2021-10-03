package com.github.terefang.template_maven_plugin.jruby;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "jruby-esp-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JRubyEspTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "jruby-esp";
    }
}

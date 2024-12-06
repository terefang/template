package com.github.terefang.template_maven_plugin.basictemplate;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "basic-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class BasicTemplateMojo
        extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "basicTemplate";
    }
}

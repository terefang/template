package com.github.terefang.template_maven_plugin.rhino;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "rhino-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RhinoTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "rhino";
    }
}

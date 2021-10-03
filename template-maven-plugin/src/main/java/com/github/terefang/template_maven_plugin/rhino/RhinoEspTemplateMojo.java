package com.github.terefang.template_maven_plugin.rhino;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "rhino-esp-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RhinoEspTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "rhino-esp";
    }
}

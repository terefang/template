package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "gsimple-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GSimpleTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "gsimple";
    }
}

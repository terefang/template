package com.github.terefang.template_maven_plugin.jinjava;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "jinjava-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JinjavaTemplateMojo extends AbstractTemplateMojo
{

    @Override
    public String getEngine() {
        return "jinjava";
    }
}

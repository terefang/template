package com.github.terefang.template_maven_plugin.thymeleaf;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "thymeleaf-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ThymeleafTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "thymeleaf";
    }
}

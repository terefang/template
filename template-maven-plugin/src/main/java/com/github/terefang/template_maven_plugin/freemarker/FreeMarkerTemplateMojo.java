package com.github.terefang.template_maven_plugin.freemarker;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "freemarker-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class FreeMarkerTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "freemarker";
    }
}

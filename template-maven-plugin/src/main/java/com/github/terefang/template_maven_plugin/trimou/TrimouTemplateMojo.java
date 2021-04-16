package com.github.terefang.template_maven_plugin.trimou;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "trimou-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class TrimouTemplateMojo extends AbstractTemplateMojo
{

    @Override
    public String getEngine() {
        return "trimou";
    }
}

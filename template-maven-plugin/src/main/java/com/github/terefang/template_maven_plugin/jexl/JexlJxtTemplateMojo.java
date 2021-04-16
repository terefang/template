package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jxlt-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlJxtTemplateMojo extends AbstractTemplateMojo
{
    @Override
    public String getEngine() {
        return "jxlt";
    }
}

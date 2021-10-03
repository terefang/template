package com.github.terefang.template_maven_plugin.jexl;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "jexl-esp-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JexlEspTemplateMojo extends AbstractTemplateMojo
{
    @Override
    public String getEngine() {
        return "jexl-esp";
    }
}

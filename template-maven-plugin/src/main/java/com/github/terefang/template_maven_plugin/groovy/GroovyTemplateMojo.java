package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import lombok.Data;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "groovy-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class GroovyTemplateMojo extends AbstractTemplateMojo {

    @Override
    public String getEngine() {
        return "groovy";
    }
}

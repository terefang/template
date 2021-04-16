package com.github.terefang.template_maven_plugin.groovy;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import lombok.Data;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "groovy-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class GroovyStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.groovy", "**/*.gy"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Override
    public String getEngine() {
        return "groovy";
    }
}

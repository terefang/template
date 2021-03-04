package com.github.terefang.template_maven_plugin.luaj;

import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

@Mojo(name = "luaj-standard", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class LuajStandardMojo extends AbstractStandardMojo {

    private static final String[] DEFAULT_INCLUDES = new String[]{"**/*.lua"};

    public String[] getDefaultIncludes()
    {
        return DEFAULT_INCLUDES;
    }

    @Parameter(defaultValue = "")
    List<String> includePath;

    @Override
    @SneakyThrows
    public String process(TemplateContext _context)
    {
        return LuaScriptContext.create(_context, includePath).runLua();
    }
}

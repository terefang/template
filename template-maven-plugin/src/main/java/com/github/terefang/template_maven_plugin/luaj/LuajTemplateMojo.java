package com.github.terefang.template_maven_plugin.luaj;

import com.github.terefang.imageutil.GfxInterface;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Mojo(name = "luaj-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Data
public class LuajTemplateMojo extends AbstractTemplateMojo {

    @Parameter(defaultValue = "")
    List<String> includePath;

    @Override
    public GfxInterface processToImage(TemplateContext _context)
    {
        LuaScriptContext _lc = LuaScriptContext.create(_context, includePath);
        LuaValue _lval = _lc.runLuaGeneric(new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        });
        return (GfxInterface) _lval.checkuserdata();
    }

    @Override
    @SneakyThrows
    public String processToString(TemplateContext _context)
    {
        return LuaScriptContext.create(_context, includePath).runLua();
    }
}

package com.github.terefang.template_maven_plugin.jinjava;

import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.util.ContextUtil;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Map;

@Mojo(name = "jinjava-template", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class JinjavaTemplateMojo extends AbstractTemplateMojo
{

    Jinjava jinjava = new Jinjava();

    @Override
    @SneakyThrows
    public String process(File _template, Map<String, Object> _context)
    {
        String sourceContent = FileUtils.fileRead(_template);
        return jinjava.render(sourceContent, _context);
    }
}

package com.github.terefang.template_cli;

import com.github.terefang.concat_maven_plugin.ConcatMojo;
import com.github.terefang.convert_maven_plugin.ToHsonMojo;
import com.github.terefang.convert_maven_plugin.ToJsonMojo;
import com.github.terefang.convert_maven_plugin.ToPdataMojo;
import com.github.terefang.preproc_maven_plugin.PreProcessorMojo;
import com.github.terefang.template_maven_plugin.AbstractStandardMojo;
import com.github.terefang.template_maven_plugin.AbstractTemplateMojo;
import com.github.terefang.template_maven_plugin.AbstractTmpMojo;
import com.github.terefang.template_maven_plugin.freemarker.FreeMarkerStandardMojo;
import com.github.terefang.template_maven_plugin.freemarker.FreeMarkerTemplateMojo;
import com.github.terefang.template_maven_plugin.groovy.GroovySimpleStandardMojo;
import com.github.terefang.template_maven_plugin.groovy.GroovySimpleTemplateMojo;
import com.github.terefang.template_maven_plugin.handlebars.HandlebarsStandardMojo;
import com.github.terefang.template_maven_plugin.handlebars.HandlebarsTemplateMojo;
import com.github.terefang.template_maven_plugin.jexl.JexlJxtStandardMojo;
import com.github.terefang.template_maven_plugin.jexl.JexlJxtTemplateMojo;
import com.github.terefang.template_maven_plugin.jinjava.JinjavaStandardMojo;
import com.github.terefang.template_maven_plugin.jinjava.JinjavaTemplateMojo;
import com.github.terefang.template_maven_plugin.thymeleaf.ThymeleafStandardMojo;
import com.github.terefang.template_maven_plugin.thymeleaf.ThymeleafTemplateMojo;
import com.github.terefang.template_maven_plugin.trimou.TrimouStandardMojo;
import com.github.terefang.template_maven_plugin.trimou.TrimouTemplateMojo;
import com.github.terefang.template_maven_plugin.velocity.VelocityStandardMojo;
import com.github.terefang.template_maven_plugin.velocity.VelocityTemplateMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import picocli.CommandLine;
import java.io.File;

public class TemplateCliMain
{
    public static void main(String[] _args)
    {
        TemplateCliOptions _opts = new TemplateCliOptions();
        CommandLine _cmd = new CommandLine(_opts);

        if(_args.length == 0 || "--help".equalsIgnoreCase(_args[0]) || "-help".equalsIgnoreCase(_args[0]) || "-h".equalsIgnoreCase(_args[0]))
        {
            _cmd.usage(System.out);
            System.exit(-1);
        }
        else
        if(_args.length == 3 && "--to-json".equalsIgnoreCase(_args[0]))
        {
            ToJsonMojo.convertToJson(new File(_args[1]), new File(_args[2]));
            System.exit(0);
        }
        else
        if(_args.length == 2 && "--to-json".equalsIgnoreCase(_args[0]))
        {
            ToJsonMojo.convertToJson(new File(_args[1]), null);
            System.exit(0);
        }
        else
        if(_args.length == 3 && "--to-hson".equalsIgnoreCase(_args[0]))
        {
            ToHsonMojo.convertToHson(new File(_args[1]), new File(_args[2]));
            System.exit(0);
        }
        else
        if(_args.length == 2 && "--to-hson".equalsIgnoreCase(_args[0]))
        {
            ToHsonMojo.convertToHson(new File(_args[1]), null);
            System.exit(0);
        }
        else
        if(_args.length == 3 && "--to-pdata".equalsIgnoreCase(_args[0]))
        {
            ToPdataMojo.convertToPdata(new File(_args[1]), new File(_args[2]));
            System.exit(0);
        }
        else
        if(_args.length == 2 && "--to-pdata".equalsIgnoreCase(_args[0]))
        {
            ToPdataMojo.convertToPdata(new File(_args[1]), null);
            System.exit(0);
        }

        _cmd.parseArgs(_args);

        TemplateCliLogger _log = new TemplateCliLogger();

        AbstractStandardMojo _smojo = null;
        AbstractTemplateMojo _tmojo = null;

        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.JINJAVA))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new JinjavaStandardMojo();
            }
            else
            {
                _tmojo = new JinjavaTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.JEXL))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new JexlJxtStandardMojo();
            }
            else
            {
                _tmojo = new JexlJxtTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.GROOVY))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new GroovySimpleStandardMojo();
            }
            else
            {
                _tmojo = new GroovySimpleTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.THYMELEAF))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new ThymeleafStandardMojo();
            }
            else
            {
                _tmojo = new ThymeleafTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.FREEMARKER))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new FreeMarkerStandardMojo();
            }
            else
            {
                _tmojo = new FreeMarkerTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TRIMOU))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new TrimouStandardMojo();
            }
            else
            {
                _tmojo = new TrimouTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.HANDLEBARS))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new HandlebarsStandardMojo();
            }
            else
            {
                _tmojo = new HandlebarsTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.VELOCITY))
        {
            if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                    || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
            {
                _smojo = new VelocityStandardMojo();
            }
            else
            {
                _tmojo = new VelocityTemplateMojo();
            }
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.PREPROCESSOR))
        {
            executePreProcessor(_log, _opts);
            return;
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.CONCAT))
        {
            executeConcat(_log, _opts);
            return;
        }
        else
        if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TOJSON)
                || _opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TOHSON)
                || _opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TOPDATA))
        {
            executeConvert(_log, _opts);
            return;
        }

        if(_opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STANDARD)
                || _opts.getDoMode().equals(TemplateCliOptions.TemplateEngineMode.STD))
        {
            _smojo.setLog(_log);
            executeTemplateStandardMojo(_smojo, _opts);
        }
        else
        {
            _tmojo.setLog(_log);
            executeTemplateTemplateMojo(_tmojo, _opts);
        }
    }

    public static void executePreProcessor(TemplateCliLogger _log, TemplateCliOptions _opts)
    {
        try
        {
            PreProcessorMojo _mojo = new PreProcessorMojo();
            _mojo.setLog(_log);

            _mojo.setIncludes(_opts.getIncludes());
            _mojo.setExcludes(_opts.getExcludes());
            _mojo.setResourcesDirectory(_opts.getResourcesDirectory());
            _mojo.setResourcesOutput(_opts.getResourcesOutput());
            _mojo.setFlattenOutput(_opts.isFlattenOutput());
            _mojo.setDestinationExtension(_opts.getDestinationExtension());

            _mojo.setSingleFileOutput(_opts.isSingleFileOutput());
            _mojo.setMarker(_opts.getProcessMarker());
            _mojo.setProcessIncludes(_opts.isProcessIncludes());
            _mojo.setProcessSingleLineMarker(_opts.isProcessSingleLineMarker());

            _mojo.execute();
        }
        catch (MojoExecutionException | MojoFailureException _me)
        {
            _log.error(_me.getMessage(), _me);
        }
    }

    public static void executeConvert(TemplateCliLogger _log, TemplateCliOptions _opts)
    {
        try
        {
            if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TOJSON))
            {
                ToJsonMojo _mojo = new ToJsonMojo();
                _mojo.setLog(_log);

                _mojo.setIncludes(_opts.getIncludes());
                _mojo.setExcludes(_opts.getExcludes());
                _mojo.setResourcesDirectory(_opts.getResourcesDirectory());
                _mojo.setResourcesOutput(_opts.getResourcesOutput());
                _mojo.setFlattenOutput(_opts.isFlattenOutput());
                _mojo.setDestinationExtension(_opts.getDestinationExtension());

                _mojo.execute();
            }
            else
            if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TOHSON))
            {
                ToHsonMojo _mojo = new ToHsonMojo();
                _mojo.setLog(_log);

                _mojo.setIncludes(_opts.getIncludes());
                _mojo.setExcludes(_opts.getExcludes());
                _mojo.setResourcesDirectory(_opts.getResourcesDirectory());
                _mojo.setResourcesOutput(_opts.getResourcesOutput());
                _mojo.setFlattenOutput(_opts.isFlattenOutput());
                _mojo.setDestinationExtension(_opts.getDestinationExtension());

                _mojo.execute();
            }
            else
            if(_opts.getDoEngine().equals(TemplateCliOptions.TemplateEngineName.TOPDATA))
            {
                ToPdataMojo _mojo = new ToPdataMojo();
                _mojo.setLog(_log);

                _mojo.setIncludes(_opts.getIncludes());
                _mojo.setExcludes(_opts.getExcludes());
                _mojo.setResourcesDirectory(_opts.getResourcesDirectory());
                _mojo.setResourcesOutput(_opts.getResourcesOutput());
                _mojo.setFlattenOutput(_opts.isFlattenOutput());
                _mojo.setDestinationExtension(_opts.getDestinationExtension());

                _mojo.execute();
            }
        }
        catch (MojoExecutionException | MojoFailureException _me)
        {
            _log.error(_me.getMessage(), _me);
        }
    }

    public static void executeConcat(TemplateCliLogger _log, TemplateCliOptions _opts)
    {
        try
        {
            ConcatMojo _mojo = new ConcatMojo();
            _mojo.setLog(_log);

            _mojo.setIncludes(_opts.getIncludes());
            _mojo.setExcludes(_opts.getExcludes());
            _mojo.setResourcesDirectory(_opts.getResourcesDirectory());
            _mojo.setResourcesOutput(_opts.getResourcesOutput());

            _mojo.execute();
        }
        catch (MojoExecutionException | MojoFailureException _me)
        {
            _log.error(_me.getMessage(), _me);
        }
    }

    public static void setSharedMojoParameters(AbstractTmpMojo _mojo, TemplateCliOptions _opts)
    {
        _mojo.setOutputType(_opts.getOutputType().toString());
        _mojo.setAdditionalContext(_opts.getAdditionalContext());
        _mojo.setAdditionalContextRoot(_opts.getAdditionalContextRoot());
        _mojo.setAdditionalVariables(_opts.getAdditionalVariables());
        if(_mojo instanceof AbstractTemplateMojo)
        {
            ((AbstractTemplateMojo)_mojo).setIncludes(_opts.getIncludes());
            ((AbstractTemplateMojo)_mojo).setExcludes(_opts.getExcludes());
            ((AbstractTemplateMojo)_mojo).setResourcesDirectory(_opts.getResourcesDirectory());
            ((AbstractTemplateMojo)_mojo).setResourcesOutput(_opts.getResourcesOutput());
            ((AbstractTemplateMojo)_mojo).setFlattenOutput(_opts.isFlattenOutput());
        }
        else
        {
            ((AbstractStandardMojo)_mojo).setIncludes(_opts.getIncludes());
            ((AbstractStandardMojo)_mojo).setExcludes(_opts.getExcludes());
            ((AbstractStandardMojo)_mojo).setResourcesDirectory(_opts.getResourcesDirectory());
            ((AbstractStandardMojo)_mojo).setResourcesOutput(_opts.getResourcesOutput());
            ((AbstractStandardMojo)_mojo).setFlattenOutput(_opts.isFlattenOutput());
        }
    }

    public static void executeTemplateTemplateMojo(AbstractTemplateMojo _mojo, TemplateCliOptions _opts)
    {
        try
        {
            setSharedMojoParameters(_mojo, _opts);

            _mojo.setTemplateFile(_opts.getTemplateFile());
            _mojo.setDestinationExtension(_opts.getDestinationExtension());

            _mojo.execute();
        }
        catch (MojoExecutionException _me)
        {
            _mojo.getLog().error(_me.getMessage(), _me);
        }
    }

    public static void executeTemplateStandardMojo(AbstractStandardMojo _mojo, TemplateCliOptions _opts)
    {
        try
        {
            setSharedMojoParameters(_mojo, _opts);

            _mojo.setProcessLocalContext(_opts.isProcessLocalContext());
            _mojo.setLocalContextRoot(_opts.getLocalContextRoot());
            _mojo.setLocalContextExtensions(_opts.getLocalContextExtensions());

            _mojo.execute();
        }
        catch (MojoExecutionException _me)
        {
            _mojo.getLog().error(_me.getMessage(), _me);
        }
    }
}

package com.github.terefang.template_cli;

import picocli.CommandLine;

import java.io.File;

public class TemplateCliOptions
{
    public static enum TemplateEngineName { THYMELEAF, JINJAVA, JEXL, GROOVY, FREEMARKER, TRIMOU, HANDLEBARS, VELOCITY }

    @CommandLine.Option(order = 10, names = {"-T", "--template-engine"}, paramLabel = "ENGINE", description = "name of the templating engine, ${COMPLETION-CANDIDATES}", required = true)
    public TemplateEngineName doEngine;

    public static enum TemplateEngineMode { STD, STANDARD, TEMPLATE }
    @CommandLine.Option(order = 20, names = {"-M", "--engine-mode"}, paramLabel = "MODE", description = "mode of engine operation, ${COMPLETION-CANDIDATES}", required = true)
    public TemplateEngineMode doMode;


    /* TMP variables */

    public static enum OutputType { TEXT, XML, HTML, JAVASCRIPT }
    @CommandLine.Option(order = 30, names = {"-O", "--output-type"}, paramLabel = "TYPE", description = "type of output, ${COMPLETION-CANDIDATES}", required = false, defaultValue = "TEXT")
    public OutputType outputType;

    @CommandLine.Option(order = 40, names = {"--additional-context-file"}, paramLabel = "FILE", description = "file containing additional context definitions", required = false)
    public File additionalContext;

    @CommandLine.Option(order = 50, names = {"--additional-context-root"}, paramLabel = "ROOT", description = "root-tag of additional context, default: 'context'", defaultValue = "context", required = false)
    public String additionalContextRoot;

    @CommandLine.Option(order = 60, names = {"--additional-variables"}, paramLabel = "KVs", description = "additional variables", defaultValue = "", required = false)
    public String additionalVariables;


    /* shared Variables */

    @CommandLine.Option(order = 70, names = {"-I", "--include"}, paramLabel = "ANTPATH", description = "include specification", required = false)
    private String[] includes;

    @CommandLine.Option(order = 80, names = {"-X", "--exclude"}, paramLabel = "ANTPATH", description = "exclude specification", required = false)
    private String[] excludes;

    @CommandLine.Option(order = 90, names = {"-S", "--src-dir", "--source-directory"}, paramLabel = "DIR", description = "source directory", required = false)
    private File resourcesDirectory;

    @CommandLine.Option(order = 100, names = {"-D", "--dest-dir", "--destination-directory"}, paramLabel = "DIR", description = "destination directory", required = false)
    private File resourcesOutput;


    /* Template Variables */

    @CommandLine.Option(order = 110, names = {"-E", "--dest-ext", "--destination-extension"}, paramLabel = "EXTENSION", description = "destination extension for template mode, default .txt", required = false, defaultValue = ".txt")
    private String destinationExtension;

    @CommandLine.Option(order = 120, names = {"-F", "--tmpl", "--template-file"}, paramLabel = "FILE", description = "global template file for template mode", required = false)
    private File templateFile;


    /* Standard Variables */

    @CommandLine.Option(order = 130, names = {"-L", "--process-local-context"}, description = "option to process local context files for templates for standard mode, default false", required = false, defaultValue = "false")
    protected boolean processLocalContext;

    @CommandLine.Option(order = 140, names = {"-l", "--local-context-extensions"}, paramLabel = "EXT-list", description = "local context file extension to check for standard mode, default: .yaml .yml .json .hson .hjson .toml .tml", required = false, defaultValue = ".yaml .yml .json .hson .hjson .toml .tml")
    protected String localContextExtensions;

    @CommandLine.Option(order = 150, names = {"--local-context-root"}, paramLabel = "ROOT", description = "root-tag of local context, default: 'local'", defaultValue = "local", required = false)
    protected String localContextRoot;

    public TemplateEngineName getDoEngine() {
        return doEngine;
    }

    public TemplateEngineMode getDoMode() {
        return doMode;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public File getAdditionalContext() {
        return additionalContext;
    }

    public String getAdditionalContextRoot() {
        return additionalContextRoot;
    }

    public String getAdditionalVariables() {
        return additionalVariables;
    }

    public String[] getIncludes() {
        return includes;
    }

    public String[] getExcludes() {
        return excludes;
    }

    public File getResourcesDirectory() {
        return resourcesDirectory;
    }

    public File getResourcesOutput() {
        return resourcesOutput;
    }

    public String getDestinationExtension() {
        return destinationExtension;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public boolean isProcessLocalContext() {
        return processLocalContext;
    }

    public String getLocalContextExtensions() {
        return localContextExtensions;
    }

    public String getLocalContextRoot() {
        return localContextRoot;
    }
}

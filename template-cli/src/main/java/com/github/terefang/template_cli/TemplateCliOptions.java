package com.github.terefang.template_cli;

import lombok.Data;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

@Data
public class TemplateCliOptions
{
    public static enum TemplateEngineName { THYMELEAF, JINJAVA, JXLT, JEXL, GSIMPLE, GROOVY, JEXL_ESP, FREEMARKER, TRIMOU, PREPROCESSOR, CONCAT, ToJSON, ToHSON, ToPDATA, SCRIPT}

    @CommandLine.Option(order = 10, names = {"-T", "--template-engine"}, paramLabel = "ENGINE", description = "name of the templating engine, ${COMPLETION-CANDIDATES}", required = true)
    public TemplateEngineName doEngine;

    public static enum TemplateEngineMode { STD, STANDARD, TEMPLATE, SCRIPT }
    @CommandLine.Option(order = 20, names = {"-M", "--engine-mode"}, paramLabel = "MODE", description = "mode of engine operation, ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})", required = false, defaultValue = "STD")
    public TemplateEngineMode doMode;

    /* TMP variables */

    public static enum OutputType { TEXT, XML, HTML, JAVASCRIPT, SVG, PNG, PDF, XLS, XLSX, CSV, BIN }
    @CommandLine.Option(order = 30, names = {"-O", "--output-type"}, paramLabel = "TYPE", description = "type of output, ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})", required = false, defaultValue = "TEXT")
    public OutputType outputType;

    @CommandLine.Option(order = 40, names = {"--additional-context-file"}, paramLabel = "FILE", description = "file containing additional context definitions", required = false)
    public File additionalContext;

    @CommandLine.Option(order = 50, names = {"--additional-context-root"}, paramLabel = "ROOT", description = "root-tag of additional context (default: ${DEFAULT-VALUE})", defaultValue = "context", required = false)
    public String additionalContextRoot;

    @CommandLine.Option(order = 60, names = {"--additional-variables"}, paramLabel = "KVs", description = "additional variables", defaultValue = "", required = false)
    public String additionalVariables;

    @CommandLine.Option(order = 65, names = {"--jdbc-url"}, paramLabel = "JDBCURL", defaultValue = "", required = false)
    public String jdbcUrl;

    @CommandLine.Option(order = 65, names = {"--jdbc-user"}, paramLabel = "USER", defaultValue = "", required = false)
    public String jdbcUserName;

    @CommandLine.Option(order = 65, names = {"--jdbc-pass"}, paramLabel = "PASS", defaultValue = "", required = false)
    public String jdbcPassWord;

    @CommandLine.Option(order = 65, names = {"--jdbc-driver"}, paramLabel = "DRIVER", defaultValue = "", required = false)
    public String jdbcDriver;

    /* shared Variables */

    @CommandLine.Option(order = 70, names = {"-I", "--include"}, paramLabel = "ANTPATH", description = "include specification", required = false)
    private String includes;

    @CommandLine.Option(order = 80, names = {"-X", "--exclude"}, paramLabel = "ANTPATH", description = "exclude specification", required = false)
    private String excludes;

    @CommandLine.Option(order = 90, names = {"-S", "--src-dir", "--source-directory", "--script", "--script-file"}, paramLabel = "DIR/SCRIPT", description = "source directory/script", required = false)
    private File resourcesDirectory;

    @CommandLine.Option(order = 100, names = {"-D", "--dest-dir", "--destination-directory", "--destination"}, paramLabel = "DIR", description = "destination directory/file", required = false)
    private File resourcesOutput;

    @CommandLine.Option(order = 101, names = {"--flatten"}, paramLabel = "DIR", description = "flatten destination directories", required = false)
    private boolean flattenOutput;

    /* Template Variables */

    @CommandLine.Option(order = 110, names = {"-E", "--dest-ext", "--destination-extension"}, paramLabel = "EXTENSION", description = "destination extension for template mode, default .txt", required = false, defaultValue = ".txt")
    private String destinationExtension;

    @CommandLine.Option(order = 120, names = {"-F", "--tmpl", "--template-file"}, paramLabel = "FILE", description = "global template file for template mode", required = false)
    private File templateFile;

    @CommandLine.Option(order = 125, names = {"--context-root", "--global-context-root"}, paramLabel = "ROOT", description = "root-tag of global context, if any, default: ''", defaultValue = "", required = false)
    String globalContextRoot;

    /* Standard Variables */

    @CommandLine.Option(order = 130, names = {"-L", "--read-local-context"}, description = "option to read local context files for templates for standard mode (default: ${DEFAULT-VALUE})", required = false, defaultValue = "false")
    protected boolean processLocalContext;

    @CommandLine.Option(order = 140, names = {"-l", "--local-context-extensions"}, paramLabel = "EXT-list", description = "local context file extension to check for standard mode (default: ${DEFAULT-VALUE})", required = false, defaultValue = ".props .properties .yaml .yml .json .hson .hjson .tml .toml .ini .pdx .pdata .sqlite.csv .list .scsv .csv .tsv .txt")
    protected String localContextExtensions;

    @CommandLine.Option(order = 150, names = {"--local-context-root"}, paramLabel = "ROOT", description = "root-tag of local context (default: ${DEFAULT-VALUE})", defaultValue = "local", required = false)
    protected String localContextRoot;

    /* Pre-Proc Variables */

    @CommandLine.Option(order = 160, names = {"--marker", "--process-marker"}, paramLabel = "MARKER", description = "option to pre-process MARKER begin/end (default: ${DEFAULT-VALUE})", required = false, defaultValue = "MARK")
    protected String processMarker;

    @CommandLine.Option(order = 140, names = {"--process-includes"}, description = "do pre-process include statements (default: ${DEFAULT-VALUE})", required = false, defaultValue = "false")
    protected boolean processIncludes;

    @CommandLine.Option(order = 140, names = {"--single-line-marker", "--process-single-line-marker"}, description = "do pre-process single-line markers (default: ${DEFAULT-VALUE})", required = false, defaultValue = "false")
    protected boolean processSingleLineMarker;

    @CommandLine.Option(order = 140, names = {"--single-file", "--process-single-file-output"}, description = "do pre-process into single file (default: ${DEFAULT-VALUE})", required = false, defaultValue = "false")
    protected boolean singleFileOutput;

    /* groovy variable */

    @CommandLine.Option(order = 150, names = {"--inc-path", "--include-path"}, description = "include paths for groovy/luaj script (default: ${DEFAULT-VALUE})", required = false, defaultValue = ".")
    protected List<String> includePath;

    @CommandLine.Unmatched
    protected List<String> arguments;
}



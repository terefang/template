package com.github.terefang.template_cli;

import lombok.Data;
import org.apache.maven.plugin.logging.Log;

@Data
public class TemplateCliLogger implements Log
{
    boolean debugEnabled;
    boolean infoEnabled;
    boolean warnEnabled;
    boolean errorEnabled;

    @Override
    public void debug(CharSequence charSequence) {
        System.err.print("DEBUG: ");
        System.err.println(charSequence);
    }

    @Override
    public void debug(CharSequence charSequence, Throwable throwable) {
        debug(charSequence);
        debug(throwable);
    }

    @Override
    public void debug(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    @Override
    public void info(CharSequence charSequence) {
        System.err.print("INFO: ");
        System.err.println(charSequence);
    }

    @Override
    public void info(CharSequence charSequence, Throwable throwable) {
        info(charSequence);
        info(throwable);
    }

    @Override
    public void info(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    @Override
    public void warn(CharSequence charSequence) {
        System.err.print("WARN: ");
        System.err.println(charSequence);
    }

    @Override
    public void warn(CharSequence charSequence, Throwable throwable) {
        warn(charSequence);
        warn(throwable);
    }

    @Override
    public void warn(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    @Override
    public void error(CharSequence charSequence) {
        System.err.print("ERROR: ");
        System.err.println(charSequence);
    }

    @Override
    public void error(CharSequence charSequence, Throwable throwable) {
        error(charSequence);
        error(throwable);
    }

    @Override
    public void error(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }


}

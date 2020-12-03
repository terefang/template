package com.github.terefang.template_cli;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.logging.Log;

@Data
@Slf4j
public class TemplateCliLogger implements Log
{
    boolean debugEnabled;
    boolean infoEnabled;
    boolean warnEnabled;
    boolean errorEnabled;

    @Override
    public void debug(CharSequence charSequence)
    {
        log.debug(charSequence.toString());
        //System.err.print("DEBUG: ");
        //System.err.println(charSequence);
    }

    @Override
    public void debug(CharSequence charSequence, Throwable throwable)
    {
        log.debug(charSequence.toString(), throwable);
        //debug(charSequence);
        //debug(throwable);
    }

    @Override
    public void debug(Throwable throwable)
    {
        log.debug(throwable.getMessage(), throwable);
        //throwable.printStackTrace(System.err);
    }

    @Override
    public void info(CharSequence charSequence)
    {
        log.info(charSequence.toString());
        //System.err.print("INFO: ");
        //System.err.println(charSequence);
    }

    @Override
    public void info(CharSequence charSequence, Throwable throwable)
    {
        log.info(charSequence.toString(), throwable);
        //info(charSequence);
        //info(throwable);
    }

    @Override
    public void info(Throwable throwable)
    {
        log.info(throwable.getMessage(), throwable);
        //throwable.printStackTrace(System.err);
    }

    @Override
    public void warn(CharSequence charSequence)
    {
        log.warn(charSequence.toString());
        //System.err.print("WARN: ");
        //System.err.println(charSequence);
    }

    @Override
    public void warn(CharSequence charSequence, Throwable throwable)
    {
        log.warn(charSequence.toString(), throwable);
        //warn(charSequence);
        //warn(throwable);
    }

    @Override
    public void warn(Throwable throwable)
    {
        log.warn(throwable.getMessage(), throwable);
        //throwable.printStackTrace(System.err);
    }

    @Override
    public void error(CharSequence charSequence)
    {
        log.error(charSequence.toString());
        //System.err.print("ERROR: ");
        //System.err.println(charSequence);
    }

    @Override
    public void error(CharSequence charSequence, Throwable throwable)
    {
        log.error(charSequence.toString(), throwable);
        //error(charSequence);
        //error(throwable);
    }

    @Override
    public void error(Throwable throwable)
    {
        log.error(throwable.getMessage(), throwable);
        //throwable.printStackTrace(System.err);
    }


}

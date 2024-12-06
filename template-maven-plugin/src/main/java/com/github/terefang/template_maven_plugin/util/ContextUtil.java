package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.jmelange.dao.JDAO;
import com.github.terefang.jmelange.dao.utils.JdaoUtils;
import com.github.terefang.jmelange.data.*;

import com.github.terefang.jmelange.script.ScriptHelper;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.text.CaseUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.codehaus.plexus.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ContextUtil extends ScriptHelper
{
    DataUtil dataUtil;

    public static Map<String, Object> loadContextFrom(String _file) {
        return DataUtil.loadContextFrom(_file);
    }

    public static List<Map<String, Object>> loadRowsFrom(String _file) {
        return DataUtil.loadRowsFrom(_file);
    }

    public static Map<String, Object> loadContextFrom(File _file) {
        return DataUtil.loadContextFrom(_file);
    }

    public static List<Map<String, Object>> loadRowsFrom(File _file) {
        return DataUtil.loadRowsFrom(_file);
    }

    public static Map<String, Object> loadContextFromIni(InputStream _fh) {
        return DataUtil.loadContextFromIni(_fh);
    }

    public static Map<String, Object> loadContextFromType(String _type, InputStream _fh) {
        return DataUtil.loadContextFromType(_type, _fh);
    }

    public static Map<String, Object> loadContextFromType(String _type, File _fh) {
        return DataUtil.loadContextFromType(_type, _fh);
    }

    public static List<Map<String, Object>> loadRowsFromType(String _type, InputStream _fh) {
        return DataUtil.loadRowsFromType(_type, _fh);
    }

    public static List<Map<String, Object>> loadRowsFromType(String _type, File _fh) {
        return DataUtil.loadRowsFromType(_type, _fh);
    }

    public static String unCompressedFilename(File _res) {
        return DataUtil.unCompressedFilename(_res);
    }

    public static InputStream getStreamBySuffix(File _res) {
        return DataUtil.getStreamBySuffix(_res);
    }

    public static Map<String, ?> loadContextFromTxt(InputStream _source) {
        return DataUtil.loadContextFromTxt(_source);
    }

    public static List<String> loadFileLines(InputStream _source) {
        return DataUtil.loadFileLines(_source, StandardCharsets.UTF_8);
    }

    public static Map<String, Object> loadContextFromTsv(InputStream _source) {
        return DataUtil.loadContextFromTsv(_source);
    }

    public static Map<String, Object> loadContextFromCsv(InputStream _source) {
        return DataUtil.loadContextFromCsv(_source);
    }

    public static Map<String, Object> loadContextFromScsv(InputStream _source) {
        return DataUtil.loadContextFromScsv(_source);
    }

    public static Map<String, Object> loadContextFromSqliteCsv(InputStream _source) {
        return DataUtil.loadContextFromSqliteCsv(_source);
    }

    public static Map<String, Object> loadContextFromSqliteList(InputStream _source) {
        return DataUtil.loadContextFromSqliteList(_source);
    }

    public static List<Map<String, Object>> readFileCsv(String _infmt, InputStream _in, Charset _cs) {
        return DataUtil.readFileCsv(_infmt, _in, _cs);
    }

    public static Map<String, Object> loadContextFromPdx(InputStream _source) {
        return DataUtil.loadContextFromPdx(_source);
    }

    public static Map<String, Object> loadContextFromPdata(InputStream _source) {
        return DataUtil.loadContextFromPdata(_source);
    }

    public static Map<String, Object> fromPdx(String _source) {
        return DataUtil.fromPdx(_source);
    }

    public static Map<String, Object> fromPdata(String _source) {
        return DataUtil.fromPdata(_source);
    }

    public static Map<String, ?> loadContextFromProperties(InputStream _source) {
        return DataUtil.loadContextFromProperties(_source);
    }

    public static Map<String, Object> loadContextFromYaml(InputStream _source) {
        return DataUtil.loadContextFromYaml(_source);
    }

    public static Map<String, Object> fromYaml(String _source) {
        return DataUtil.fromYaml(_source);
    }

    public static void writeContextAsType(String _type, Map<String, Object> _data, OutputStream _fh) {
        DataUtil.writeContextAsType(_type, _data, _fh);
    }

    public static void writeContextAsType(String _type, Map<String, Object> _data, Writer _fh) {
        DataUtil.writeContextAsType(_type, _data, _fh);
    }

    public static void writeContextAsType(String _type, Map<String, Object> _data, File _fh) {
        DataUtil.writeContextAsType(_type, _data, _fh);
    }

    public static void writeRowsAsType(String _type, List<Map<String, Object>> _data, OutputStream _fh) {
        DataUtil.writeRowsAsType(_type, _data, _fh);
    }

    public static void writeRowsAsType(String _type, List<Map<String, Object>> _data, File _fh) {
        DataUtil.writeRowsAsType(_type, _data, _fh);
    }

    public static void writeAsHson(boolean _json, Writer _out, List<Map<String, Object>> _res) {
        DataUtil.writeAsHson(_json, _out, _res);
    }

    public static void writeAsHson(boolean _json, Writer _out, Map<String, Object> _res) {
        DataUtil.writeAsHson(_json, _out, _res);
    }

    public static String toHson(Map<String, Object> _res) {
        return DataUtil.toHson(_res);
    }

    public static String toJson(Map<String, Object> _res) {
        return DataUtil.toJson(_res);
    }

    public static void writeAsPdata(Writer _out, Map<String, Object> _res) {
        DataUtil.writeAsPdata(_out, _res);
    }

    public static String toPdata(Map<String, Object> _res) {
        return DataUtil.toPdata(_res);
    }

    public static Map<String, Object> loadContextFromHjson(InputStream _source) {
        return DataUtil.loadContextFromHjson(_source);
    }

    public static Map<String, Object> fromJson(String _source) {
        return DataUtil.fromJson(_source);
    }

    public static Map<String, Object> fromHjson(String _source) {
        return DataUtil.fromHjson(_source);
    }

    public static void writeAsYaml(OutputStream _out, Map<String, Object> _res)
    {
        writeContextAsType("yaml", _res, _out);
    }

    public static void writeAsYaml(Writer _out, Map<String, Object> _res)
    {
        writeContextAsType("yaml", _res, _out);
    }

    public static void writeAsYaml(File _out, Map<String, Object> _res)
    {
        writeContextAsType("yaml", _res, _out);
    }

    public static void writeAsJsonPerLine(Writer _out, List<Map<String, Object>> _res) {
        DataUtil.writeAsJsonPerLine(_out, _res);
    }

    public static String toJsonPerLine(List<Map<String, Object>> _res) {
        return DataUtil.toJsonPerLine(_res);
    }

    public static List<Map<String, Object>> loadRowsFromJsonPerLine(InputStream _source) {
        return DataUtil.loadRowsFromJsonPerLine(_source);
    }

    public static List<Map<String, Object>> fromJsonPerLine(String _source) {
        return DataUtil.fromJsonPerLine(_source);
    }

    /*----- image helper -----*/
    /* TODO
    public static PixelImage pixImage(int _w, int _h) { return png(_w,_h); }

    public static SvgImage svgImage(int _w, int _h) { return svg(_w,_h); }

    public static PdfImage pdfImage(int _w, int _h) { return pdf(_w,_h); }
    */

    /*----- WordUtils -----*/

    public static String toCamelCase(String str, boolean capitalizeFirstLetter, char... delimiters) {
        return CaseUtils.toCamelCase(str, capitalizeFirstLetter, delimiters);
    }

    public static String toCamelCase(String str) {
        return CaseUtils.toCamelCase(str, false);
    }


    /*----- StringEscapeUtils -----*/

    public static String escapeJava(String input) {
        return StringEscapeUtils.escapeJava(input);
    }

    public static String escapeEcmaScript(String input) {
        return StringEscapeUtils.escapeEcmaScript(input);
    }

    public static String escapeJson(String input) {
        return StringEscapeUtils.escapeJson(input);
    }

    public static String unescapeJava(String input) {
        return StringEscapeUtils.unescapeJava(input);
    }

    public static String unescapeEcmaScript(String input) {
        return StringEscapeUtils.unescapeEcmaScript(input);
    }

    public static String unescapeJson(String input) {
        return StringEscapeUtils.unescapeJson(input);
    }

    public static String escapeHtml4(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }

    public static String escapeHtml3(String input) {
        return StringEscapeUtils.escapeHtml3(input);
    }

    public static String unescapeHtml4(String input) {
        return StringEscapeUtils.unescapeHtml4(input);
    }

    public static String unescapeHtml3(String input) {
        return StringEscapeUtils.unescapeHtml3(input);
    }

    public static String escapeXml(String input) {
        return StringEscapeUtils.escapeXml10(input);
    }

    public static String escapeXml10(String input) {
        return StringEscapeUtils.escapeXml10(input);
    }

    public static String escapeXml11(String input) {
        return StringEscapeUtils.escapeXml11(input);
    }

    public static String unescapeXml(String input) {
        return StringEscapeUtils.unescapeXml(input);
    }

    public static String escapeCsv(String input) {
        return StringEscapeUtils.escapeCsv(input);
    }

    public static String unescapeCsv(String input) {
        return StringEscapeUtils.unescapeCsv(input);
    }

    // URL codec

    @SneakyThrows
    public static String encodeUrl(String _raw)
    {
        return new String(URLCodec.encodeUrl(null, _raw.getBytes()));
    }

    @SneakyThrows
    public static String decodeUrl(String _cooked)
    {
        return new String(URLCodec.decodeUrl( _cooked.getBytes()));
    }

    // dao


    @SneakyThrows
    public static JDAO daoFromJdbc(String jdbcDriver, String _url, String _user, String _pass)
    {
        return JDAO.createDaoFromConnection(JdaoUtils.createConnectionByDriverSpec(StringUtils.isNotEmpty(jdbcDriver) ? jdbcDriver : null, _url, _user, _pass), true);
    }

    public static JDAO mysqlDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("com.mysql.jdbc.Driver", "jdbc:mysql://"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_MYSQL);
        return _dao;
    }

    public static JDAO mysqlDao(boolean mysqlCJvsMariaDb, String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc(mysqlCJvsMariaDb ? "com.mysql.cj.jdbc.Driver" : "org.mariadb.jdbc.Driver", "jdbc:mysql://"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_MYSQL);
        return _dao;
    }

    public static JDAO sqliteDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("", "jdbc:sqlite:"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_SQLITE);
        return _dao;
    }

    public static JDAO pgsqlDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("org.postgresql.Driver", "jdbc:postgresql://"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_POSTGRES);
        return _dao;
    }

    public static JDAO mariadbDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("org.mariadb.jdbc.Driver", "jdbc:mariadb://"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_MYSQL);
        return _dao;
    }

    public static JDAO mssqlDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver://"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_MSSQL);
        return _dao;
    }

    public static JDAO jtdsDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:"+_hostPortDb, _user, _pass);
        _dao.setDbType(JDAO.DB_TYPE_SYBASE);
        return _dao;
    }


}

package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.jdao.JDAO;
import com.github.terefang.jdao.JdaoUtils;
import com.moandjiezana.toml.Toml;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;
import org.hjson.JsonArray;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.ini4j.Ini;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class ContextUtil {

    @SneakyThrows
    public static Map<String, Object> loadContextFrom(File _file) throws MojoExecutionException
    {
        Map<String, Object> _ret = new HashMap<>();
        InputStream _fh = getStreamBySuffix(_file);
        String _fn = unCompressedFilename(_file);

        if(_fn.endsWith(".yaml")
            || _fn.endsWith(".yml"))
        {
            _ret.putAll(loadContextFromYaml(_fh));
        }
        else
        if(_fn.endsWith(".json")
                || _fn.endsWith(".hson")
                || _fn.endsWith(".hjson"))
        {
            _ret.putAll(loadContextFromHjson(_fh));
        }
        else
        if(_fn.endsWith(".tml")
                || _fn.endsWith(".toml"))
        {
            _ret.putAll(loadContextFromToml(_fh));
        }
        else
        if(_fn.endsWith(".ini"))
        {
            _ret.putAll(loadContextFromIni(_fh));
        }
        else
        if(_fn.endsWith(".pdx")
                || _fn.endsWith(".pdata") )
        {
            _ret.putAll(loadContextFromPdx(_fh));
        }
        else
        if(_fn.endsWith(".sqlite.csv"))
        {
            _ret.putAll(loadContextFromSqliteCsv(_fh));
        }
        else
        if(_fn.endsWith(".list"))
        {
            _ret.putAll(loadContextFromSqliteList(_fh));
        }
        else
        if(_fn.endsWith(".scsv"))
        {
            _ret.putAll(loadContextFromScsv(_fh));
        }
        else
        if(_fn.endsWith(".csv"))
        {
            _ret.putAll(loadContextFromCsv(_fh));
        }
        else
        if(_fn.endsWith(".tsv"))
        {
            _ret.putAll(loadContextFromTsv(_fh));
        }
        else
        {
            throw new MojoExecutionException(MessageFormat.format("Context file '{0}' is unknown format", _file.getName()));
        }
        IOUtil.close(_fh);
        return _ret;
    }

    public static String unCompressedFilename(File _res)
    {
        String _fn = _res.getName();
        String _ext = FileUtils.getExtension(_fn);
        if("gz".equalsIgnoreCase(_ext))
        {
            return _fn.substring(0, _fn.length()-3);
        }
        else
        if("zst".equalsIgnoreCase(_ext))
        {
            return _fn.substring(0, _fn.length()-4);
        }
        else
        if("zstd".equalsIgnoreCase(_ext))
        {
            return _fn.substring(0, _fn.length()-5);
        }
        else
        if("xz".equalsIgnoreCase(_ext))
        {
            return _fn.substring(0, _fn.length()-3);
        }
        else
        if("bz2".equalsIgnoreCase(_ext))
        {
            return _fn.substring(0, _fn.length()-4);
        }
        return _fn;
    }

    @SneakyThrows
    public static InputStream getStreamBySuffix(File _res)
    {
        InputStream _stream = new FileInputStream(_res);
        String _ext = FileUtils.getExtension(_res.getName());
        if("gz".equalsIgnoreCase(_ext))
        {
        //    _stream = new GzipCompressorInputStream(_stream);
        }
        else
        if("zstd".equalsIgnoreCase(_ext) || "zst".equalsIgnoreCase(_ext))
        {
        //    _stream = new ZstdCompressorInputStream(_stream);
        }
        else
        if("xz".equalsIgnoreCase(_ext))
        {
        //    _stream = new XZCompressorInputStream(_stream);
        }
        else
        if("bz2".equalsIgnoreCase(_ext))
        {
        //    _stream = new BZip2CompressorInputStream(_stream);
        }
        return _stream;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromIni(InputStream _source)
    {
        Ini _ini = new Ini();
        _ini.load(new InputStreamReader(_source));
        Map<String, Object> _ret = new HashMap<>();
        for(String _key : _ini.keySet())
        {
            Map<String, Object> _set = new HashMap<>();
            for(String _k : _ini.get(_key).keySet())
            {
                _set.put(_k, _ini.get(_key, _k));
            }
            _ret.put(_key, _set);
        }
        return _ret;
    }

    public static final CSVFormat _SCSV = CSVFormat.newFormat(';')
            .withAllowDuplicateHeaderNames()
            .withEscape('\\')
            .withQuote('"')
            .withQuoteMode(QuoteMode.MINIMAL)
            .withRecordSeparator('\n')
            .withTrim();

    public static final CSVFormat _SQLITECSV = CSVFormat.newFormat(',')
            .withAllowDuplicateHeaderNames()
            .withEscape('\\')
            .withQuote('"')
            .withQuoteMode(QuoteMode.ALL_NON_NULL)
            .withRecordSeparator('\n')
            .withTrim();

    public static final CSVFormat _SQLITELIST = CSVFormat.newFormat('|')
            .withAllowDuplicateHeaderNames()
            .withEscape('\\')
            .withRecordSeparator('\n')
            .withTrim();

    @SneakyThrows
    public static Map<String, Object> loadContextFromTsv(InputStream _source)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("data", readFileCsv("TDF", _source, StandardCharsets.UTF_8));
        return _ret;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromCsv(InputStream _source)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("data", readFileCsv("Default", _source, StandardCharsets.UTF_8));
        return _ret;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromScsv(InputStream _source)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("data", readFileCsv("scsv", _source, StandardCharsets.UTF_8));
        return _ret;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromSqliteCsv(InputStream _source)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("data", readFileCsv("sqlite-csv", _source, StandardCharsets.UTF_8));
        return _ret;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromSqliteList(InputStream _source)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("data", readFileCsv("sqlite-list", _source, StandardCharsets.UTF_8));
        return _ret;
    }

    @SneakyThrows
    public static JDAO daoFromJdbc(String jdbcDriver, String _url, String _user, String _pass)
    {
        return JDAO.createDaoFromConnection(JdaoUtils.createConnectionByDriverSpec(StringUtils.isNotEmpty(jdbcDriver) ? jdbcDriver : null, _url, _user, _pass), true);
    }

    @SneakyThrows
    public static List<Map<String, Object>> readFileCsv(String _infmt, InputStream _in, Charset _cs)
    {
        List<Map<String, Object>> _res = new Vector<>();
        List<String> _hs = new Vector<>();

        BufferedReader _inr = new BufferedReader(new InputStreamReader(_in,_cs), 65536);
        CSVParser parser = null;
        if("scsv".equalsIgnoreCase(_infmt))
        {
            parser = new CSVParser(_inr, _SCSV);
        }
        else
        if("sqlite-csv".equalsIgnoreCase(_infmt))
        {
            parser = new CSVParser(_inr, _SQLITECSV);
        }
        else
        if("sqlite-list".equalsIgnoreCase(_infmt))
        {
            parser = new CSVParser(_inr, _SQLITELIST);
        }
        else
        {
            parser = new CSVParser(_inr, CSVFormat.valueOf(_infmt));
        }

        boolean _first = true;
        for(CSVRecord _row : parser.getRecords())
        {
            if(_first)
            {
                for(int _i = 0; _i<_row.size(); _i++)
                {
                    String _k = _row.get(_i);
                    if(StringUtils.isNotEmpty(_k) && StringUtils.isNotBlank(_k))
                    {
                        _hs.add(_k);
                    }
                    else
                    {
                        _hs.add(String.format("_%04d", _i));
                    }
                }
                _first = false;
            }
            else
            {
                Map<String,Object> _map = new LinkedHashMap<>();
                for(int _i = 0; _i<_hs.size(); _i++)
                {
                    try
                    {
                        String _v = _row.get(_i);
                        String _h = _hs.get(_i);
                        if(_v!=null) _map.put(_h, _v);
                    }
                    catch (Exception _xe)
                    {
                        log.error(_xe.getMessage()+" on row="+_row.toString());
                    }
                }
                if(_map.size()==0) {
                    log.error(_row.toString());
                }
                else
                {
                    _res.add(_map);
                }
            }
        }
        IOUtil.close(_inr);

        return _res;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromPdx(InputStream _source)
    {
        return PdxParser.loadFrom(new InputStreamReader(_source));
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromYaml(InputStream _source)
    {
        Yaml _y = new Yaml();
        HashMap<String, Object> _obj = _y.loadAs(new InputStreamReader(_source), HashMap.class);
        return _obj;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromToml(InputStream _source)
    {
        Toml _toml = new Toml();
        _toml.read(new InputStreamReader(_source));
        return _toml.toMap();
    }

    @SneakyThrows
    public static void writeAsHson(boolean _json, Writer _out, List<Map<String, Object>> _res)
    {
        JsonArray _arr = new JsonArray();
        for(Map<String, Object> _row : _res)
        {
            JsonObject _obj = new JsonObject();
            for(Map.Entry<String, Object> _entry : _row.entrySet())
            {
                Object _v = _entry.getValue();
                if(_v==null) _v= "";
                _obj.set(_entry.getKey(), _v.toString());
            }
            _arr.add(_obj);
        }
        _arr.writeTo(_out, _json ? Stringify.FORMATTED : Stringify.HJSON);
    }

    @SneakyThrows
    public static void writeAsHson(boolean _json, Writer _out, Map<String, Object> _res)
    {
        JsonObject _obj = new JsonObject();
        for(Map.Entry<String, Object> _entry : _res.entrySet())
        {
            Object _v = _entry.getValue();
            if(_v==null) _v= "";
            _obj.set(_entry.getKey(), toJsonObject(_v));
        }
        _obj.writeTo(_out, _json ? Stringify.FORMATTED : Stringify.HJSON);
    }

    private static JsonValue toJsonObject(Object _v) {
        if(_v instanceof Map)
        {
            return toJsonObject((Map)_v);
        }
        else
        if(_v instanceof List)
        {
            return toJsonObject((List)_v);
        }
        else
        if(_v instanceof String)
        {
            return JsonValue.valueOf((String)_v);
        }
        else
        if(_v instanceof Integer)
        {
            return JsonValue.valueOf((Integer)_v);
        }
        else
        if(_v instanceof Long)
        {
            return JsonValue.valueOf((Long)_v);
        }
        else
        if(_v instanceof Boolean)
        {
            return JsonValue.valueOf((Boolean)_v);
        }
        else
        if(_v instanceof Double)
        {
            return JsonValue.valueOf((Double)_v);
        }
        else
        if(_v instanceof Float)
        {
            return JsonValue.valueOf((Float)_v);
        }
        else
        if(_v.getClass().isArray())
        {
            return toJsonObject((List)Arrays.asList((Object[])_v));
        }
        return JsonValue.valueOf(_v.toString());
    }

    private static JsonValue toJsonObject(String _v) {
        return JsonValue.valueOf(_v);
    }

    private static JsonValue toJsonObject(int _v) {
        return JsonValue.valueOf(_v);
    }

    private static JsonValue toJsonObject(long _v) {
        return JsonValue.valueOf(_v);
    }

    private static JsonValue toJsonObject(boolean _v) {
        return JsonValue.valueOf(_v);
    }

    private static JsonValue toJsonObject(double _v) {
        return JsonValue.valueOf(_v);
    }

    private static JsonValue toJsonObject(float _v) {
        return JsonValue.valueOf(_v);
    }

    private static JsonValue toJsonObject(Map<String,Object> _v) {
        JsonObject _obj = new JsonObject();
        for(Map.Entry<String,Object> _entry : _v.entrySet())
        {
            Object _v1 = _entry.getValue();
            if(_v1==null) _v1= "";
            _obj.set(_entry.getKey(), toJsonObject(_v1));
        }
        return _obj;
    }

    private static JsonValue toJsonObject(List<Object> _v) {
        JsonArray _obj = new JsonArray();
        for(Object _entry : _v)
        {
            if(_entry==null) _entry= "";
            _obj.add(toJsonObject(_entry));
        }
        return _obj;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromHjson(InputStream _source)
    {
        HashMap<String, Object> _obj = new HashMap<>();
        JsonValue _hson = JsonValue.readHjson(new InputStreamReader(_source));
        for(Map.Entry<String, Object> _entry : hjsonToMap(_hson).entrySet())
        {
            _obj.put(_entry.getKey(), _entry.getValue());
        }
        return _obj;
    }

    static Map<String, Object> hjsonToMap(JsonValue _v)
    {
        Map<String, Object> _ret = new HashMap<>();
        if(_v.isObject())
        {
            _v.asObject().forEach(m -> _ret.put(m.getName(), hjsonToValue(m.getValue())));
        }
        else
        {
            _ret.put("data", hjsonToValue(_v));
        }
        return _ret;
    }

    static Object hjsonToValue(JsonValue value)
    {
        if(value.isObject())
        {
            return hjsonToMap(value);
        }
        else
        if(value.isArray())
        {
            return hjsonToArray(value);
        }
        else
        if(value.isString())
        {
            return value.asString();
        }
        else
        if(value.isNumber())
        {
            return Double.valueOf(value.asDouble());
        }
        else
        if(value.isBoolean())
        {
            return Boolean.valueOf(value.asBoolean());
        }
        else
        if(value.isNull())
        {
            return null;
        }
        else
        {
            return value.toString();
        }
    }

    static List hjsonToArray(JsonValue value)
    {
        List _ret = new Vector();
        value.asArray().forEach(m -> _ret.add(hjsonToValue(m)));
        return _ret;
    }
}

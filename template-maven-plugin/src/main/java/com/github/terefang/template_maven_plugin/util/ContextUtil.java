package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.jmelange.image.PdfImage;
import com.github.terefang.jmelange.image.PixelImage;
import com.github.terefang.jmelange.image.SvgImage;
import com.github.terefang.jdao.JDAO;
import com.github.terefang.jdao.JdaoUtils;
import com.github.terefang.jmelange.pdata.PdataParser;
import com.github.terefang.jmelange.pdata.PdataWriter;
import com.moandjiezana.toml.Toml;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.net.PercentCodec;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.CaseUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.WordUtils;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ContextUtil {

    public static final String[] EXTENSION_LIST = {
            ".props",
            ".properties",
            ".yaml",
            ".yml",
            ".json",
            ".hson",
            ".hjson",
            ".tml",
            ".toml",
            ".ini",
            ".pdx",
            ".pdata",
            ".sqlite.csv",
            ".list",
            ".scsv",
            ".csv",
            ".tsv",
            ".txt" };
    public static final String EXTENSIONS = StringUtils.join(ContextUtil.EXTENSION_LIST," ");

    public static Map<String, Object> loadContextFrom(String _file) throws MojoExecutionException
    {
        return loadContextFrom(new File(_file));
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFrom(File _file) throws MojoExecutionException
    {
        Map<String, Object> _ret = new HashMap<>();
        InputStream _fh = getStreamBySuffix(_file);
        String _fn = unCompressedFilename(_file);

        if(_fn.endsWith(".props")
                || _fn.endsWith(".properties"))
        {
            _ret.putAll(loadContextFromProperties(_fh));
        }
        else
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
        if(_fn.endsWith(".txt"))
        {
            _ret.putAll(loadContextFromTxt(_fh));
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
    public static Map<String,?> loadContextFromTxt(InputStream _source)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("data", loadFileLines(_source));
        return _ret;
    }

    @SneakyThrows
    public static List<String> loadFileLines(InputStream _source)
    {
        final List<String> _lines = new ArrayList<String>();
        BufferedReader _reader = null;
        try
        {
            _reader = new BufferedReader(new InputStreamReader(_source));

            for (String _line = _reader.readLine(); _line != null; _line = _reader.readLine())
            {
                _line = _line.trim();

                if (!_line.startsWith("#") && (_line.length() != 0))
                {
                    _lines.add(_line);
                }
            }

            _reader.close();
            _reader = null;
        }
        finally
        {
            IOUtil.close(_reader);
        }

        return _lines;
    }

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
        return PdataParser.loadFrom(new InputStreamReader(_source));
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromPdata(InputStream _source)
    {
        return PdataParser.loadFrom(new InputStreamReader(_source));
    }

    @SneakyThrows
    public static Map<String, Object> fromPdx(String _source)
    {
        return PdataParser.loadFrom(new StringReader(_source));
    }

    @SneakyThrows
    public static Map<String, Object> fromPdata(String _source)
    {
        return PdataParser.loadFrom(new StringReader(_source));
    }

    @SneakyThrows
    public static Map<String,?> loadContextFromProperties(InputStream _source) {
        InputStreamReader _rd = new InputStreamReader(_source);
        Properties _props = new Properties();
        _props.load(_rd);
        IOUtil.close(_rd);
        HashMap<String, String> _ret = new HashMap<String, String>();
        for (final String name : _props.stringPropertyNames())
            _ret.put(name, _props.getProperty(name));
        return _ret;
    }

    @SneakyThrows
    public static Map<String, Object> loadContextFromYaml(InputStream _source)
    {
        Yaml _y = new Yaml();
        HashMap<String, Object> _obj = _y.loadAs(new InputStreamReader(_source), HashMap.class);
        return _obj;
    }

    @SneakyThrows
    public static Map<String, Object> fromYaml(String _source)
    {
        Yaml _y = new Yaml();
        HashMap<String, Object> _obj = _y.loadAs(_source, HashMap.class);
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

    public static String toHson(Map<String, Object> _res)
    {
        StringWriter _sw = new StringWriter();
        writeAsHson(false, _sw, _res);
        _sw.flush();
        return _sw.getBuffer().toString();
    }

    public static String toJson(Map<String, Object> _res)
    {
        StringWriter _sw = new StringWriter();
        writeAsHson(true, _sw, _res);
        _sw.flush();
        return _sw.getBuffer().toString();
    }

    @SneakyThrows
    public static void writeAsPdata(Writer _out, Map<String, Object> _res)
    {
        PdataWriter.writeTo(_res,_out);
    }

    public static String toPdata(Map<String, Object> _res)
    {
        StringWriter _sw = new StringWriter();
        writeAsPdata(_sw, _res);
        _sw.flush();
        return _sw.getBuffer().toString();
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

    @SneakyThrows
    public static Map<String, Object> fromJson(String _source)
    {
        HashMap<String, Object> _obj = new HashMap<>();
        JsonValue _hson = JsonValue.readJSON(_source);
        for(Map.Entry<String, Object> _entry : hjsonToMap(_hson).entrySet())
        {
            _obj.put(_entry.getKey(), _entry.getValue());
        }
        return _obj;
    }

    @SneakyThrows
    public static Map<String, Object> fromHjson(String _source)
    {
        HashMap<String, Object> _obj = new HashMap<>();
        JsonValue _hson = JsonValue.readHjson(_source);
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

    /*----- image helper -----*/

    public static PixelImage pixImage(int _w, int _h) { return PixelImage.create(_w,_h); }

    public static SvgImage svgImage(int _w, int _h) { return SvgImage.create(_w,_h); }

    public static PdfImage pdfImage(int _w, int _h) { return PdfImage.create(_w,_h); }


    /*----- hmacs -----*/

    public static String hmacMd5Hex(String key, String valueToDigest) {
        return HmacUtils.hmacMd5Hex(key, valueToDigest);
    }

    public static String hmacSha1Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha1Hex(key, valueToDigest);
    }

    public static String hmacSha256Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha256Hex(key, valueToDigest);
    }

    public static String hmacSha384Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha384Hex(key, valueToDigest);
    }

    public static String hmacSha512Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha512Hex(key, valueToDigest);
    }

    /*----- digest -----*/

    public static String md2Hex(String data) {
        return DigestUtils.md2Hex(data);
    }
    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }
    public static String sha1Hex(String data) {
        return DigestUtils.sha1Hex(data);
    }
    public static String sha256Hex(String data) {
        return DigestUtils.sha256Hex(data);
    }
    public static String sha3_256Hex(String data) {
        return DigestUtils.sha3_256Hex(data);
    }
    public static String sha3_384Hex(String data) {
        return DigestUtils.sha3_384Hex(data);
    }
    public static String sha3_512Hex(String data) {
        return DigestUtils.sha3_512Hex(data);
    }
    public static String sha384Hex(String data) {
        return DigestUtils.sha384Hex(data);
    }
    public static String sha512_224Hex(String data) {
        return DigestUtils.sha512_224Hex(data);
    }
    public static String sha512_256Hex(String data) {
        return DigestUtils.sha512_256Hex(data);
    }
    public static String sha512Hex(String data) {
        return DigestUtils.sha512Hex(data);
    }

    /*----- WordUtils -----*/

    public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords, String wrapOn) {
        return WordUtils.wrap(str, wrapLength, newLineStr, wrapLongWords, wrapOn);
    }

    public static boolean containsAllWords(CharSequence word, CharSequence... words) {
        return WordUtils.containsAllWords(word, words);
    }

    @Deprecated
    public static boolean isDelimiter(char ch, char[] delimiters) {
        return WordUtils.isDelimiter(ch, delimiters);
    }

    @Deprecated
    public static boolean isDelimiter(int codePoint, char[] delimiters) {
        return WordUtils.isDelimiter(codePoint, delimiters);
    }

    public static String abbreviate(String str, int lower, int upper, String appendToEnd) {
        return WordUtils.abbreviate(str, lower, upper, appendToEnd);
    }

    public static String toCamelCase(String str, boolean capitalizeFirstLetter, char... delimiters) {
        return CaseUtils.toCamelCase(str, capitalizeFirstLetter, delimiters);
    }

    public static String toCamelCase(String str) {
        return CaseUtils.toCamelCase(str, false);
    }

    public static String wrap(String str, int wrapLength) {
        return WordUtils.wrap(str, wrapLength);
    }

    public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords) {
        return WordUtils.wrap(str, wrapLength, newLineStr, wrapLongWords);
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

    /*----- StringUtils -----*/

    public static boolean isEmpty(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotEmpty(cs);
    }

    public static boolean isAnyEmpty(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isAnyEmpty(css);
    }

    public static boolean isNoneEmpty(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isNoneEmpty(css);
    }

    public static boolean isBlank(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(cs);
    }

    public static boolean isAnyBlank(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isAnyBlank(css);
    }

    public static boolean isNoneBlank(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isNoneBlank(css);
    }

    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }

    public static String trimToNull(String str) {
        return org.apache.commons.lang3.StringUtils.trimToNull(str);
    }

    public static String trimToEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
    }

    public static String strip(String str) {
        return org.apache.commons.lang3.StringUtils.strip(str);
    }

    public static String stripToNull(String str) {
        return org.apache.commons.lang3.StringUtils.stripToNull(str);
    }

    public static String stripToEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.stripToEmpty(str);
    }

    public static String strip(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.strip(str, stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripStart(str, stripChars);
    }

    public static String stripEnd(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripEnd(str, stripChars);
    }

    public static String[] stripAll(String... strs) {
        return org.apache.commons.lang3.StringUtils.stripAll(strs);
    }

    public static String[] stripAll(String[] strs, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripAll(strs, stripChars);
    }

    public static String stripAccents(String input) {
        return org.apache.commons.lang3.StringUtils.stripAccents(input);
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(str1, str2);
    }

    public static int indexOf(CharSequence seq, int searchChar) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchChar);
    }

    public static int indexOf(CharSequence seq, int searchChar, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchChar, startPos);
    }

    public static int indexOf(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchSeq);
    }

    public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchSeq, startPos);
    }

    public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return org.apache.commons.lang3.StringUtils.ordinalIndexOf(str, searchStr, ordinal);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(str, searchStr);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public static int lastIndexOf(CharSequence seq, int searchChar) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchChar);
    }

    public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchChar, startPos);
    }

    public static int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchSeq);
    }

    public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return org.apache.commons.lang3.StringUtils.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchSeq, startPos);
    }

    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr);
    }

    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    public static boolean contains(CharSequence seq, int searchChar) {
        return org.apache.commons.lang3.StringUtils.contains(seq, searchChar);
    }

    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.contains(seq, searchSeq);
    }

    public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, searchStr);
    }

    public static boolean containsWhitespace(CharSequence seq) {
        return org.apache.commons.lang3.StringUtils.containsWhitespace(seq);
    }

    public static int indexOfAny(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(cs, searchChars);
    }

    public static int indexOfAny(CharSequence cs, String searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(cs, searchChars);
    }

    public static boolean containsAny(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchChars);
    }

    public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchChars);
    }

    public static boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchCharSequences);
    }

    public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAnyBut(cs, searchChars);
    }

    public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAnyBut(seq, searchChars);
    }

    public static boolean containsOnly(CharSequence cs, char... valid) {
        return org.apache.commons.lang3.StringUtils.containsOnly(cs, valid);
    }

    public static boolean containsOnly(CharSequence cs, String validChars) {
        return org.apache.commons.lang3.StringUtils.containsOnly(cs, validChars);
    }

    public static boolean containsNone(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.containsNone(cs, searchChars);
    }

    public static boolean containsNone(CharSequence cs, String invalidChars) {
        return org.apache.commons.lang3.StringUtils.containsNone(cs, invalidChars);
    }

    public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(str, searchStrs);
    }

    public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfAny(str, searchStrs);
    }

    public static String substring(String str, int start) {
        return org.apache.commons.lang3.StringUtils.substring(str, start);
    }

    public static String substring(String str, int start, int end) {
        return org.apache.commons.lang3.StringUtils.substring(str, start, end);
    }

    public static String left(String str, int len) {
        return org.apache.commons.lang3.StringUtils.left(str, len);
    }

    public static String right(String str, int len) {
        return org.apache.commons.lang3.StringUtils.right(str, len);
    }

    public static String mid(String str, int pos, int len) {
        return org.apache.commons.lang3.StringUtils.mid(str, pos, len);
    }

    public static String substringBefore(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
    }

    public static String substringAfter(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfter(str, separator);
    }

    public static String substringBeforeLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBeforeLast(str, separator);
    }

    public static String substringAfterLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfterLast(str, separator);
    }

    public static String substringBetween(String str, String tag) {
        return org.apache.commons.lang3.StringUtils.substringBetween(str, tag);
    }

    public static String substringBetween(String str, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringBetween(str, open, close);
    }

    public static String[] substringsBetween(String str, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringsBetween(str, open, close);
    }

    public static String[] split(String str) {
        return org.apache.commons.lang3.StringUtils.split(str);
    }

    public static String[] split(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChar);
    }

    public static String[] split(String str, String separatorChars) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChars);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChars, max);
    }

    public static String[] splitByWholeSeparator(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator);
    }

    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator, max);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

    public static String[] splitPreserveAllTokens(String str) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str);
    }

    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChar);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChars);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChars, max);
    }

    public static String[] splitByCharacterType(String str) {
        return org.apache.commons.lang3.StringUtils.splitByCharacterType(str);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase(str);
    }

    public static <T> String join(T... elements) {
        return org.apache.commons.lang3.StringUtils.join(elements);
    }

    public static String join(Object[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(long[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(int[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(short[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(byte[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(char[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(float[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(double[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(long[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(int[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(byte[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(short[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(char[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(double[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(float[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(Object[] array, String separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(Iterator<?> iterator, char separator) {
        return org.apache.commons.lang3.StringUtils.join(iterator, separator);
    }

    public static String join(Iterator<?> iterator, String separator) {
        return org.apache.commons.lang3.StringUtils.join(iterator, separator);
    }

    public static String join(Iterable<?> iterable, char separator) {
        return org.apache.commons.lang3.StringUtils.join(iterable, separator);
    }

    public static String join(Iterable<?> iterable, String separator) {
        return org.apache.commons.lang3.StringUtils.join(iterable, separator);
    }

    public static String deleteWhitespace(String str) {
        return org.apache.commons.lang3.StringUtils.deleteWhitespace(str);
    }

    public static String removeStart(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeStart(str, remove);
    }

    public static String removeStartIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeStartIgnoreCase(str, remove);
    }

    public static String removeEnd(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeEnd(str, remove);
    }

    public static String removeEndIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeEndIgnoreCase(str, remove);
    }

    public static String remove(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static String remove(String str, char remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replaceOnce(text, searchString, replacement);
    }

    public static String replacePattern(String source, String regex, String replacement) {
        return org.apache.commons.lang3.StringUtils.replacePattern(source, regex, replacement);
    }

    public static String removePattern(String source, String regex) {
        return org.apache.commons.lang3.StringUtils.removePattern(source, regex);
    }

    public static String replace(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replace(text, searchString, replacement);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        return org.apache.commons.lang3.StringUtils.replace(text, searchString, replacement, max);
    }

    public static String replaceEach(String text, String[] searchList, String[] replacementList) {
        return org.apache.commons.lang3.StringUtils.replaceEach(text, searchList, replacementList);
    }

    public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return org.apache.commons.lang3.StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
    }

    public static String replaceChars(String str, char searchChar, char replaceChar) {
        return org.apache.commons.lang3.StringUtils.replaceChars(str, searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars) {
        return org.apache.commons.lang3.StringUtils.replaceChars(str, searchChars, replaceChars);
    }

    public static String overlay(String str, String overlay, int start, int end) {
        return org.apache.commons.lang3.StringUtils.overlay(str, overlay, start, end);
    }

    public static String chomp(String str) {
        return org.apache.commons.lang3.StringUtils.chomp(str);
    }

    @Deprecated
    public static String chomp(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.chomp(str, separator);
    }

    public static String chop(String str) {
        return org.apache.commons.lang3.StringUtils.chop(str);
    }

    public static String repeat(String str, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(str, repeat);
    }

    public static String repeat(String str, String separator, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(str, separator, repeat);
    }

    public static String repeat(char ch, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(ch, repeat);
    }

    public static String rightPad(String str, int size) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size);
    }

    public static String rightPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size, padChar);
    }

    public static String rightPad(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size, padStr);
    }

    public static String leftPad(String str, int size) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size);
    }

    public static String leftPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padChar);
    }

    public static String leftPad(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padStr);
    }

    public static int length(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.length(cs);
    }

    public static String center(String str, int size) {
        return org.apache.commons.lang3.StringUtils.center(str, size);
    }

    public static String center(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.center(str, size, padChar);
    }

    public static String center(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.center(str, size, padStr);
    }

    public static String upperCase(String str) {
        return org.apache.commons.lang3.StringUtils.upperCase(str);
    }

    public static String upperCase(String str, Locale locale) {
        return org.apache.commons.lang3.StringUtils.upperCase(str, locale);
    }

    public static String lowerCase(String str) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str);
    }

    public static String lowerCase(String str, Locale locale) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str, locale);
    }

    public static String capitalize(String str) {
        return org.apache.commons.lang3.StringUtils.capitalize(str);
    }

    public static String capitalize(String str, char... delimiters) {
        return WordUtils.capitalize(str, delimiters);
    }

    public static String capitalizeFully(String str) {
        return WordUtils.capitalizeFully(str);
    }

    public static String capitalizeFully(String str, char... delimiters) {
        return WordUtils.capitalizeFully(str, delimiters);
    }

    public static String uncapitalize(String str) {
        return org.apache.commons.lang3.StringUtils.uncapitalize(str);
    }

    public static String uncapitalize(String str, char... delimiters) {
        return WordUtils.uncapitalize(str, delimiters);
    }

    public static String swapCase(String str) {
        return org.apache.commons.lang3.StringUtils.swapCase(str);
    }

    public static String initials(String str) {
        return WordUtils.initials(str);
    }

    public static String initials(String str, char... delimiters) {
        return WordUtils.initials(str, delimiters);
    }

    public static int countMatches(CharSequence str, CharSequence sub) {
        return org.apache.commons.lang3.StringUtils.countMatches(str, sub);
    }

    public static int countMatches(CharSequence str, char ch) {
        return org.apache.commons.lang3.StringUtils.countMatches(str, ch);
    }

    public static boolean isAlpha(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlpha(cs);
    }

    public static boolean isAlphaSpace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlphaSpace(cs);
    }

    public static boolean isAlphanumeric(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlphanumeric(cs);
    }

    public static boolean isAlphanumericSpace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlphanumericSpace(cs);
    }

    public static boolean isAsciiPrintable(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAsciiPrintable(cs);
    }

    public static boolean isNumeric(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNumeric(cs);
    }

    public static boolean isNumericSpace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNumericSpace(cs);
    }

    public static boolean isWhitespace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isWhitespace(cs);
    }

    public static boolean isAllLowerCase(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAllLowerCase(cs);
    }

    public static boolean isAllUpperCase(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAllUpperCase(cs);
    }

    public static String defaultString(String str) {
        return org.apache.commons.lang3.StringUtils.defaultString(str);
    }

    public static String defaultString(String str, String defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultString(str, defaultStr);
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(str, defaultStr);
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultIfEmpty(str, defaultStr);
    }

    public static String reverse(String str) {
        return org.apache.commons.lang3.StringUtils.reverse(str);
    }

    public static String reverseDelimited(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.reverseDelimited(str, separatorChar);
    }

    public static String abbreviate(String str, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.abbreviate(str, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.abbreviate(str, offset, maxWidth);
    }

    public static String abbreviateMiddle(String str, String middle, int length) {
        return org.apache.commons.lang3.StringUtils.abbreviateMiddle(str, middle, length);
    }

    public static String difference(String str1, String str2) {
        return org.apache.commons.lang3.StringUtils.difference(str1, str2);
    }

    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.indexOfDifference(cs1, cs2);
    }

    public static int indexOfDifference(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.indexOfDifference(css);
    }

    public static String getCommonPrefix(String... strs) {
        return org.apache.commons.lang3.StringUtils.getCommonPrefix(strs);
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        return org.apache.commons.lang3.StringUtils.getLevenshteinDistance(s, t);
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
        return org.apache.commons.lang3.StringUtils.getLevenshteinDistance(s, t, threshold);
    }

    public static double getJaroWinklerDistance(CharSequence first, CharSequence second) {
        return org.apache.commons.lang3.StringUtils.getJaroWinklerDistance(first, second);
    }

    public static int getFuzzyDistance(CharSequence term, CharSequence query, Locale locale) {
        return org.apache.commons.lang3.StringUtils.getFuzzyDistance(term, query, locale);
    }

    public static boolean startsWith(CharSequence str, CharSequence prefix) {
        return org.apache.commons.lang3.StringUtils.startsWith(str, prefix);
    }

    public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(str, prefix);
    }

    public static boolean startsWithAny(CharSequence string, CharSequence... searchStrings) {
        return org.apache.commons.lang3.StringUtils.startsWithAny(string, searchStrings);
    }

    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWith(str, suffix);
    }

    public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(str, suffix);
    }

    public static String normalizeSpace(String str) {
        return org.apache.commons.lang3.StringUtils.normalizeSpace(str);
    }

    public static boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
        return org.apache.commons.lang3.StringUtils.endsWithAny(string, searchStrings);
    }

    public static String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
        return org.apache.commons.lang3.StringUtils.appendIfMissing(str, suffix, suffixes);
    }

    public static String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
        return org.apache.commons.lang3.StringUtils.appendIfMissingIgnoreCase(str, suffix, suffixes);
    }

    public static String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
        return org.apache.commons.lang3.StringUtils.prependIfMissing(str, prefix, prefixes);
    }

    public static String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
        return org.apache.commons.lang3.StringUtils.prependIfMissingIgnoreCase(str, prefix, prefixes);
    }

    @Deprecated
    public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return org.apache.commons.lang3.StringUtils.toString(bytes, charsetName);
    }

    public static String toEncodedString(byte[] bytes, Charset charset) {
        return org.apache.commons.lang3.StringUtils.toEncodedString(bytes, charset);
    }

    public static String wrap(String str, char wrapWith) {
        return org.apache.commons.lang3.StringUtils.wrap(str, wrapWith);
    }

    public static String wrap(String str, String wrapWith) {
        return org.apache.commons.lang3.StringUtils.wrap(str, wrapWith);
    }

    /* ---- numberutils ---- */

    public static int toInt(String str) {
        return NumberUtils.toInt(str);
    }

    public static int toInt(String str, int defaultValue) {
        return NumberUtils.toInt(str, defaultValue);
    }

    public static long toLong(String str) {
        return NumberUtils.toLong(str);
    }

    public static long toLong(String str, long defaultValue) {
        return NumberUtils.toLong(str, defaultValue);
    }

    public static float toFloat(String str) {
        return NumberUtils.toFloat(str);
    }

    public static float toFloat(String str, float defaultValue) {
        return NumberUtils.toFloat(str, defaultValue);
    }

    public static double toDouble(String str) {
        return NumberUtils.toDouble(str);
    }

    public static double toDouble(String str, double defaultValue) {
        return NumberUtils.toDouble(str, defaultValue);
    }

    public static byte toByte(String str) {
        return NumberUtils.toByte(str);
    }

    public static byte toByte(String str, byte defaultValue) {
        return NumberUtils.toByte(str, defaultValue);
    }

    public static short toShort(String str) {
        return NumberUtils.toShort(str);
    }

    public static short toShort(String str, short defaultValue) {
        return NumberUtils.toShort(str, defaultValue);
    }

    public static Number createNumber(String str) throws NumberFormatException {
        return NumberUtils.createNumber(str);
    }

    public static Float createFloat(String str) {
        return NumberUtils.createFloat(str);
    }

    public static Double createDouble(String str) {
        return NumberUtils.createDouble(str);
    }

    public static Integer createInteger(String str) {
        return NumberUtils.createInteger(str);
    }

    public static Long createLong(String str) {
        return NumberUtils.createLong(str);
    }

    public static BigInteger createBigInteger(String str) {
        return NumberUtils.createBigInteger(str);
    }

    public static BigDecimal createBigDecimal(String str) {
        return NumberUtils.createBigDecimal(str);
    }

    public static long min(long... array) {
        return NumberUtils.min(array);
    }

    public static int min(int... array) {
        return NumberUtils.min(array);
    }

    public static short min(short... array) {
        return NumberUtils.min(array);
    }

    public static byte min(byte... array) {
        return NumberUtils.min(array);
    }

    public static double min(double... array) {
        return NumberUtils.min(array);
    }

    public static float min(float... array) {
        return NumberUtils.min(array);
    }

    public static long max(long... array) {
        return NumberUtils.max(array);
    }

    public static int max(int... array) {
        return NumberUtils.max(array);
    }

    public static short max(short... array) {
        return NumberUtils.max(array);
    }

    public static byte max(byte... array) {
        return NumberUtils.max(array);
    }

    public static double max(double... array) {
        return NumberUtils.max(array);
    }

    public static float max(float... array) {
        return NumberUtils.max(array);
    }

    public static long min(long a, long b, long c) {
        return NumberUtils.min(a, b, c);
    }

    public static int min(int a, int b, int c) {
        return NumberUtils.min(a, b, c);
    }

    public static short min(short a, short b, short c) {
        return NumberUtils.min(a, b, c);
    }

    public static byte min(byte a, byte b, byte c) {
        return NumberUtils.min(a, b, c);
    }

    public static double min(double a, double b, double c) {
        return NumberUtils.min(a, b, c);
    }

    public static float min(float a, float b, float c) {
        return NumberUtils.min(a, b, c);
    }

    public static long max(long a, long b, long c) {
        return NumberUtils.max(a, b, c);
    }

    public static int max(int a, int b, int c) {
        return NumberUtils.max(a, b, c);
    }

    public static short max(short a, short b, short c) {
        return NumberUtils.max(a, b, c);
    }

    public static byte max(byte a, byte b, byte c) {
        return NumberUtils.max(a, b, c);
    }

    public static double max(double a, double b, double c) {
        return NumberUtils.max(a, b, c);
    }

    public static float max(float a, float b, float c) {
        return NumberUtils.max(a, b, c);
    }

    public static boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }

    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    public static boolean isParsable(String str) {
        return NumberUtils.isParsable(str);
    }

    public static int compare(int x, int y) {
        return NumberUtils.compare(x, y);
    }

    public static int compare(long x, long y) {
        return NumberUtils.compare(x, y);
    }

    public static int compare(short x, short y) {
        return NumberUtils.compare(x, y);
    }

    public static int compare(byte x, byte y) {
        return NumberUtils.compare(x, y);
    }

    /* ---- booleanutils ---- */

    public static Boolean negate(Boolean bool) {
        return BooleanUtils.negate(bool);
    }

    public static boolean isTrue(Boolean bool) {
        return BooleanUtils.isTrue(bool);
    }

    public static boolean isNotTrue(Boolean bool) {
        return BooleanUtils.isNotTrue(bool);
    }

    public static boolean isFalse(Boolean bool) {
        return BooleanUtils.isFalse(bool);
    }

    public static boolean isNotFalse(Boolean bool) {
        return BooleanUtils.isNotFalse(bool);
    }

    public static boolean toBoolean(Boolean bool) {
        return BooleanUtils.toBoolean(bool);
    }

    public static boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull) {
        return BooleanUtils.toBooleanDefaultIfNull(bool, valueIfNull);
    }

    public static boolean toBoolean(int value) {
        return BooleanUtils.toBoolean(value);
    }

    public static Boolean toBooleanObject(int value) {
        return BooleanUtils.toBooleanObject(value);
    }

    public static Boolean toBooleanObject(Integer value) {
        return BooleanUtils.toBooleanObject(value);
    }

    public static boolean toBoolean(int value, int trueValue, int falseValue) {
        return BooleanUtils.toBoolean(value, trueValue, falseValue);
    }

    public static boolean toBoolean(Integer value, Integer trueValue, Integer falseValue) {
        return BooleanUtils.toBoolean(value, trueValue, falseValue);
    }

    public static Boolean toBooleanObject(int value, int trueValue, int falseValue, int nullValue) {
        return BooleanUtils.toBooleanObject(value, trueValue, falseValue, nullValue);
    }

    public static Boolean toBooleanObject(Integer value, Integer trueValue, Integer falseValue, Integer nullValue) {
        return BooleanUtils.toBooleanObject(value, trueValue, falseValue, nullValue);
    }

    public static int toInteger(boolean bool) {
        return BooleanUtils.toInteger(bool);
    }

    public static Integer toIntegerObject(boolean bool) {
        return BooleanUtils.toIntegerObject(bool);
    }

    public static Integer toIntegerObject(Boolean bool) {
        return BooleanUtils.toIntegerObject(bool);
    }

    public static int toInteger(boolean bool, int trueValue, int falseValue) {
        return BooleanUtils.toInteger(bool, trueValue, falseValue);
    }

    public static int toInteger(Boolean bool, int trueValue, int falseValue, int nullValue) {
        return BooleanUtils.toInteger(bool, trueValue, falseValue, nullValue);
    }

    public static Integer toIntegerObject(boolean bool, Integer trueValue, Integer falseValue) {
        return BooleanUtils.toIntegerObject(bool, trueValue, falseValue);
    }

    public static Integer toIntegerObject(Boolean bool, Integer trueValue, Integer falseValue, Integer nullValue) {
        return BooleanUtils.toIntegerObject(bool, trueValue, falseValue, nullValue);
    }

    public static Boolean toBooleanObject(String str) {
        return BooleanUtils.toBooleanObject(str);
    }

    public static Boolean toBooleanObject(String str, String trueString, String falseString, String nullString) {
        return BooleanUtils.toBooleanObject(str, trueString, falseString, nullString);
    }

    public static boolean toBoolean(String str) {
        return BooleanUtils.toBoolean(str);
    }

    public static boolean toBoolean(String str, String trueString, String falseString) {
        return BooleanUtils.toBoolean(str, trueString, falseString);
    }

    public static String toStringTrueFalse(Boolean bool) {
        return BooleanUtils.toStringTrueFalse(bool);
    }

    public static String toStringOnOff(Boolean bool) {
        return BooleanUtils.toStringOnOff(bool);
    }

    public static String toStringYesNo(Boolean bool) {
        return BooleanUtils.toStringYesNo(bool);
    }

    public static String toString(Boolean bool, String trueString, String falseString, String nullString) {
        return BooleanUtils.toString(bool, trueString, falseString, nullString);
    }

    public static String toStringTrueFalse(boolean bool) {
        return BooleanUtils.toStringTrueFalse(bool);
    }

    public static String toStringOnOff(boolean bool) {
        return BooleanUtils.toStringOnOff(bool);
    }

    public static String toStringYesNo(boolean bool) {
        return BooleanUtils.toStringYesNo(bool);
    }

    public static String toString(boolean bool, String trueString, String falseString) {
        return BooleanUtils.toString(bool, trueString, falseString);
    }

    public static boolean and(boolean... array) {
        return BooleanUtils.and(array);
    }

    public static Boolean and(Boolean... array) {
        return BooleanUtils.and(array);
    }

    public static boolean or(boolean... array) {
        return BooleanUtils.or(array);
    }

    public static Boolean or(Boolean... array) {
        return BooleanUtils.or(array);
    }

    public static boolean xor(boolean... array) {
        return BooleanUtils.xor(array);
    }

    public static Boolean xor(Boolean... array) {
        return BooleanUtils.xor(array);
    }

    public static int compare(boolean x, boolean y) {
        return BooleanUtils.compare(x, y);
    }

    /*-----  -----*/

    public static boolean checkBoolean(final Object _str)
    {
        if(_str==null)
        {
            return false;
        }

        return checkBoolean(_str.toString());
    }

    static final String [] _btests = {"false", "f", "off", "none", "no", "n", "null", "nul", "nil", "0"};
    public static boolean checkBoolean(String _str)
    {
        if (_str == null) {
            return false;
        }

        _str = _str.trim().toLowerCase();
        // any defined string is "true" unless it is a false indicator:
        // false, off, none, no, null, nul, nil, 0
        for(String _test : _btests)
        {
            if(_test.equalsIgnoreCase(_str))
                return false;
        }

        return true;
    }

    // Hex

    public static byte[] decodeHex(String data) throws DecoderException {
        return Hex.decodeHex(data);
    }

    public static byte[] decodeHex(char[] data) throws DecoderException {
        return Hex.decodeHex(data);
    }

    public static char[] encodeHex(byte[] data) {
        return Hex.encodeHex(data);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return Hex.encodeHex(data, toLowerCase);
    }

    public static String encodeHexString(byte[] data) {
        return Hex.encodeHexString(data);
    }

    public static String encodeHexString(byte[] data, boolean toLowerCase) {
        return Hex.encodeHexString(data, toLowerCase);
    }

    //

    static PercentCodec _pct = new PercentCodec();

    @SneakyThrows
    public static String encodePercent(String _raw)
    {
        return new String(_pct.encode(_raw.getBytes()));
    }

    @SneakyThrows
    public static String decodePercent(String _cooked)
    {
        return new String(_pct.encode(_cooked.getBytes()));
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


    // DateUtils _du;

    public static long MILLIS_PER_DAY = DateUtils.MILLIS_PER_DAY;
    public static long MILLIS_PER_HOUR = DateUtils.MILLIS_PER_HOUR;
    public static long MILLIS_PER_MINUTE = DateUtils.MILLIS_PER_MINUTE;
    public static long MILLIS_PER_SECOND = DateUtils.MILLIS_PER_SECOND;

    public static int DATE_FIELD_YEAR = Calendar.YEAR;
    public static int DATE_FIELD_MONTH = Calendar.MONTH;
    public static int DATE_FIELD_DAY = Calendar.DAY_OF_MONTH;
    public static int DATE_FIELD_HOUR = Calendar.HOUR;
    public static int DATE_FIELD_MINUTE = Calendar.MINUTE;
    public static int DATE_FIELD_SECOND = Calendar.SECOND;
    public static int DATE_FIELD_MILLISECOND = Calendar.MILLISECOND;

    public static int DATE_RANGE_WEEK_SUNDAY = DateUtils.RANGE_WEEK_SUNDAY;
    public static int DATE_RANGE_MONTH_SUNDAY = DateUtils.RANGE_MONTH_SUNDAY;
    public static int DATE_RANGE_WEEK_CENTER = DateUtils.RANGE_WEEK_CENTER;
    public static int DATE_RANGE_WEEK_MONDAY = DateUtils.RANGE_WEEK_MONDAY;
    public static int DATE_RANGE_WEEK_RELATIVE = DateUtils.RANGE_WEEK_RELATIVE;
    public static int DATE_RANGE_MONTH_MONDAY = DateUtils.RANGE_MONTH_MONDAY;

    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    public static boolean isSameInstant(Date date1, Date date2) {
        return DateUtils.isSameInstant(date1, date2);
    }

    public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameInstant(cal1, cal2);
    }

    public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameLocalTime(cal1, cal2);
    }

    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return DateUtils.parseDate(str, parsePatterns);
    }

    public static Date parseDate(String str, Locale locale, String... parsePatterns) throws ParseException {
        return DateUtils.parseDate(str, locale, parsePatterns);
    }

    public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return DateUtils.parseDateStrictly(str, parsePatterns);
    }

    public static Date parseDateStrictly(String str, Locale locale, String... parsePatterns) throws ParseException {
        return DateUtils.parseDateStrictly(str, locale, parsePatterns);
    }

    public static Date addYears(Date date, int amount) {
        return DateUtils.addYears(date, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }

    public static Date setYears(Date date, int amount) {
        return DateUtils.setYears(date, amount);
    }

    public static Date setMonths(Date date, int amount) {
        return DateUtils.setMonths(date, amount);
    }

    public static Date setDays(Date date, int amount) {
        return DateUtils.setDays(date, amount);
    }

    public static Date setHours(Date date, int amount) {
        return DateUtils.setHours(date, amount);
    }

    public static Date setMinutes(Date date, int amount) {
        return DateUtils.setMinutes(date, amount);
    }

    public static Date setSeconds(Date date, int amount) {
        return DateUtils.setSeconds(date, amount);
    }

    public static Date setMilliseconds(Date date, int amount) {
        return DateUtils.setMilliseconds(date, amount);
    }

    public static Calendar toCalendar(Date date) {
        return DateUtils.toCalendar(date);
    }

    public static Calendar toCalendar(Date date, TimeZone tz) {
        return DateUtils.toCalendar(date, tz);
    }

    public static Date dateRound(Date date, int field) {
        return DateUtils.round(date, field);
    }

    public static Calendar dateRound(Calendar date, int field) {
        return DateUtils.round(date, field);
    }

    public static Date dateRound(Object date, int field) {
        return DateUtils.round(date, field);
    }

    public static Date dateTruncate(Date date, int field) {
        return DateUtils.truncate(date, field);
    }

    public static Calendar dateTruncate(Calendar date, int field) {
        return DateUtils.truncate(date, field);
    }

    public static Date dateTruncate(Object date, int field) {
        return DateUtils.truncate(date, field);
    }

    public static Date dateCeiling(Date date, int field) {
        return DateUtils.ceiling(date, field);
    }

    public static Calendar dateCeiling(Calendar date, int field) {
        return DateUtils.ceiling(date, field);
    }

    public static Date dateCeiling(Object date, int field) {
        return DateUtils.ceiling(date, field);
    }

    public static Iterator<Calendar> dateIterator(Date focus, int rangeStyle) {
        return DateUtils.iterator(focus, rangeStyle);
    }

    public static Iterator<Calendar> dateIterator(Calendar focus, int rangeStyle) {
        return DateUtils.iterator(focus, rangeStyle);
    }

    public static Iterator<?> dateIterator(Object focus, int rangeStyle) {
        return DateUtils.iterator(focus, rangeStyle);
    }

    public static long getFragmentInMilliseconds(Date date, int fragment) {
        return DateUtils.getFragmentInMilliseconds(date, fragment);
    }

    public static long getFragmentInSeconds(Date date, int fragment) {
        return DateUtils.getFragmentInSeconds(date, fragment);
    }

    public static long getFragmentInMinutes(Date date, int fragment) {
        return DateUtils.getFragmentInMinutes(date, fragment);
    }

    public static long getFragmentInHours(Date date, int fragment) {
        return DateUtils.getFragmentInHours(date, fragment);
    }

    public static long getFragmentInDays(Date date, int fragment) {
        return DateUtils.getFragmentInDays(date, fragment);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInMilliseconds(calendar, fragment);
    }

    public static long getFragmentInSeconds(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInSeconds(calendar, fragment);
    }

    public static long getFragmentInMinutes(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInMinutes(calendar, fragment);
    }

    public static long getFragmentInHours(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInHours(calendar, fragment);
    }

    public static long getFragmentInDays(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInDays(calendar, fragment);
    }

    public static boolean dateTruncatedEquals(Calendar cal1, Calendar cal2, int field) {
        return DateUtils.truncatedEquals(cal1, cal2, field);
    }

    public static boolean dateTruncatedEquals(Date date1, Date date2, int field) {
        return DateUtils.truncatedEquals(date1, date2, field);
    }

    public static int dateTruncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
        return DateUtils.truncatedCompareTo(cal1, cal2, field);
    }

    public static int dateTruncatedCompareTo(Date date1, Date date2, int field) {
        return DateUtils.truncatedCompareTo(date1, date2, field);
    }

    // netcool like inspired by probe functions

    @SneakyThrows
    public static Date dateToTime(String _format, String _text)
    {
        return DateUtils.parseDate(_text, _format);
    }

    @SneakyThrows
    public static Long dateToLong(String _format, String _text)
    {
        Date _d = DateUtils.parseDate(_text, _format);
        return (_d==null) ? null : _d.getTime();
    }

    public static Date getDate()
    {
        return new Date();
    }

    public static Long getDateLong()
    {
        return new Date().getTime();
    }

    public static String timeToDate(String _format, Date _time)
    {
        SimpleDateFormat _sdf = new SimpleDateFormat(_format);
        return _sdf.format(_time);
    }

    public static String timeToDate(String _format, long _time)
    {
        SimpleDateFormat _sdf = new SimpleDateFormat(_format);
        return _sdf.format(new Date(_time));
    }

    public static String toBase(int _base, long _n)
    {
        return Long.toString(_n, _base);
    }

    public static String regReplace(String _text, String _expr, String _repl)
    {
        return replace(_text, _expr, _repl);
    }

    public static String regReplace(String _text, String _expr, String _repl, int _count)
    {
        return replace(_text, _expr, _repl, _count);
    }

    public static String decimalToAscii(String _text)
    {
        String[] _parts = StringUtils.split(_text);
        StringBuilder _sb = new StringBuilder();
        for(String _part : _parts)
        {
            _sb.append((char)NumberUtils.toInt(_part));
        }
        return _sb.toString();
    }

    public static String decimalToHex(String _text)
    {
        String[] _parts = StringUtils.split(_text);
        for(int _i=0; _i<_parts.length; _i++)
        {
            _parts[_i] = toBase(16,NumberUtils.toInt(_parts[_i]));
        }
        return StringUtils.join(_parts, "");
    }

    public static String decimalToHex(String _text, String _sep)
    {
        String[] _parts = StringUtils.split(_text, _sep);
        for(int _i=0; _i<_parts.length; _i++)
        {
            _parts[_i] = toBase(16,NumberUtils.toInt(_parts[_i]));
        }
        return StringUtils.join(_parts, "");
    }

    public static String decimalToHex(String _text, String _sep, String _delim)
    {
        String[] _parts = StringUtils.split(_text, _sep);
        for(int _i=0; _i<_parts.length; _i++)
        {
            _parts[_i] = toBase(16,NumberUtils.toInt(_parts[_i]));
        }
        return StringUtils.join(_parts, _delim);
    }

    public static String extract(String s, String rx)
    {
        Pattern p = Pattern.compile(rx);
        Matcher m = p.matcher(s);
        if(m.find())
        {
            return m.group(1);
        }
        return "";
    }

    public static String[] extractN(String s, String rx)
    {
        Pattern p = Pattern.compile(rx);
        Matcher m = p.matcher(s);
        if(m.find())
        {
            String[] sa = new String[m.groupCount()];
            for(int i=0; i< sa.length; i++)
            {
                sa[i]=m.group(i+1);
            }
            return sa;
        }
        return new String[]{};
    }

    public static String formatMsg(String _fmt, Object... _params)
    {
        return MessageFormat.format(_fmt, _params);
    }

    public static String format(String _fmt, Object... _params)
    {
        return String.format(_fmt, _params);
    }

    public static boolean fnmatch(String a, String fx)
    {
        return wcmatch(fx, a);
    }

    public static boolean wcmatch(String expr, String value)
    {
        if(value==null)
            return false;

        ArrayList<String> p = new ArrayList();

        switch(wildcard_substring(expr.toLowerCase(),p))
        {
            case -1: // presense
                return (value.length() > 0);

            case 1: // wcmatch
                return wildcard_check((String[])p.toArray(new String[] {}), value.toLowerCase());

            case 0: // simple
            default:
                return expr.equalsIgnoreCase(value);
        }
    }

    private static boolean wildcard_check(String[] pieces, String s)
    {
        // Walk the pieces to match the string
        // There are implicit stars between each piece,
        // and the first and last pieces might be "" to anchor the match.
        // assert (pieces.length > 1)
        // minimal case is <string>*<string>

        boolean result = false;
        int len = pieces.length;

        int index = 0;
        for (int i = 0; i < len; i++)
        {
            String piece = (String) pieces[i];

            if (i == len - 1)
            {
                // this is the last piece
                if (s.endsWith(piece))
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
                break;
            }
            // initial non-star; assert index == 0
            else if (i == 0)
            {
                if (!s.startsWith(piece))
                {
                    result = false;
                    break;
                }
            }
            // assert i > 0 && i < len-1
            else
            {
                // Sure wish stringbuffer supported e.g. indexOf
                index = s.indexOf(piece, index);
                if (index < 0)
                {
                    result = false;
                    break;
                }
            }
            // start beyond the matching piece
            index += piece.length();
        }

        return result;
    }

    private static int wildcard_substring(String wcstring, ArrayList pieces)
    {
        pieces.clear();
        StringBuffer ss = new StringBuffer();
        boolean wasStar = false; // indicates last piece was a star
        boolean leftstar = false; // track if the initial piece is a star
        boolean rightstar = false; // track if the final piece is a star

        char[] wcs = wcstring.toCharArray();
        int wco=0;
        // We assume (sub)strings can contain leading and trailing blanks
        for (;wco<wcs.length;)
        {
            int c = wcs[wco];
            switch (c)
            {
                case '\\' :
                    wasStar = false;
                    wco++;
                    if(wco<wcs.length)
                    {
                        c = wcs[wco]; wco++;
                        switch(c)
                        {
                            case 'n':
                                ss.append((char) 10);
                                break;
                            case 'r':
                                ss.append((char) 13);
                                break;
                            case 't':
                                ss.append((char) 9);
                                break;
                            default:
                                ss.append((char) c);
                                break;
                        }
                    }
                    break;
                case '*' :
                    if (wasStar)
                    {
                        // encountered two successive stars;
                        // I assume this is illegal but permissive
                        wco++;
                        break;
                    }
                    wco++;
                    if (ss.length() > 0)
                    {
                        pieces.add(ss.toString()); // accumulate the pieces
                        // between '*' occurrences
                    }
                    ss.setLength(0);
                    // if this is a leading star, then track it
                    if (pieces.size() == 0)
                    {
                        leftstar = true;
                    }
                    ss.setLength(0);
                    wasStar = true;
                    break;
                default :
                    wasStar = false;
                    c = wcs[wco]; wco++;
                    ss.append((char) c);
            }
        }

        if (ss.length() > 0)
        {
            pieces.add(ss.toString()); // accumulate the pieces
            ss.setLength(0);
        }

        if (pieces.size() == 0)
        {
            return -1; // presense
        }
        if (leftstar || rightstar || pieces.size() > 1)
        {
            // insert leading and/or trailing "" to anchor ends
            if (rightstar)
            {
                pieces.add("");
            }
            if (leftstar)
            {
                pieces.add(0, "");
            }
            return 1; // wcmatch
        }
        // assert !leftstar && !rightstar && pieces.size == 1
        return 0; // simple
    }

    public static String toString(Object _o)
    {
        return Objects.toString(_o);
    }

    public static String randomUUID() { return UUID.randomUUID().toString().toUpperCase(); }
    public static String randomGUID() {
        UUID _uuid = UUID.randomUUID();
        long _l = System.currentTimeMillis();
        long _n = System.nanoTime();
        return toGUID(_uuid.getMostSignificantBits(), _uuid.getLeastSignificantBits(), _l, _n);
    }

    public static String toGUID(long _n1, long _n2)
    {
        return String.format("%s-%s",
                Long.toString(_n1 & 0x7fffffffffffffffL, 13),
                Long.toString(_n2 & 0x7fffffffffffffffL, 17)
        ).toUpperCase();
    }

    public static String toGUID(long _n1, long _n2, long _n3)
    {
        return String.format("%s-%s-%s",
                Long.toString(_n1 & 0x7fffffffffffffffL, 13),
                Long.toString(_n2 & 0x7fffffffffffffffL, 17),
                Long.toString(_n3 & 0x7fffffffffffffffL, 35)
        ).toUpperCase();
    }

    public static String toGUID(long _n1, long _n2, long _n3, long _n4)
    {
        return String.format("%s-%s-%s-%s",
                Long.toString(_n1 & 0x7fffffffffffffffL, 17),
                Long.toString(_n2 & 0x7fffffffffffffffL, 17),
                Long.toString(_n3 & 0x7fffffffffffffffL, 36),
                Long.toString(_n4 & 0x7fffffffffffffffL, 36)
        ).toUpperCase();
    }

    public static String toGUID(long _n1, long _n2, long _n3, long _n4, long _n5)
    {
        return String.format("%s-%s-%s-%s-%s",
                Long.toString(_n1 & 0x7ffffffffffffffL, 36),
                Long.toString(_n2 & 0x7ffffffffffffffL, 36),
                Long.toString(_n3 & 0x7ffffffffffffffL, 36),
                Long.toString(_n4 & 0x7ffffffffffffffL, 36),
                Long.toString(_n5 & 0x7ffffffffffffffL, 36)
        ).toUpperCase();
    }

    public static String toGUID(String _name)
    {
        UUID _uuid = UUID.nameUUIDFromBytes(_name.getBytes());
        return toGUID(_name, _uuid.toString());
    }

    public static BigInteger sha512ToBigInt(String _name)
    {
        try {
            MessageDigest _md = MessageDigest.getInstance("SHA-512");
            return new BigInteger(_md.digest(_name.getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String toHashGUID(String _name)
    {
        BigInteger _bi = sha512ToBigInt(_name);

        return toGUID(_bi.longValue(),
                _bi.shiftRight(64).longValue(),
                _bi.shiftRight(128).longValue(),
                _bi.shiftRight(192).longValue(),
                _bi.shiftRight(256).longValue());
    }

    public static String toGUID(String _name1, String _name2)
    {
        UUID _uuid1 = UUID.nameUUIDFromBytes(_name1.getBytes());
        UUID _uuid2 = UUID.nameUUIDFromBytes(_name2.getBytes());
        return toGUID(_uuid1.getMostSignificantBits(),
                _uuid1.getLeastSignificantBits(),
                _uuid2.getMostSignificantBits(),
                _uuid2.getLeastSignificantBits());
    }

    public static String toGUID(String _name1, String _name2, Long _l)
    {
        UUID _uuid1 = UUID.nameUUIDFromBytes(_name1.getBytes());
        UUID _uuid2 = UUID.nameUUIDFromBytes(_name2.getBytes());
        return toGUID(_uuid1.getMostSignificantBits(),
                _uuid1.getLeastSignificantBits(),
                _uuid2.getMostSignificantBits(),
                _uuid2.getLeastSignificantBits(),
                _l);
    }

    public static String toGUID(String _name1, String _name2, String _name3)
    {
        UUID _uuid1 = UUID.nameUUIDFromBytes(_name1.getBytes());
        UUID _uuid2 = UUID.nameUUIDFromBytes((_name2).getBytes());
        UUID _uuid3 = UUID.nameUUIDFromBytes((_name3).getBytes());
        return toGUID(_uuid1.getMostSignificantBits() ^ _uuid1.getLeastSignificantBits(),
                _uuid2.getMostSignificantBits(),
                _uuid2.getLeastSignificantBits(),
                _uuid3.getMostSignificantBits(),
                _uuid3.getLeastSignificantBits());
    }

    public static String toGUID(String _name1, String _name2, String _name3, Long _l)
    {
        UUID _uuid1 = UUID.nameUUIDFromBytes(_name1.getBytes());
        UUID _uuid2 = UUID.nameUUIDFromBytes((_name2).getBytes());
        UUID _uuid3 = UUID.nameUUIDFromBytes((_name3).getBytes());
        return toGUID(
                _uuid1.getMostSignificantBits() ^ _uuid1.getLeastSignificantBits(),
                _uuid2.getMostSignificantBits() ^ _uuid2.getLeastSignificantBits(),
                _uuid3.getMostSignificantBits() ^ _uuid3.getLeastSignificantBits(),
                _l);
    }

    public static String mformat(String _pattern, Object... _objs)
    {
        return MessageFormat.format(_pattern, _objs);
    }

    public static String sformat(String _pattern, Object... _objs)
    {
        return     String.format(_pattern, _objs);
    }

    public static BigInteger sha256ToBigInt(String _name)
    {
        try {
            MessageDigest _md = MessageDigest.getInstance("SHA-256");
            return new BigInteger(_md.digest(_name.getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static BigInteger sha1ToBigInt(String _name)
    {
        try {
            MessageDigest _md = MessageDigest.getInstance("SHA-1");
            return new BigInteger(_md.digest(_name.getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String toHashGUID(String _name1, String _name2)
    {
        BigInteger _b1 = sha256ToBigInt(_name1);
        BigInteger _b2 = sha256ToBigInt(_name2);

        return toGUID(_b1.longValue(),
                _b1.shiftRight(64).longValue(),
                _b2.longValue(),
                _b2.shiftRight(64).longValue(),
                _b2.shiftRight(128).longValue());
    }

    public static String toHashGUID(String _name1, String _name2, String _name3)
    {
        BigInteger _b1 = sha1ToBigInt(_name1);
        BigInteger _b2 = sha1ToBigInt(_name2);
        BigInteger _b3 = sha1ToBigInt(_name3);

        return toGUID(_b1.longValue(),
                _b1.shiftRight(64).longValue(),
                _b2.longValue(),
                _b2.shiftRight(64).longValue(),
                _b3.longValue());
    }

    public static Map<String, Object> toMap(List<Object> _args)
    {
        Map<String, Object> _ret = new HashMap<>();
        for(int _i = 0; (_i+1)<_args.size(); _i+=2)
        {
            _ret.put(_args.get(_i).toString(), _args.get(_i+1));
        }
        return _ret;
    }

    public static Map<String, Object> toMap(Object... _args)
    {
        Map<String, Object> _ret = new HashMap<>();
        for(int _i = 0; (_i+1)<_args.length; _i+=2)
        {
            _ret.put(_args[_i].toString(), _args[_i+1]);
        }
        return _ret;
    }

    public static Map<String, Object> toMap(String _k, Object _v)
    {
        Map<String, Object> _ret = new HashMap<>();
        _ret.put(_k, _v);
        return _ret;
    }

    public static JDAO mysqlDao(String _hostPortDb, String _user, String _pass)
    {
        JDAO _dao = daoFromJdbc("com.mysql.jdbc.Driver", "jdbc:mysql://"+_hostPortDb, _user, _pass);
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

### Helper Methods

Basic invocation ```__.<method>( . . . )```

#### Context Helper

    public final Map<String, Object> loadContextFile(String _file)

#### Context Util

##### dataUtil

     public static Map<String, Object> loadContextFrom(String _file)
     public static List<Map<String, Object>> loadRowsFrom(String _file)
     public static Map<String, Object> loadContextFrom(File _file)
     public static List<Map<String, Object>> loadRowsFrom(File _file)
     public static Map<String, Object> loadContextFromIni(InputStream _fh)
     public static Map<String, Object> loadContextFromType(String _type, InputStream _fh)
     public static Map<String, Object> loadContextFromType(String _type, File _fh)
     public static List<Map<String, Object>> loadRowsFromType(String _type, InputStream _fh)
     public static List<Map<String, Object>> loadRowsFromType(String _type, File _fh)
     
     public static String unCompressedFilename(File _res)
     public static InputStream getStreamBySuffix(File _res)
     public static Map<String, ?> loadContextFromTxt(InputStream _source)
     public static List<String> loadFileLines(InputStream _source)
     public static Map<String, Object> loadContextFromTsv(InputStream _source)
     public static Map<String, Object> loadContextFromCsv(InputStream _source)
     public static Map<String, Object> loadContextFromScsv(InputStream _source)
     public static Map<String, Object> loadContextFromSqliteCsv(InputStream _source)
     public static Map<String, Object> loadContextFromSqliteList(InputStream _source)
     public static List<Map<String, Object>> readFileCsv(String _infmt, InputStream _in, Charset _cs)
     public static Map<String, Object> loadContextFromPdx(InputStream _source)
     public static Map<String, Object> loadContextFromPdata(InputStream _source)
     public static Map<String, Object> fromPdx(String _source)
     public static Map<String, Object> fromPdata(String _source)
     public static Map<String, ?> loadContextFromProperties(InputStream _source)
     public static Map<String, Object> loadContextFromYaml(InputStream _source)
     public static Map<String, Object> fromYaml(String _source)
     public static void writeContextAsType(String _type, Map<String, Object> _data, OutputStream _fh)
     public static void writeContextAsType(String _type, Map<String, Object> _data, File _fh)
     public static void writeRowsAsType(String _type, List<Map<String, Object>> _data, OutputStream _fh)
     public static void writeRowsAsType(String _type, List<Map<String, Object>> _data, File _fh)
     public static void writeAsHson(boolean _json, Writer _out, List<Map<String, Object>> _res)
     public static void writeAsHson(boolean _json, Writer _out, Map<String, Object> _res)
     public static String toHson(Map<String, Object> _res)
     public static String toJson(Map<String, Object> _res)
     public static void writeAsPdata(Writer _out, Map<String, Object> _res)
     public static String toPdata(Map<String, Object> _res)
     public static Map<String, Object> loadContextFromHjson(InputStream _source)
     public static Map<String, Object> fromJson(String _source)
     public static Map<String, Object> fromHjson(String _source)
     public static void writeAsJsonPerLine(Writer _out, List<Map<String, Object>> _res)
     public static String toJsonPerLine(List<Map<String, Object>> _res)
     public static List<Map<String, Object>> loadRowsFromJsonPerLine(InputStream _source)
     public static List<Map<String, Object>> fromJsonPerLine(String _source)

#####  image helper

     public static PixelImage pixImage(int _w, int _h)
     public static SvgImage svgImage(int _w, int _h)
     public static PdfImage pdfImage(int _w, int _h)

##### WordUtils

     public static String toCamelCase(String str, boolean capitalizeFirstLetter, char... delimiters) 
     public static String toCamelCase(String str)


##### Escape

     public static String escapeJava(String input)
     public static String escapeEcmaScript(String input)
     public static String escapeJson(String input)
     public static String unescapeJava(String input)
     public static String unescapeEcmaScript(String input)
     public static String unescapeJson(String input)
     public static String escapeHtml4(String input)
     public static String escapeHtml3(String input)
     public static String unescapeHtml4(String input)
     public static String unescapeHtml3(String input)
     public static String escapeXml(String input) 
     public static String escapeXml10(String input) 
     public static String escapeXml11(String input) 
     public static String unescapeXml(String input) 
     public static String escapeCsv(String input) 
     public static String unescapeCsv(String input) 

#####  URL codec

     public static String encodeUrl(String _raw)
     public static String decodeUrl(String _cooked)

##### dao

     public static JDAO daoFromJdbc(String jdbcDriver, String _url, String _user, String _pass)
     public static JDAO mysqlDao(String _hostPortDb, String _user, String _pass)
     public static JDAO sqliteDao(String _hostPortDb, String _user, String _pass)
     public static JDAO pgsqlDao(String _hostPortDb, String _user, String _pass)
     public static JDAO mariadbDao(String _hostPortDb, String _user, String _pass)
     public static JDAO mssqlDao(String _hostPortDb, String _user, String _pass)
     public static JDAO jtdsDao(String _hostPortDb, String _user, String _pass)

#### CommonUtil

##### logging
    public static void trace(String msg)
    public static void debug(String msg)
    public static void info(String msg)
    public static void warn(String msg)
    public static void error(String msg)

##### arrays
    public static int getLength(final Object array)
    public static <T> boolean isEmptyArray(T[] arr)
    public static boolean isEmptyArray(int[] arr)
    public static boolean isEmptyArray(char[] arr)

##### numbers
    public static boolean isNumber(final String str)
    public static boolean isNumberCreatable(final String str)

    public static Float checkFloat(Object _str)
    public static Float checkFloat(String _str)

    public static Double checkDouble(Object _str)
    public static Double checkDouble(String _str)

    public static int checkInt(final Object _str)
    public static int checkInt(String _str)
    public static Integer checkInteger(final Object _str)
    public static Integer checkInteger(String _str)
    public static Integer createInteger(String _str)

    public static Long checkLong(final Object _str)
    public static Long checkLong(String _str)

    public static BigInteger checkBigInt(String _str)
    public static BigInteger checkBigInteger(String _str)

    public static BigDecimal checkBigNum(String _str)
    public static BigDecimal checkBigDecimal(String _str)

    public static String checkStringDefaultIfNullOrBlank(Object _str, String _def, String _blank)
    public static String checkStringDefaultIfNullOrBlank(String _str, String _def, String _blank)
    public static String checkStringDefaultIfNull(Object _str, String _def)
    public static String checkStringDefaultIfNull(String _str, String _def)
    public static String checkStringDefaultIfBlank(Object _str, String _blank)
    public static String checkStringDefaultIfBlank(String _str, String _blank)
    public static String checkString(Object _str)
    public static String checkString(String _str)

    public static boolean checkBooleanDefaultIfNull(Object _bool, boolean _b)
    public static boolean checkBooleanDefaultIfNull(String _bool, boolean _b)
    public static Boolean checkBoolean(final Object _str)
    public static Boolean checkBoolean(String _str)

    public static float toFloat(String str)
    public static double toDouble(String str)
    public static int toInt(String str)
    public static int toInteger(String str)
    public static long toLong(String str)

    public static int toInt(String str, int defaultValue)
    public static long toLong(String str, long defaultValue)
    public static float toFloat(String str, float defaultValue)
    public static double toDouble(String str, double defaultValue)

##### min/max/compare
    public static long min(long... array)
    public static int min(int... array)
    public static double min(double... array)
    public static float min(float... array)
    public static long max(final long... array)
    public static int max(final int... array)
    public static double max(final double... array)
    public static float max(final float... array)
    public static long min(long a, final long b)
    public static int min(int a, final int b)
    public static double min(final double a, final double b)
    public static float min(final float a, final float b)
    public static long min(long a, final long b, final long c)
    public static int min(int a, final int b, final int c)
    public static double min(final double a, final double b, final double c)
    public static float min(final float a, final float b, final float c)
    public static long max(long a, final long b)
    public static int max(int a, final int b)
    public static double max(final double a, final double b)
    public static float max(final float a, final float b)
    public static long max(long a, final long b, final long c)
    public static int max(int a, final int b, final int c)
    public static double max(final double a, final double b, final double c)
    public static float max(final float a, final float b, final float c)
    public static int compare(final int x, final int y)
    public static int compare(final long x, final long y)

##### hash/mac
    public static String sha512HMacHex(String _key, String _buffer)
    public static String sha256HMacHex(String _key, String _buffer)
    public static String sha1HMacHex(String _key, String _buffer)
    public static String md5HMacHex(String _key, String _buffer)

    public static String hashMacHex(String _name, String _key, String... _buffer)
    public static String hashMacHex(String _name, String _key, String _buffer, String _buffer2)
    public static String hashMacHex(String _name, String _key, String _buffer, String _buffer2, String _buffer3)
    public static String hashMacHex(String _name, String _key, String _buffer)

    public static String hashHex(String _name, String... _buffer)
    public static String hashHex(String _name, String _buffer, String _buffer2)
    public static String hashHex(String _name, String _buffer, String _buffer2, String _buffer3)
    public static String hashHex(String _name, String _buffer)
    public static String sha512Hex(String _name)
    public static String sha256Hex(String _name)
    public static String sha1Hex(String _name)
    public static String md5Hex(String _name)

    public static String hashHex(String _name, byte[]... _buffer)

    public static byte[] hash(String _name, byte[]... _buffer)

    public static String md5(String data)
    public static String sha1(String data)
    public static String sha256(String data)
    public static String sha512(String data)

    public static byte[] md5(byte[] data)
    public static byte[] sha1(byte[] data)
    public static byte[] sha256(byte[] data)
    public static byte[] sha512(byte[] data)


##### bases
    public static byte[] fromHex(String _hex)
    public static byte[] fromHex(char[] _hex)

    static int toDigit(final char ch)

    public static String toHex(Long _n)
    public static String toHex(byte[] _buf)
    public static String toHex(String _buf)

    public static String toBase64(Long _n)
    public static String toBase64(byte[] binaryData)
    public static String toBase64(String binaryData)

    public static String toBase26(Long _n)
    public static String toBase26(byte[] binaryData)
    public static String toBase26(String binaryData)

    public static String toBase62(Long _n)
    public static String toBase62(byte[] binaryData)
    public static String toBase62(String binaryData)

    public static String toBase36(Long _n)
    public static String toBase36(byte[] binaryData)
    public static String toBase36(String binaryData)

    public static String base64(String binaryData)
    public static String base64(byte[] binaryData)


    public static String toGuid(String data)
    public static String toGuid(String data, String data2)

##### netcool like inspired by probe functions

    public static String regReplace(String _text, String _expr, String _repl)
    public static String regReplace(String _text, String _expr, String _repl, int _count)

    public static Date dateToTime(String _format, String _text)
    public static Long dateToLong(String _format, String _text)

    public static Date getDate()
    public static Long getDateLong()

    public static String timeToDate(String _format, Date _time)
    public static String timeToDate(String _format, long _time)

    public static String toBase(int _base, long _n)
    public static String toBase(int _base, long _n, int _min)

    public static String decimalToAscii(String _text)

    public static String decimalToHex(String _text)
    public static String decimalToHex(String _text, String _sep)
    public static String decimalToHex(String _text, String _sep, String _delim)

    public static long ipToLong(String _addr)
    public static String longToIp(long _addr)
    public static String ipToHex(String _addr)
    public static String ipToHex(long _addr)
    public static String hexToIp(String _hex)

    public static String extract(String s, String rx)
    public static String[] extractN(String s, String rx)

    public static String formatMsg(String _fmt, Object... _params)
    public static String format(String _fmt, Object... _params)
    public static String mformat(String _pattern, Object... _objs)
    public static String sformat(String _pattern, Object... _objs)

    public static boolean fnmatch(String a, String fx)
    public static boolean wcmatch(String expr, String value)

##### collections
    public static <T> T[] toArray(T... _args)
    public static Object[] toArray(Object _a1, Object _a2, Object _a3, Object _a4, Object _a5, Object _a6)
    public static String[] toArray(String _a1, String _a2, String _a3, String _a4, String _a5, String _a6)
    public static Object[] toArray(Object _a1, Object _a2, Object _a3, Object _a4, Object _a5)
    public static String[] toArray(String _a1, String _a2, String _a3, String _a4, String _a5)
    public static Object[] toArray(Object _a1, Object _a2, Object _a3, Object _a4)
    public static String[] toArray(String _a1, String _a2, String _a3, String _a4)
    public static Object[] toArray(Object _a1, Object _a2, Object _a3)
    public static String[] toArray(String _a1, String _a2, String _a3)
    public static Object[] toArray(Object _a1, Object _a2)
    public static String[] toArray(String _a1, String _a2)
    public static Object[] toArray(Object _a1)
    public static String[] toArray(String _a1)

    public static Map<String, Object> toMap(String _k1, Object _v1, String _k2, Object _v2, String _k3, Object _v3, String _k4, Object _v4, String _k5, Object _v5, String _k6, Object _v6)
    public static Map<String, Object> toMap(String _k1, Object _v1, String _k2, Object _v2, String _k3, Object _v3, String _k4, Object _v4, String _k5, Object _v5)
    public static Map<String, Object> toMap(String _k1, Object _v1, String _k2, Object _v2, String _k3, Object _v3, String _k4, Object _v4)
    public static Map<String, Object> toMap(String _k1, Object _v1, String _k2, Object _v2, String _k3, Object _v3)
    public static Map<String, Object> toMap(String _k1, Object _v1, String _k2, Object _v2)
    public static Map<String, Object> toMap(String _k, Object _v)
    public static Map<String, Object> toMap(String... _args)
    public static Map<String, Object> toMap(Object... _args)
    public static Map<String, Object> toMap(List<Object> _args)

    public static List<String> toList(String... _args)
    public static List<String> toList(String _a1, String _a2, String _a3, String _a4, String _a5, String _a6)
    public static List<String> toList(String _a1, String _a2, String _a3, String _a4, String _a5)
    public static List<String> toList(String _a1, String _a2, String _a3, String _a4)
    public static List<String> toList(String _a1, String _a2, String _a3)
    public static List<String> toList(String _a1, String _a2)
    public static <T> List<T> toList(T... _args)

    public static List<Object> toList(Object _a1, Object _a2, Object _a3, Object _a4, Object _a5, Object _a6)
    public static List<Object> toList(Object _a1, Object _a2, Object _a3, Object _a4, Object _a5)
    public static List<Object> toList(Object _a1, Object _a2, Object _a3, Object _a4)
    public static List<Object> toList(Object _a1, Object _a2, Object _a3)
    public static List<Object> toList(Object _a1, Object _a2)

    public static List newList()
    public static Map newMap()
    public static Map newLMap()
    public static String toString(Object _o)

##### StringUtils

    public static String stringClean(String str)
    public static String trim(String str)

    public static boolean isNotEmpty(String str)
    public static boolean isEmpty(String str)
    public static boolean isBlank(String str)
    public static boolean isNotBlank(String str)

    public static boolean stringEquals(String str1, String str2)
    public static boolean stringEqualsIgnoreCase(String str1, String str2)

    public static int indexOfAny(String str, String[] searchStrs)
    public static int lastIndexOfAny(String str, String[] searchStrs)

    public static String substring(String str, int start)
    public static String substring(String str, int start, int end)

    public static String left(String str, int len)
    public static String right(String str, int len)
    public static String mid(String str, int pos, int len)

    public static String[] split(String text, String separator)
    public static String[] split(String str, String separator, int max)

    public static String concat(Object[] array)
    public static String concatenate(Object[] array)

    public static String join(Object[] array, String separator)
    public static String join(List<?> list, String separator)
    public static String join(Collection<?> col, String separator)
    public static String join(Set<?> set, String separator)

    public static String replaceOnce(String text, char repl, char with)
    public static String replace(String text, char repl, char with)
    public static String replace(String text, char repl, char with, int max)
    public static String replaceOnce(String text, String repl, String with)
    public static String replace(String text, String repl, String with)
    public static String replace(String text, String repl, String with, int max)

    public static String overlayString(String text, String overlay, int start, int end)
    public static String center(String str, int size)
    public static String center(String str, int size, String delim)
    public static String chomp(String str)
    public static String chomp(String str, String sep)
    public static String chompLast(String str)
    public static String chompLast(String str, String sep)
    public static String getChomp(String str, String sep)
    public static String prechomp(String str, String sep)
    public static String getPrechomp(String str, String sep)
    public static String chopNewline(String str)
    public static String escape(String str)
    public static String rightPad(String str, int size)
    public static String rightPad(String str, int size, String delim)
    public static String leftPad(String str, int size)
    public static String leftPad(String str, int size, String delim)
    public static String strip(String str)
    public static String strip(String str, String delim)
    public static String[] stripAll(String[] strs)
    public static String[] stripAll(String[] strs, String delimiter)
    public static String stripEnd(String str, String strip)
    public static String stripStart(String str, String strip)
    public static String upperCase(String str)
    public static String lowerCase(String str)
    public static String uncapitalise(String str)
    public static String capitalise(String str)
    public static String swapCase(String str)
    public static String capitaliseAllWords(String str)
    public static String uncapitaliseAllWords(String str)

    public static String getNestedString(String str, String tag)
    public static String getNestedString(String str, String open, String close)

    public static int countMatches(String str, String sub)

    public static boolean isAlpha(String str)
    public static boolean isWhitespace(String str)
    public static boolean isAlphaSpace(String str)
    public static boolean isAlphanumeric(String str)
    public static boolean isAlphanumericSpace(String str)

    public static boolean isNumeric(String str)
    public static boolean isNumericSpace(String str)

    public static String defaultString(Object obj)
    public static String defaultString(Object obj, String defaultString)

    public static String reverse(String str)
    public static String reverseDelimitedString(String str, String delimiter)

    public static String abbreviate(String s, int maxWidth)
    public static String abbreviate(String s, int offset, int maxWidth)

    public static String difference(String s1, String s2)
    public static int differenceAt(String s1, String s2)

    public static String interpolate(String text, Map<?, ?> namespace)

    public static String removeAndHump(String data, String replaceThis)

    public static String capitalizeFirstLetter(String data)

    public static String lowercaseFirstLetter(String data)

    public static String addAndDeHump(String view)

    public static String quoteAndEscape(String source, char quoteChar)
    public static String quoteAndEscape(String source, char quoteChar, char[] quotingTriggers)
    public static String quoteAndEscape(String source, char quoteChar, char[] escapedChars, char escapeChar, boolean force)
    public static String quoteAndEscape(String source, char quoteChar, char[] escapedChars, char[] quotingTriggers, char escapeChar, boolean force)
    public static String quoteAndEscape(String source, char quoteChar, char[] escapedChars, char[] quotingTriggers, String escapePattern, boolean force)

    public static String escape(String source, char[] escapedChars, char escapeChar)
    public static String escape(String source, char[] escapedChars, String escapePattern)

    public static String removeDuplicateWhitespace(String s)

    public static String unifyLineSeparators(String s)
    public static String unifyLineSeparators(String s, String ls)

    public static boolean contains(String str, char searchChar)
    public static boolean contains(String str, String searchStr)

##### copy
    public static void copy( final InputStream input, final OutputStream output )
    public static void copy( final InputStream input,
                             final OutputStream output,
                             final int bufferSize )
    public static void copy( final Reader input, final Writer output )
    public static void copy( final Reader input, final Writer output, final int bufferSize )

    public static void copy( final InputStream input, final Writer output )
    public static void copy( final InputStream input, final Writer output, final int bufferSize )
    public static void copy( final InputStream input, final Writer output, final String encoding )
    public static void copy( final InputStream input,
                             final Writer output,
                             final String encoding,
                             final int bufferSize )

    public static String toString( final InputStream input )
    public static String toString( final InputStream input, final int bufferSize )
    public static String toString( final InputStream input, final String encoding )
    public static String toString( final InputStream input,
                                   final String encoding,
                                   final int bufferSize )

    public static byte[] toByteArray( final InputStream input )
    public static byte[] toByteArray( final InputStream input, final int bufferSize )

    public static void copy( final Reader input, final OutputStream output )
    public static void copy( final Reader input, final OutputStream output, final int bufferSize )

    public static String toString( final Reader input )
    public static String toString( final Reader input, final int bufferSize )

    public static byte[] toByteArray( final Reader input )
    public static byte[] toByteArray( final Reader input, final int bufferSize )

    public static void copy( final String input, final OutputStream output )
    public static void copy( final String input, final OutputStream output, final int bufferSize )
    public static void copy( final String input, final Writer output )

    public static void bufferedCopy( final InputStream input, final OutputStream output )

    public static byte[] toByteArray( final String input )
    public static byte[] toByteArray( final String input, final int bufferSize )

    public static void copy( final byte[] input, final Writer output )
    public static void copy( final byte[] input, final Writer output, final int bufferSize )
    public static void copy( final byte[] input, final Writer output, final String encoding )
    public static void copy( final byte[] input,
                             final Writer output,
                             final String encoding,
                             final int bufferSize )

    public static String toString( final byte[] input )
    public static String toString( final byte[] input, final int bufferSize )
    public static String toString( final byte[] input, final String encoding )
    public static String toString( final byte[] input,
                                   final String encoding,
                                   final int bufferSize )

    public static void copy( final byte[] input, final OutputStream output )
    public static void copy( final byte[] input, final OutputStream output, final int bufferSize )

    public static boolean contentEquals( final InputStream input1, final InputStream input2 )

###### closeXXX()
    public static void close( InputStream inputStream )
    public static void close( Channel channel )
    public static void close( OutputStream outputStream )
    public static void close( Reader reader )
    public static void close( Writer writer )

    public static int countPrefix(String _text, char _c)
    public static int countSuffix(String _text, char _c)

##### Boolean utilities
    public static Boolean negate(final Boolean bool)

##### boolean Boolean methods
    public static boolean isTrue(final Boolean bool)
    public static boolean isNotTrue(final Boolean bool)
    public static boolean isFalse(final Boolean bool)
    public static boolean isNotFalse(final Boolean bool)
    public static boolean toBoolean(final Boolean bool)
    public static boolean toBooleanDefaultIfNull(final Boolean bool, final boolean valueIfNull)
    public static boolean toBooleanDefaultIfNull(final String bool, final boolean valueIfNull)

##### Integer to Boolean methods
    public static boolean toBoolean(final int value)
    public static Boolean toBooleanObject(final int value)
    public static Boolean toBooleanObject(final Integer value)
    public static boolean toBoolean(final int value, final int trueValue, final int falseValue)
    public static boolean toBoolean(final Integer value, final Integer trueValue, final Integer falseValue)
    public static Boolean toBooleanObject(final int value, final int trueValue, final int falseValue, final int nullValue)
    public static Boolean toBooleanObject(final Integer value, final Integer trueValue, final Integer falseValue, final Integer nullValue)

##### Boolean to Integer methods
    public static int toInteger(final boolean bool)
    public static int toInteger(final boolean bool, final int trueValue, final int falseValue)
    public static int toInteger(final Boolean bool, final int trueValue, final int falseValue, final int nullValue)
    public static Integer toIntegerObject(final boolean bool, final Integer trueValue, final Integer falseValue)
    public static Integer toIntegerObject(final Boolean bool, final Integer trueValue, final Integer falseValue, final Integer nullValue)

##### String to Boolean methods
    public static Boolean toBooleanObject(final String str)
    public static Boolean toBooleanObject(final String str, final String trueString, final String falseString, final String nullString)

##### String to boolean methods
    public static boolean toBoolean(final String str)
    public static boolean toBoolean(final String str, final String trueString, final String falseString)
    public static boolean toBoolean(final String str, final String trueString)

##### Boolean to String methods
    public static String toStringTrueFalse(final Boolean bool)
    public static String toStringOnOff(final Boolean bool)
    public static String toStringYesNo(final Boolean bool)
    public static String toString(final Boolean bool, final String trueString, final String falseString, final String nullString)

##### boolean to String methods
    public static String toStringTrueFalse(final boolean bool)
    public static String toStringOnOff(final boolean bool)
    public static String toStringYesNo(final boolean bool)
    public static String toString(final boolean bool, final String trueString, final String falseString)
    public static int compare(final boolean x, final boolean y)
    public static boolean and(final boolean... array)
    public static Boolean and(final Boolean... array)
    public static boolean or(final boolean... array)
    public static Boolean or(final Boolean... array)
    public static boolean xor(final boolean... array)
    public static Boolean xor(final Boolean... array)
    public static boolean[] toPrimitive(final Boolean[] array)
    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull)

##### Empty/Blsnk

    public static boolean isEmpty(final CharSequence cs)
    public static boolean isNotEmpty(final CharSequence cs)
    public static boolean isAnyEmpty(final CharSequence... css)
    public static boolean isNoneEmpty(final CharSequence... css)
    public static boolean isAllEmpty(final CharSequence... css)
    public static boolean isBlank(final CharSequence cs)
    public static boolean isNotBlank(final CharSequence cs)
    public static boolean isAnyBlank(final CharSequence... css)
    public static boolean isNoneBlank(final CharSequence... css)
    public static boolean isAllBlank(final CharSequence... css)
    public static String trimToNull(final String str)
    public static String trimToEmpty(final String str)
    public static String truncate(final String str, final int maxWidth)
    public static String truncate(final String str, final int offset, final int maxWidth)

##### Stripping
    public static String stripToNull(String str)
    public static String stripToEmpty(final String str)

##### StripAll
    public static String stripAccents(final String input)

##### Equals
    public static boolean equals(final CharSequence cs1, final CharSequence cs2)
    public static boolean equalsIgnoreCase(final CharSequence str1, final CharSequence str2)

##### Compare
    public static int compare(final String str1, final String str2)
    public static int compare(final String str1, final String str2, final boolean nullIsLess)
    public static int compareIgnoreCase(final String str1, final String str2)
    public static int compareIgnoreCase(final String str1, final String str2, final boolean nullIsLess)
    public static boolean equalsAny(final CharSequence string, final CharSequence... searchStrings)
    public static boolean equalsAnyIgnoreCase(final CharSequence string, final CharSequence...searchStrings)

###### IndexOf
    public static int indexOf(final CharSequence seq, final int searchChar)
    public static int indexOf(final CharSequence cs, final int searchChar, int start)
    public static int indexOf(final CharSequence seq, final CharSequence searchSeq)
    public static int indexOf(final CharSequence seq, final CharSequence searchSeq, final int startPos)
    public static int ordinalIndexOf(final CharSequence str, final CharSequence searchStr, final int ordinal)
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr)
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int startPos)

###### LastIndexOf
    public static int lastIndexOf(final CharSequence seq, final int searchChar)
    public static int lastIndexOf(final CharSequence seq, final int searchChar, final int startPos)
    public static int lastIndexOf(final CharSequence seq, final CharSequence searchSeq)
    public static int lastOrdinalIndexOf(final CharSequence str, final CharSequence searchStr, final int ordinal)
    public static int lastIndexOf(final CharSequence seq, final CharSequence searchSeq, final int startPos)
    public static int lastIndexOfIgnoreCase(final CharSequence str, final CharSequence searchStr)
    public static int lastIndexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int startPos)

##### Contains
    public static boolean contains(final CharSequence seq, final int searchChar)
    public static boolean contains(final CharSequence seq, final CharSequence searchSeq)
    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr)
    public static boolean containsWhitespace(final CharSequence seq)

##### IndexOfAny chars
    public static int indexOfAny(final CharSequence cs, final char... searchChars)
    public static int indexOfAny(final CharSequence cs, final String searchChars)

##### ContainsAny
    public static boolean containsAny(final CharSequence cs, final char... searchChars)
    public static boolean containsAny(final CharSequence cs, final CharSequence searchChars)
    public static boolean containsAny(final CharSequence cs, final CharSequence... searchCharSequences)

##### IndexOfAnyBut chars
    public static int indexOfAnyBut(final CharSequence cs, final char... searchChars)
    public static int indexOfAnyBut(final CharSequence seq, final CharSequence searchChars)

##### ContainsOnly
    public static boolean containsOnly(final CharSequence cs, final char... valid)
    public static boolean containsOnly(final CharSequence cs, final String validChars)

##### ContainsNone
    public static boolean containsNone(final CharSequence cs, final char... searchChars)
    public static boolean containsNone(final CharSequence cs, final String invalidChars)

##### IndexOfAny strings
    public static int indexOfAny(final CharSequence str, final CharSequence... searchStrs)
    public static int lastIndexOfAny(final CharSequence str, final CharSequence... searchStrs)

##### SubStringAfter/SubStringBefore
    public static String substringBefore(final String str, final String separator)
    public static String substringAfter(final String str, final String separator)
    public static String substringBeforeLast(final String str, final String separator)
    public static String substringAfterLast(final String str, final String separator)

##### Substring between
    public static String substringBetween(final String str, final String tag)
    public static String substringBetween(final String str, final String open, final String close)
    public static String[] substringsBetween(final String str, final String open, final String close)

##### Splitting
    public static String[] split(final String str)
    public static String[] split(final String str, final char separatorChar)
    public static String[] splitByWholeSeparator(final String str, final String separator)
    public static String[] splitByWholeSeparator( final String str, final String separator, final int max)
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String str, final String separator)
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String str, final String separator, final int max)
    public static String[] splitPreserveAllTokens(final String str)
    public static String[] splitPreserveAllTokens(final String str, final char separatorChar)
    public static String[] splitPreserveAllTokens(final String str, final String separatorChars)
    public static String[] splitPreserveAllTokens(final String str, final String separatorChars, final int max)
    public static String[] splitByCharacterType(final String str)
    public static String[] splitByCharacterTypeCamelCase(final String str)

##### Joining
    public static <T> String join(final T... elements)
    public static String join(final Object[] array, final char separator)
    public static String join(final long[] array, final char separator)
    public static String join(final int[] array, final char separator)
    public static String join(final short[] array, final char separator)
    public static String join(final byte[] array, final char separator)
    public static String join(final char[] array, final char separator)
    public static String join(final float[] array, final char separator)
    public static String join(final double[] array, final char separator)
    public static String join(final Object[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final long[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final int[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final byte[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final short[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final char[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final double[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final float[] array, final char separator, final int startIndex, final int endIndex)
    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex)
    public static String join(final Iterator<?> iterator, final char separator)
    public static String join(final Iterator<?> iterator, final String separator)
    public static String join(final Iterable<?> iterable, final char separator)
    public static String join(final Iterable<?> iterable, final String separator)
    public static String join(final List<?> list, final char separator, final int startIndex, final int endIndex)
    public static String join(final List<?> list, final String separator, final int startIndex, final int endIndex)
    public static String joinWith(final String separator, final Object... objects)

##### Delete
    public static String deleteWhitespace(final String str)

##### Remove
    public static String removeStart(final String str, final String remove)
    public static String removeStartIgnoreCase(final String str, final String remove)
    public static String removeEnd(final String str, final String remove)
    public static String removeEndIgnoreCase(final String str, final String remove)
    public static String remove(final String str, final String remove)
    public static String removeIgnoreCase(final String str, final String remove)
    public static String remove(final String str, final char remove)

##### Replacing
    public static String replaceOnceIgnoreCase(final String text, final String searchString, final String replacement)
    public static String replaceIgnoreCase(final String text, final String searchString, final String replacement)
    public static String replaceIgnoreCase(final String text, final String searchString, final String replacement, final int max)
    public static String replaceEach(final String text, final String[] searchList, final String[] replacementList)
    public static String replaceEachRepeatedly(final String text, final String[] searchList, final String[] replacementList)

##### Replace, character based
    public static String replaceChars(final String str, final char searchChar, final char replaceChar)
    public static String replaceChars(final String str, final String searchChars, String replaceChars)

##### Overlay
    public static String overlay(final String str, String overlay, int start, int end)

##### Chopping
    public static String chop(final String str)

##### Padding
    public static String repeat(final String str, final int repeat)
    public static String repeat(final String str, final String separator, final int repeat)
    public static String repeat(final char ch, final int repeat)
    public static String rightPad(final String str, final int size, final char padChar)
    public static String leftPad(final String str, final int size, final char padChar)
    public static int length(final CharSequence cs)

##### Centering
    public static String center(String str, final int size, final char padChar)

##### Case conversion
    public static String capitalize(final String str)
    public static String uncapitalize(final String str)

##### Count matches
    public static int countMatches(final CharSequence str, final CharSequence sub)
    public static int countMatches(final CharSequence str, final char ch)
    public static String defaultString(final String str)
    public static String defaultString(final String str, final String defaultStr)
    public static <T extends CharSequence> T firstNonBlank(final T... values)
    public static <T extends CharSequence> T firstNonEmpty(final T... values)
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr)
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr)

##### Difference
    public static int indexOfDifference(final CharSequence cs1, final CharSequence cs2)
    public static int indexOfDifference(final CharSequence... css)
    public static String getCommonPrefix(final String... strs)

##### Misc
    public static int getLevenshteinDistance(CharSequence s, CharSequence t)
    public static int getLevenshteinDistance(CharSequence s, CharSequence t, final int threshold)
    public static double getJaroWinklerDistance(final CharSequence first, final CharSequence second)
    public static int getFuzzyDistance(final CharSequence term, final CharSequence query, final Locale locale)

##### startsWith
    public static boolean startsWith(final CharSequence str, final CharSequence prefix)
    public static boolean startsWithIgnoreCase(final CharSequence str, final CharSequence prefix)
    public static boolean startsWithAny(final CharSequence sequence, final CharSequence... searchStrings)

##### endsWith
    public static boolean endsWith(final CharSequence str, final CharSequence suffix)
    public static boolean endsWithIgnoreCase(final CharSequence str, final CharSequence suffix)

    public static String normalizeSpace(final String str)
    public static boolean endsWithAny(final CharSequence sequence, final CharSequence... searchStrings)

    public static String appendIfMissing(final String str, final CharSequence suffix, final CharSequence... suffixes)
    public static String appendIfMissingIgnoreCase(final String str, final CharSequence suffix, final CharSequence... suffixes)
    public static String prependIfMissing(final String str, final CharSequence prefix, final CharSequence... prefixes)
    public static String prependIfMissingIgnoreCase(final String str, final CharSequence prefix, final CharSequence... prefixes)

    public static String toEncodedString(final byte[] bytes, final Charset charset)

##### wrap 

    public static String wrap(final String str, final char wrapWith)
    public static String wrap(final String str, final String wrapWith)
    public static String wrapIfMissing(final String str, final char wrapWith)
    public static String wrapIfMissing(final String str, final String wrapWith)
    public static String unwrap(final String str, final String wrapToken)
    public static String unwrap(final String str, final char wrapChar)


#### GuidUtil

##### UUIDs
     public static String randomUUID()
     public static String toUUID(String _name)

##### TUIDs
     public static String toTUID(String _name) 
     public static String toTUID(String _name, String _key)

##### GUIDs
     public static String randomGUID() 
     public static String toGUID(long _n1, long _n2)
     public static String toGUID(long _n1, long _n2, long _n3)
     public static String toGUID(long _n1, long _n2, long _n3, long _n4)
     public static String toGUID(long _n1, long _n2, long _n3, long _n4, long _n5)
     public static String toHashGUID(String _name)
     public static String toHashGUID(String _name, String _key)
     public static String toGUID(String _name)
     public static String toGUID(String _name1, String _name2)
     public static String toGUID(String _name1, String _name2, String _name3)
     public static String toGUID(String _name1, long _num)
     public static String toGUID(String _name1, String _name2, long _num)
     public static String toGUID(String _name1, String _name2, String _name3, long _num)

##### XUIDs
     public static String toXUID(long _n1, long _n2)
     public static String toXUID(long _n1, long _n2, long _n3)
     public static String toXUID(long _n1, long _n2, long _n3, long _n4)
     public static String toXUID(long _n1, long _n2, long _n3, long _n4, long _n5)
     public static String toXUID(String _name)
     public static String toXUID(String _name1, String _name2)
     public static String toXUID(String _name1, String _name2, String _name3)
     public static String toXUID(String _name1, long _num)
     public static String toXUID(String _name1, String _name2, long _num)
     public static String toXUID(String _name1, String _name2, String _name3, long _num)

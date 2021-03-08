package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.imageutil.PdfImage;
import com.github.terefang.imageutil.PixelImage;
import com.github.terefang.imageutil.SvgImage;
import com.github.terefang.jdao.JDAO;
import com.github.terefang.template_maven_plugin.TemplateContext;
import com.github.terefang.template_maven_plugin.luaj.LuaScriptContext;
import lombok.SneakyThrows;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.WordUtils;
import org.apache.commons.text.CaseUtils;
import org.luaj.vm2.LuaValue;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class ContextHelper {
    TemplateContext context;

    public static ContextHelper from(TemplateContext _tc)
    {
        ContextHelper _h = new ContextHelper();
        _h.context = _tc;
        _tc.processContext.put("_", _h);
        return _h;
    }

    @SneakyThrows
    public Map<String, Object> loadContextFile(String _file)
    {
        return ContextUtil.loadContextFrom(new File(this.context.processParent, _file));
    }

    /*----- dao helper -----*/

    public JDAO daoFromJdbc(String _driver, String _url, String _user, String _pass)
    {
        return ContextUtil.daoFromJdbc(_driver, _url, _user,_pass);
    }

    /*----- lua helper -----*/

    public LuaValue lvalue(Object _o)
    {
        return LuaScriptContext.wrapJava(_o);
    }

    /*----- image helper -----*/

    public PixelImage pixImage(int _w, int _h) { return PixelImage.create(_w,_h); }

    public SvgImage svgImage(int _w, int _h) { return SvgImage.create(_w,_h); }

    public PdfImage pdfImage(int _w, int _h) { return PdfImage.create(_w,_h); }

    /*----- hmacs -----*/

    public  String hmacMd5Hex(String key, String valueToDigest) {
        return HmacUtils.hmacMd5Hex(key, valueToDigest);
    }

    public  String hmacSha1Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha1Hex(key, valueToDigest);
    }

    public  String hmacSha256Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha256Hex(key, valueToDigest);
    }

    public  String hmacSha384Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha384Hex(key, valueToDigest);
    }

    public  String hmacSha512Hex(String key, String valueToDigest) {
        return HmacUtils.hmacSha512Hex(key, valueToDigest);
    }

    /*----- digest -----*/

    public  String md2Hex(String data) {
        return DigestUtils.md2Hex(data);
    }

    public  String md5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

    public  String sha1Hex(String data) {
        return DigestUtils.sha1Hex(data);
    }

    public  String sha256Hex(String data) {
        return DigestUtils.sha256Hex(data);
    }

    public  String sha3_256Hex(String data) {
        return DigestUtils.sha3_256Hex(data);
    }

    public  String sha3_384Hex(String data) {
        return DigestUtils.sha3_384Hex(data);
    }

    public  String sha3_512Hex(String data) {
        return DigestUtils.sha3_512Hex(data);
    }

    public  String sha384Hex(String data) {
        return DigestUtils.sha384Hex(data);
    }

    public  String sha512_224Hex(String data) {
        return DigestUtils.sha512_224Hex(data);
    }
    public  String sha512_256Hex(String data) {
        return DigestUtils.sha512_256Hex(data);
    }

    public  String sha512Hex(String data) {
        return DigestUtils.sha512Hex(data);
    }

    /*-----  -----*/

    public String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords, String wrapOn) {
        return WordUtils.wrap(str, wrapLength, newLineStr, wrapLongWords, wrapOn);
    }

    public boolean containsAllWords(CharSequence word, CharSequence... words) {
        return WordUtils.containsAllWords(word, words);
    }

    @Deprecated
    public boolean isDelimiter(char ch, char[] delimiters) {
        return WordUtils.isDelimiter(ch, delimiters);
    }

    @Deprecated
    public boolean isDelimiter(int codePoint, char[] delimiters) {
        return WordUtils.isDelimiter(codePoint, delimiters);
    }

    public String abbreviate(String str, int lower, int upper, String appendToEnd) {
        return WordUtils.abbreviate(str, lower, upper, appendToEnd);
    }

    public String toCamelCase(String str, boolean capitalizeFirstLetter, char... delimiters) {
        return CaseUtils.toCamelCase(str, capitalizeFirstLetter, delimiters);
    }

    public String toCamelCase(String str) {
        return CaseUtils.toCamelCase(str, false);
    }

    public String wrap(String str, int wrapLength) {
        return WordUtils.wrap(str, wrapLength);
    }

    public String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords) {
        return WordUtils.wrap(str, wrapLength, newLineStr, wrapLongWords);
    }

    public String escapeJava(String input) {
        return StringEscapeUtils.escapeJava(input);
    }

    public String escapeEcmaScript(String input) {
        return StringEscapeUtils.escapeEcmaScript(input);
    }

    public String escapeJson(String input) {
        return StringEscapeUtils.escapeJson(input);
    }

    public String unescapeJava(String input) {
        return StringEscapeUtils.unescapeJava(input);
    }

    public String unescapeEcmaScript(String input) {
        return StringEscapeUtils.unescapeEcmaScript(input);
    }

    public String unescapeJson(String input) {
        return StringEscapeUtils.unescapeJson(input);
    }

    public String escapeHtml4(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }

    public String escapeHtml3(String input) {
        return StringEscapeUtils.escapeHtml3(input);
    }

    public String unescapeHtml4(String input) {
        return StringEscapeUtils.unescapeHtml4(input);
    }

    public String unescapeHtml3(String input) {
        return StringEscapeUtils.unescapeHtml3(input);
    }

    public String escapeXml(String input) {
        return StringEscapeUtils.escapeXml10(input);
    }

    public String escapeXml10(String input) {
        return StringEscapeUtils.escapeXml10(input);
    }

    public String escapeXml11(String input) {
        return StringEscapeUtils.escapeXml11(input);
    }

    public String unescapeXml(String input) {
        return StringEscapeUtils.unescapeXml(input);
    }

    public String escapeCsv(String input) {
        return StringEscapeUtils.escapeCsv(input);
    }

    public String unescapeCsv(String input) {
        return StringEscapeUtils.unescapeCsv(input);
    }

    public boolean isEmpty(CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    public boolean isNotEmpty(CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }

    public boolean isAnyEmpty(CharSequence... css) {
        return StringUtils.isAnyEmpty(css);
    }

    public boolean isNoneEmpty(CharSequence... css) {
        return StringUtils.isNoneEmpty(css);
    }

    public boolean isBlank(CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public boolean isNotBlank(CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }

    public boolean isAnyBlank(CharSequence... css) {
        return StringUtils.isAnyBlank(css);
    }

    public boolean isNoneBlank(CharSequence... css) {
        return StringUtils.isNoneBlank(css);
    }

    public String trim(String str) {
        return StringUtils.trim(str);
    }

    public String trimToNull(String str) {
        return StringUtils.trimToNull(str);
    }

    public String trimToEmpty(String str) {
        return StringUtils.trimToEmpty(str);
    }

    public String strip(String str) {
        return StringUtils.strip(str);
    }

    public String stripToNull(String str) {
        return StringUtils.stripToNull(str);
    }

    public String stripToEmpty(String str) {
        return StringUtils.stripToEmpty(str);
    }

    public String strip(String str, String stripChars) {
        return StringUtils.strip(str, stripChars);
    }

    public String stripStart(String str, String stripChars) {
        return StringUtils.stripStart(str, stripChars);
    }

    public String stripEnd(String str, String stripChars) {
        return StringUtils.stripEnd(str, stripChars);
    }

    public String[] stripAll(String... strs) {
        return StringUtils.stripAll(strs);
    }

    public String[] stripAll(String[] strs, String stripChars) {
        return StringUtils.stripAll(strs, stripChars);
    }

    public String stripAccents(String input) {
        return StringUtils.stripAccents(input);
    }

    public boolean equals(CharSequence cs1, CharSequence cs2) {
        return StringUtils.equals(cs1, cs2);
    }

    public boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    public int indexOf(CharSequence seq, int searchChar) {
        return StringUtils.indexOf(seq, searchChar);
    }

    public int indexOf(CharSequence seq, int searchChar, int startPos) {
        return StringUtils.indexOf(seq, searchChar, startPos);
    }

    public int indexOf(CharSequence seq, CharSequence searchSeq) {
        return StringUtils.indexOf(seq, searchSeq);
    }

    public int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return StringUtils.indexOf(seq, searchSeq, startPos);
    }

    public int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return StringUtils.ordinalIndexOf(str, searchStr, ordinal);
    }

    public int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return StringUtils.indexOfIgnoreCase(str, searchStr);
    }

    public int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public int lastIndexOf(CharSequence seq, int searchChar) {
        return StringUtils.lastIndexOf(seq, searchChar);
    }

    public int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
        return StringUtils.lastIndexOf(seq, searchChar, startPos);
    }

    public int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
        return StringUtils.lastIndexOf(seq, searchSeq);
    }

    public int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return StringUtils.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    public int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return StringUtils.lastIndexOf(seq, searchSeq, startPos);
    }

    public int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return StringUtils.lastIndexOfIgnoreCase(str, searchStr);
    }

    public int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    public boolean contains(CharSequence seq, int searchChar) {
        return StringUtils.contains(seq, searchChar);
    }

    public boolean contains(CharSequence seq, CharSequence searchSeq) {
        return StringUtils.contains(seq, searchSeq);
    }

    public boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        return StringUtils.containsIgnoreCase(str, searchStr);
    }

    public boolean containsWhitespace(CharSequence seq) {
        return StringUtils.containsWhitespace(seq);
    }

    public int indexOfAny(CharSequence cs, char... searchChars) {
        return StringUtils.indexOfAny(cs, searchChars);
    }

    public int indexOfAny(CharSequence cs, String searchChars) {
        return StringUtils.indexOfAny(cs, searchChars);
    }

    public boolean containsAny(CharSequence cs, char... searchChars) {
        return StringUtils.containsAny(cs, searchChars);
    }

    public boolean containsAny(CharSequence cs, CharSequence searchChars) {
        return StringUtils.containsAny(cs, searchChars);
    }

    public boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
        return StringUtils.containsAny(cs, searchCharSequences);
    }

    public int indexOfAnyBut(CharSequence cs, char... searchChars) {
        return StringUtils.indexOfAnyBut(cs, searchChars);
    }

    public int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
        return StringUtils.indexOfAnyBut(seq, searchChars);
    }

    public boolean containsOnly(CharSequence cs, char... valid) {
        return StringUtils.containsOnly(cs, valid);
    }

    public boolean containsOnly(CharSequence cs, String validChars) {
        return StringUtils.containsOnly(cs, validChars);
    }

    public boolean containsNone(CharSequence cs, char... searchChars) {
        return StringUtils.containsNone(cs, searchChars);
    }

    public boolean containsNone(CharSequence cs, String invalidChars) {
        return StringUtils.containsNone(cs, invalidChars);
    }

    public int indexOfAny(CharSequence str, CharSequence... searchStrs) {
        return StringUtils.indexOfAny(str, searchStrs);
    }

    public int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
        return StringUtils.lastIndexOfAny(str, searchStrs);
    }

    public String substring(String str, int start) {
        return StringUtils.substring(str, start);
    }

    public String substring(String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }

    public String left(String str, int len) {
        return StringUtils.left(str, len);
    }

    public String right(String str, int len) {
        return StringUtils.right(str, len);
    }

    public String mid(String str, int pos, int len) {
        return StringUtils.mid(str, pos, len);
    }

    public String substringBefore(String str, String separator) {
        return StringUtils.substringBefore(str, separator);
    }

    public String substringAfter(String str, String separator) {
        return StringUtils.substringAfter(str, separator);
    }

    public String substringBeforeLast(String str, String separator) {
        return StringUtils.substringBeforeLast(str, separator);
    }

    public String substringAfterLast(String str, String separator) {
        return StringUtils.substringAfterLast(str, separator);
    }

    public String substringBetween(String str, String tag) {
        return StringUtils.substringBetween(str, tag);
    }

    public String substringBetween(String str, String open, String close) {
        return StringUtils.substringBetween(str, open, close);
    }

    public String[] substringsBetween(String str, String open, String close) {
        return StringUtils.substringsBetween(str, open, close);
    }

    public String[] split(String str) {
        return StringUtils.split(str);
    }

    public String[] split(String str, char separatorChar) {
        return StringUtils.split(str, separatorChar);
    }

    public String[] split(String str, String separatorChars) {
        return StringUtils.split(str, separatorChars);
    }

    public String[] split(String str, String separatorChars, int max) {
        return StringUtils.split(str, separatorChars, max);
    }

    public String[] splitByWholeSeparator(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

    public String[] splitByWholeSeparator(String str, String separator, int max) {
        return StringUtils.splitByWholeSeparator(str, separator, max);
    }

    public String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

    public String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

    public String[] splitPreserveAllTokens(String str) {
        return StringUtils.splitPreserveAllTokens(str);
    }

    public String[] splitPreserveAllTokens(String str, char separatorChar) {
        return StringUtils.splitPreserveAllTokens(str, separatorChar);
    }

    public String[] splitPreserveAllTokens(String str, String separatorChars) {
        return StringUtils.splitPreserveAllTokens(str, separatorChars);
    }

    public String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return StringUtils.splitPreserveAllTokens(str, separatorChars, max);
    }

    public String[] splitByCharacterType(String str) {
        return StringUtils.splitByCharacterType(str);
    }

    public String[] splitByCharacterTypeCamelCase(String str) {
        return StringUtils.splitByCharacterTypeCamelCase(str);
    }

    public <T> String join(T... elements) {
        return StringUtils.join(elements);
    }

    public String join(Object[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(long[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(int[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(short[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(byte[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(char[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(float[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(double[] array, char separator) {
        return StringUtils.join(array, separator);
    }

    public String join(Object[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(long[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(int[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(byte[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(short[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(char[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(double[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(float[] array, char separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(Object[] array, String separator) {
        return StringUtils.join(array, separator);
    }

    public String join(Object[] array, String separator, int startIndex, int endIndex) {
        return StringUtils.join(array, separator, startIndex, endIndex);
    }

    public String join(Iterator<?> iterator, char separator) {
        return StringUtils.join(iterator, separator);
    }

    public String join(Iterator<?> iterator, String separator) {
        return StringUtils.join(iterator, separator);
    }

    public String join(Iterable<?> iterable, char separator) {
        return StringUtils.join(iterable, separator);
    }

    public String join(Iterable<?> iterable, String separator) {
        return StringUtils.join(iterable, separator);
    }

    public String deleteWhitespace(String str) {
        return StringUtils.deleteWhitespace(str);
    }

    public String removeStart(String str, String remove) {
        return StringUtils.removeStart(str, remove);
    }

    public String removeStartIgnoreCase(String str, String remove) {
        return StringUtils.removeStartIgnoreCase(str, remove);
    }

    public String removeEnd(String str, String remove) {
        return StringUtils.removeEnd(str, remove);
    }

    public String removeEndIgnoreCase(String str, String remove) {
        return StringUtils.removeEndIgnoreCase(str, remove);
    }

    public String remove(String str, String remove) {
        return StringUtils.remove(str, remove);
    }

    public String remove(String str, char remove) {
        return StringUtils.remove(str, remove);
    }

    public String replaceOnce(String text, String searchString, String replacement) {
        return StringUtils.replaceOnce(text, searchString, replacement);
    }

    public String replacePattern(String source, String regex, String replacement) {
        return StringUtils.replacePattern(source, regex, replacement);
    }

    public String removePattern(String source, String regex) {
        return StringUtils.removePattern(source, regex);
    }

    public String replace(String text, String searchString, String replacement) {
        return StringUtils.replace(text, searchString, replacement);
    }

    public String replace(String text, String searchString, String replacement, int max) {
        return StringUtils.replace(text, searchString, replacement, max);
    }

    public String replaceEach(String text, String[] searchList, String[] replacementList) {
        return StringUtils.replaceEach(text, searchList, replacementList);
    }

    public String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
    }

    public String replaceChars(String str, char searchChar, char replaceChar) {
        return StringUtils.replaceChars(str, searchChar, replaceChar);
    }

    public String replaceChars(String str, String searchChars, String replaceChars) {
        return StringUtils.replaceChars(str, searchChars, replaceChars);
    }

    public String overlay(String str, String overlay, int start, int end) {
        return StringUtils.overlay(str, overlay, start, end);
    }

    public String chomp(String str) {
        return StringUtils.chomp(str);
    }

    @Deprecated
    public String chomp(String str, String separator) {
        return StringUtils.chomp(str, separator);
    }

    public String chop(String str) {
        return StringUtils.chop(str);
    }

    public String repeat(String str, int repeat) {
        return StringUtils.repeat(str, repeat);
    }

    public String repeat(String str, String separator, int repeat) {
        return StringUtils.repeat(str, separator, repeat);
    }

    public String repeat(char ch, int repeat) {
        return StringUtils.repeat(ch, repeat);
    }

    public String rightPad(String str, int size) {
        return StringUtils.rightPad(str, size);
    }

    public String rightPad(String str, int size, char padChar) {
        return StringUtils.rightPad(str, size, padChar);
    }

    public String rightPad(String str, int size, String padStr) {
        return StringUtils.rightPad(str, size, padStr);
    }

    public String leftPad(String str, int size) {
        return StringUtils.leftPad(str, size);
    }

    public String leftPad(String str, int size, char padChar) {
        return StringUtils.leftPad(str, size, padChar);
    }

    public String leftPad(String str, int size, String padStr) {
        return StringUtils.leftPad(str, size, padStr);
    }

    public int length(CharSequence cs) {
        return StringUtils.length(cs);
    }

    public String center(String str, int size) {
        return StringUtils.center(str, size);
    }

    public String center(String str, int size, char padChar) {
        return StringUtils.center(str, size, padChar);
    }

    public String center(String str, int size, String padStr) {
        return StringUtils.center(str, size, padStr);
    }

    public String upperCase(String str) {
        return StringUtils.upperCase(str);
    }

    public String upperCase(String str, Locale locale) {
        return StringUtils.upperCase(str, locale);
    }

    public String lowerCase(String str) {
        return StringUtils.lowerCase(str);
    }

    public String lowerCase(String str, Locale locale) {
        return StringUtils.lowerCase(str, locale);
    }

    public String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

    public String capitalize(String str, char... delimiters) {
        return WordUtils.capitalize(str, delimiters);
    }

    public String capitalizeFully(String str) {
        return WordUtils.capitalizeFully(str);
    }

    public String capitalizeFully(String str, char... delimiters) {
        return WordUtils.capitalizeFully(str, delimiters);
    }

    public String uncapitalize(String str) {
        return StringUtils.uncapitalize(str);
    }

    public String uncapitalize(String str, char... delimiters) {
        return WordUtils.uncapitalize(str, delimiters);
    }

    public String swapCase(String str) {
        return StringUtils.swapCase(str);
    }

    public String initials(String str) {
        return WordUtils.initials(str);
    }

    public String initials(String str, char... delimiters) {
        return WordUtils.initials(str, delimiters);
    }

    public int countMatches(CharSequence str, CharSequence sub) {
        return StringUtils.countMatches(str, sub);
    }

    public int countMatches(CharSequence str, char ch) {
        return StringUtils.countMatches(str, ch);
    }

    public boolean isAlpha(CharSequence cs) {
        return StringUtils.isAlpha(cs);
    }

    public boolean isAlphaSpace(CharSequence cs) {
        return StringUtils.isAlphaSpace(cs);
    }

    public boolean isAlphanumeric(CharSequence cs) {
        return StringUtils.isAlphanumeric(cs);
    }

    public boolean isAlphanumericSpace(CharSequence cs) {
        return StringUtils.isAlphanumericSpace(cs);
    }

    public boolean isAsciiPrintable(CharSequence cs) {
        return StringUtils.isAsciiPrintable(cs);
    }

    public boolean isNumeric(CharSequence cs) {
        return StringUtils.isNumeric(cs);
    }

    public boolean isNumericSpace(CharSequence cs) {
        return StringUtils.isNumericSpace(cs);
    }

    public boolean isWhitespace(CharSequence cs) {
        return StringUtils.isWhitespace(cs);
    }

    public boolean isAllLowerCase(CharSequence cs) {
        return StringUtils.isAllLowerCase(cs);
    }

    public boolean isAllUpperCase(CharSequence cs) {
        return StringUtils.isAllUpperCase(cs);
    }

    public String defaultString(String str) {
        return StringUtils.defaultString(str);
    }

    public String defaultString(String str, String defaultStr) {
        return StringUtils.defaultString(str, defaultStr);
    }

    public <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return StringUtils.defaultIfBlank(str, defaultStr);
    }

    public <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return StringUtils.defaultIfEmpty(str, defaultStr);
    }

    public String reverse(String str) {
        return StringUtils.reverse(str);
    }

    public String reverseDelimited(String str, char separatorChar) {
        return StringUtils.reverseDelimited(str, separatorChar);
    }

    public String abbreviate(String str, int maxWidth) {
        return StringUtils.abbreviate(str, maxWidth);
    }

    public String abbreviate(String str, int offset, int maxWidth) {
        return StringUtils.abbreviate(str, offset, maxWidth);
    }

    public String abbreviateMiddle(String str, String middle, int length) {
        return StringUtils.abbreviateMiddle(str, middle, length);
    }

    public String difference(String str1, String str2) {
        return StringUtils.difference(str1, str2);
    }

    public int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        return StringUtils.indexOfDifference(cs1, cs2);
    }

    public int indexOfDifference(CharSequence... css) {
        return StringUtils.indexOfDifference(css);
    }

    public String getCommonPrefix(String... strs) {
        return StringUtils.getCommonPrefix(strs);
    }

    public int getLevenshteinDistance(CharSequence s, CharSequence t) {
        return StringUtils.getLevenshteinDistance(s, t);
    }

    public int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
        return StringUtils.getLevenshteinDistance(s, t, threshold);
    }

    public double getJaroWinklerDistance(CharSequence first, CharSequence second) {
        return StringUtils.getJaroWinklerDistance(first, second);
    }

    public int getFuzzyDistance(CharSequence term, CharSequence query, Locale locale) {
        return StringUtils.getFuzzyDistance(term, query, locale);
    }

    public boolean startsWith(CharSequence str, CharSequence prefix) {
        return StringUtils.startsWith(str, prefix);
    }

    public boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return StringUtils.startsWithIgnoreCase(str, prefix);
    }

    public boolean startsWithAny(CharSequence string, CharSequence... searchStrings) {
        return StringUtils.startsWithAny(string, searchStrings);
    }

    public boolean endsWith(CharSequence str, CharSequence suffix) {
        return StringUtils.endsWith(str, suffix);
    }

    public boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return StringUtils.endsWithIgnoreCase(str, suffix);
    }

    public String normalizeSpace(String str) {
        return StringUtils.normalizeSpace(str);
    }

    public boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
        return StringUtils.endsWithAny(string, searchStrings);
    }

    public String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
        return StringUtils.appendIfMissing(str, suffix, suffixes);
    }

    public String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
        return StringUtils.appendIfMissingIgnoreCase(str, suffix, suffixes);
    }

    public String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
        return StringUtils.prependIfMissing(str, prefix, prefixes);
    }

    public String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
        return StringUtils.prependIfMissingIgnoreCase(str, prefix, prefixes);
    }

    @Deprecated
    public String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return StringUtils.toString(bytes, charsetName);
    }

    public String toEncodedString(byte[] bytes, Charset charset) {
        return StringUtils.toEncodedString(bytes, charset);
    }

    public String wrap(String str, char wrapWith) {
        return StringUtils.wrap(str, wrapWith);
    }

    public String wrap(String str, String wrapWith) {
        return StringUtils.wrap(str, wrapWith);
    }

    /*-----  -----*/

    public int toInt(String str) {
        return NumberUtils.toInt(str);
    }

    public int toInt(String str, int defaultValue) {
        return NumberUtils.toInt(str, defaultValue);
    }

    public long toLong(String str) {
        return NumberUtils.toLong(str);
    }

    public long toLong(String str, long defaultValue) {
        return NumberUtils.toLong(str, defaultValue);
    }

    public float toFloat(String str) {
        return NumberUtils.toFloat(str);
    }

    public float toFloat(String str, float defaultValue) {
        return NumberUtils.toFloat(str, defaultValue);
    }

    public double toDouble(String str) {
        return NumberUtils.toDouble(str);
    }

    public double toDouble(String str, double defaultValue) {
        return NumberUtils.toDouble(str, defaultValue);
    }

    public byte toByte(String str) {
        return NumberUtils.toByte(str);
    }

    public byte toByte(String str, byte defaultValue) {
        return NumberUtils.toByte(str, defaultValue);
    }

    public short toShort(String str) {
        return NumberUtils.toShort(str);
    }

    public short toShort(String str, short defaultValue) {
        return NumberUtils.toShort(str, defaultValue);
    }

    public Number createNumber(String str) throws NumberFormatException {
        return NumberUtils.createNumber(str);
    }

    public Float createFloat(String str) {
        return NumberUtils.createFloat(str);
    }

    public Double createDouble(String str) {
        return NumberUtils.createDouble(str);
    }

    public Integer createInteger(String str) {
        return NumberUtils.createInteger(str);
    }

    public Long createLong(String str) {
        return NumberUtils.createLong(str);
    }

    public BigInteger createBigInteger(String str) {
        return NumberUtils.createBigInteger(str);
    }

    public BigDecimal createBigDecimal(String str) {
        return NumberUtils.createBigDecimal(str);
    }

    public long min(long... array) {
        return NumberUtils.min(array);
    }

    public int min(int... array) {
        return NumberUtils.min(array);
    }

    public short min(short... array) {
        return NumberUtils.min(array);
    }

    public byte min(byte... array) {
        return NumberUtils.min(array);
    }

    public double min(double... array) {
        return NumberUtils.min(array);
    }

    public float min(float... array) {
        return NumberUtils.min(array);
    }

    public long max(long... array) {
        return NumberUtils.max(array);
    }

    public int max(int... array) {
        return NumberUtils.max(array);
    }

    public short max(short... array) {
        return NumberUtils.max(array);
    }

    public byte max(byte... array) {
        return NumberUtils.max(array);
    }

    public double max(double... array) {
        return NumberUtils.max(array);
    }

    public float max(float... array) {
        return NumberUtils.max(array);
    }

    public long min(long a, long b, long c) {
        return NumberUtils.min(a, b, c);
    }

    public int min(int a, int b, int c) {
        return NumberUtils.min(a, b, c);
    }

    public short min(short a, short b, short c) {
        return NumberUtils.min(a, b, c);
    }

    public byte min(byte a, byte b, byte c) {
        return NumberUtils.min(a, b, c);
    }

    public double min(double a, double b, double c) {
        return NumberUtils.min(a, b, c);
    }

    public float min(float a, float b, float c) {
        return NumberUtils.min(a, b, c);
    }

    public long max(long a, long b, long c) {
        return NumberUtils.max(a, b, c);
    }

    public int max(int a, int b, int c) {
        return NumberUtils.max(a, b, c);
    }

    public short max(short a, short b, short c) {
        return NumberUtils.max(a, b, c);
    }

    public byte max(byte a, byte b, byte c) {
        return NumberUtils.max(a, b, c);
    }

    public double max(double a, double b, double c) {
        return NumberUtils.max(a, b, c);
    }

    public float max(float a, float b, float c) {
        return NumberUtils.max(a, b, c);
    }

    public boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }

    public boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    public boolean isParsable(String str) {
        return NumberUtils.isParsable(str);
    }

    public int compare(int x, int y) {
        return NumberUtils.compare(x, y);
    }

    public int compare(long x, long y) {
        return NumberUtils.compare(x, y);
    }

    public int compare(short x, short y) {
        return NumberUtils.compare(x, y);
    }

    public int compare(byte x, byte y) {
        return NumberUtils.compare(x, y);
    }

    public Boolean negate(Boolean bool) {
        return BooleanUtils.negate(bool);
    }

    public boolean isTrue(Boolean bool) {
        return BooleanUtils.isTrue(bool);
    }

    public boolean isNotTrue(Boolean bool) {
        return BooleanUtils.isNotTrue(bool);
    }

    public boolean isFalse(Boolean bool) {
        return BooleanUtils.isFalse(bool);
    }

    public boolean isNotFalse(Boolean bool) {
        return BooleanUtils.isNotFalse(bool);
    }

    public boolean toBoolean(Boolean bool) {
        return BooleanUtils.toBoolean(bool);
    }

    public boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull) {
        return BooleanUtils.toBooleanDefaultIfNull(bool, valueIfNull);
    }

    public boolean toBoolean(int value) {
        return BooleanUtils.toBoolean(value);
    }

    public Boolean toBooleanObject(int value) {
        return BooleanUtils.toBooleanObject(value);
    }

    public Boolean toBooleanObject(Integer value) {
        return BooleanUtils.toBooleanObject(value);
    }

    public boolean toBoolean(int value, int trueValue, int falseValue) {
        return BooleanUtils.toBoolean(value, trueValue, falseValue);
    }

    public boolean toBoolean(Integer value, Integer trueValue, Integer falseValue) {
        return BooleanUtils.toBoolean(value, trueValue, falseValue);
    }

    public Boolean toBooleanObject(int value, int trueValue, int falseValue, int nullValue) {
        return BooleanUtils.toBooleanObject(value, trueValue, falseValue, nullValue);
    }

    public Boolean toBooleanObject(Integer value, Integer trueValue, Integer falseValue, Integer nullValue) {
        return BooleanUtils.toBooleanObject(value, trueValue, falseValue, nullValue);
    }

    public int toInteger(boolean bool) {
        return BooleanUtils.toInteger(bool);
    }

    public Integer toIntegerObject(boolean bool) {
        return BooleanUtils.toIntegerObject(bool);
    }

    public Integer toIntegerObject(Boolean bool) {
        return BooleanUtils.toIntegerObject(bool);
    }

    public int toInteger(boolean bool, int trueValue, int falseValue) {
        return BooleanUtils.toInteger(bool, trueValue, falseValue);
    }

    public int toInteger(Boolean bool, int trueValue, int falseValue, int nullValue) {
        return BooleanUtils.toInteger(bool, trueValue, falseValue, nullValue);
    }

    public Integer toIntegerObject(boolean bool, Integer trueValue, Integer falseValue) {
        return BooleanUtils.toIntegerObject(bool, trueValue, falseValue);
    }

    public Integer toIntegerObject(Boolean bool, Integer trueValue, Integer falseValue, Integer nullValue) {
        return BooleanUtils.toIntegerObject(bool, trueValue, falseValue, nullValue);
    }

    public Boolean toBooleanObject(String str) {
        return BooleanUtils.toBooleanObject(str);
    }

    public Boolean toBooleanObject(String str, String trueString, String falseString, String nullString) {
        return BooleanUtils.toBooleanObject(str, trueString, falseString, nullString);
    }

    public boolean toBoolean(String str) {
        return BooleanUtils.toBoolean(str);
    }

    public boolean toBoolean(String str, String trueString, String falseString) {
        return BooleanUtils.toBoolean(str, trueString, falseString);
    }

    public String toStringTrueFalse(Boolean bool) {
        return BooleanUtils.toStringTrueFalse(bool);
    }

    public String toStringOnOff(Boolean bool) {
        return BooleanUtils.toStringOnOff(bool);
    }

    public String toStringYesNo(Boolean bool) {
        return BooleanUtils.toStringYesNo(bool);
    }

    public String toString(Boolean bool, String trueString, String falseString, String nullString) {
        return BooleanUtils.toString(bool, trueString, falseString, nullString);
    }

    public String toStringTrueFalse(boolean bool) {
        return BooleanUtils.toStringTrueFalse(bool);
    }

    public String toStringOnOff(boolean bool) {
        return BooleanUtils.toStringOnOff(bool);
    }

    public String toStringYesNo(boolean bool) {
        return BooleanUtils.toStringYesNo(bool);
    }

    public String toString(boolean bool, String trueString, String falseString) {
        return BooleanUtils.toString(bool, trueString, falseString);
    }

    public boolean and(boolean... array) {
        return BooleanUtils.and(array);
    }

    public Boolean and(Boolean... array) {
        return BooleanUtils.and(array);
    }

    public boolean or(boolean... array) {
        return BooleanUtils.or(array);
    }

    public Boolean or(Boolean... array) {
        return BooleanUtils.or(array);
    }

    public boolean xor(boolean... array) {
        return BooleanUtils.xor(array);
    }

    public Boolean xor(Boolean... array) {
        return BooleanUtils.xor(array);
    }

    public int compare(boolean x, boolean y) {
        return BooleanUtils.compare(x, y);
    }

    /*-----  -----*/

    public boolean checkBoolean(final Object _str)
    {
        if(_str==null)
        {
            return false;
        }

        return checkBoolean(_str.toString());
    }

    public boolean checkBoolean(final String _str)
    {
        if (_str == null) {
            return false;
        }

        // any defined string is "true" unless it is a false indicator:
        // false, off, none, no, null, nul, nil, 0
        if (containsAny(_str.trim().toLowerCase(),
                "false", "off", "none", "no", "null", "nul", "nil", "0"))
        {
            return false;
        }

        return true;
    }

}

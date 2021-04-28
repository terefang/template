package com.github.terefang.template_maven_plugin.util;

import com.github.terefang.jmelange.dao.JDAO;
import com.github.terefang.jmelange.image.PdfImage;
import com.github.terefang.jmelange.image.PixelImage;
import com.github.terefang.jmelange.image.SvgImage;
import com.github.terefang.template_maven_plugin.TemplateContext;
import lombok.SneakyThrows;

import org.apache.commons.codec.DecoderException;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.*;

public class ContextHelper {
    TemplateContext context;

    public static ContextHelper from(TemplateContext _tc)
    {
        ContextHelper _h = new ContextHelper();
        _h.context = _tc;
        _tc.processContext.put("__", _h);
        return _h;
    }

    @SneakyThrows
    public final Map<String, Object> loadContextFile(String _file)
    {
        return ContextUtil.loadContextFrom(new File(this.context.processParent, _file));
    }

    /*----- dao helper -----*/

    public final JDAO daoFromJdbc(String _driver, String _url, String _user, String _pass)
    {
        return ContextUtil.daoFromJdbc(_driver, _url, _user,_pass);
    }

    public static String toString(Object _o)
    {
        return Objects.toString(_o);
    }

    public final PixelImage pixImage(int _w, int _h) {
        return ContextUtil.pixImage(_w, _h);
    }

    public final SvgImage svgImage(int _w, int _h) {
        return ContextUtil.svgImage(_w, _h);
    }

    public final PdfImage pdfImage(int _w, int _h) {
        return ContextUtil.pdfImage(_w, _h);
    }

    public final String hmacMd5Hex(String key, String valueToDigest) {
        return ContextUtil.hmacMd5Hex(key, valueToDigest);
    }

    public final String hmacSha1Hex(String key, String valueToDigest) {
        return ContextUtil.hmacSha1Hex(key, valueToDigest);
    }

    public final String hmacSha256Hex(String key, String valueToDigest) {
        return ContextUtil.hmacSha256Hex(key, valueToDigest);
    }

    public final String hmacSha384Hex(String key, String valueToDigest) {
        return ContextUtil.hmacSha384Hex(key, valueToDigest);
    }

    public final String hmacSha512Hex(String key, String valueToDigest) {
        return ContextUtil.hmacSha512Hex(key, valueToDigest);
    }

    public final String md5Hex(String data) {
        return ContextUtil.md5Hex(data);
    }

    public final String sha1Hex(String data) {
        return ContextUtil.sha1Hex(data);
    }

    public final String sha256Hex(String data) {
        return ContextUtil.sha256Hex(data);
    }

    public final String sha384Hex(String data) {
        return ContextUtil.sha384Hex(data);
    }

    public final String sha512Hex(String data) {
        return ContextUtil.sha512Hex(data);
    }

    public final String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords, String wrapOn) {
        return ContextUtil.wrap(str, wrapLength, newLineStr, wrapLongWords, wrapOn);
    }

    public final boolean containsAllWords(CharSequence word, CharSequence... words) {
        return ContextUtil.containsAllWords(word, words);
    }

    @Deprecated
    public final boolean isDelimiter(char ch, char[] delimiters) {
        return ContextUtil.isDelimiter(ch, delimiters);
    }

    @Deprecated
    public final boolean isDelimiter(int codePoint, char[] delimiters) {
        return ContextUtil.isDelimiter(codePoint, delimiters);
    }

    public final String abbreviate(String str, int lower, int upper, String appendToEnd) {
        return ContextUtil.abbreviate(str, lower, upper, appendToEnd);
    }

    public final String toCamelCase(String str, boolean capitalizeFirstLetter, char... delimiters) {
        return ContextUtil.toCamelCase(str, capitalizeFirstLetter, delimiters);
    }

    public final String toCamelCase(String str) {
        return ContextUtil.toCamelCase(str);
    }

    public final String wrap(String str, int wrapLength) {
        return ContextUtil.wrap(str, wrapLength);
    }

    public final String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords) {
        return ContextUtil.wrap(str, wrapLength, newLineStr, wrapLongWords);
    }

    public final String escapeJava(String input) {
        return ContextUtil.escapeJava(input);
    }

    public final String escapeEcmaScript(String input) {
        return ContextUtil.escapeEcmaScript(input);
    }

    public final String escapeJson(String input) {
        return ContextUtil.escapeJson(input);
    }

    public final String unescapeJava(String input) {
        return ContextUtil.unescapeJava(input);
    }

    public final String unescapeEcmaScript(String input) {
        return ContextUtil.unescapeEcmaScript(input);
    }

    public final String unescapeJson(String input) {
        return ContextUtil.unescapeJson(input);
    }

    public final String escapeHtml4(String input) {
        return ContextUtil.escapeHtml4(input);
    }

    public final String escapeHtml3(String input) {
        return ContextUtil.escapeHtml3(input);
    }

    public final String unescapeHtml4(String input) {
        return ContextUtil.unescapeHtml4(input);
    }

    public final String unescapeHtml3(String input) {
        return ContextUtil.unescapeHtml3(input);
    }

    public final String escapeXml(String input) {
        return ContextUtil.escapeXml(input);
    }

    public final String escapeXml10(String input) {
        return ContextUtil.escapeXml10(input);
    }

    public final String escapeXml11(String input) {
        return ContextUtil.escapeXml11(input);
    }

    public final String unescapeXml(String input) {
        return ContextUtil.unescapeXml(input);
    }

    public final String escapeCsv(String input) {
        return ContextUtil.escapeCsv(input);
    }

    public final String unescapeCsv(String input) {
        return ContextUtil.unescapeCsv(input);
    }

    public final boolean isEmpty(CharSequence cs) {
        return ContextUtil.isEmpty(cs);
    }

    public final boolean isNotEmpty(CharSequence cs) {
        return ContextUtil.isNotEmpty(cs);
    }

    public final boolean isAnyEmpty(CharSequence... css) {
        return ContextUtil.isAnyEmpty(css);
    }

    public final boolean isNoneEmpty(CharSequence... css) {
        return ContextUtil.isNoneEmpty(css);
    }

    public final boolean isBlank(CharSequence cs) {
        return ContextUtil.isBlank(cs);
    }

    public final boolean isNotBlank(CharSequence cs) {
        return ContextUtil.isNotBlank(cs);
    }

    public final boolean isAnyBlank(CharSequence... css) {
        return ContextUtil.isAnyBlank(css);
    }

    public final boolean isNoneBlank(CharSequence... css) {
        return ContextUtil.isNoneBlank(css);
    }

    public final String trim(String str) {
        return ContextUtil.trim(str);
    }

    public final String trimToNull(String str) {
        return ContextUtil.trimToNull(str);
    }

    public final String trimToEmpty(String str) {
        return ContextUtil.trimToEmpty(str);
    }

    public final String strip(String str) {
        return ContextUtil.strip(str);
    }

    public final String stripToNull(String str) {
        return ContextUtil.stripToNull(str);
    }

    public final String stripToEmpty(String str) {
        return ContextUtil.stripToEmpty(str);
    }

    public final String strip(String str, String stripChars) {
        return ContextUtil.strip(str, stripChars);
    }

    public final String stripStart(String str, String stripChars) {
        return ContextUtil.stripStart(str, stripChars);
    }

    public final String stripEnd(String str, String stripChars) {
        return ContextUtil.stripEnd(str, stripChars);
    }

    public final String[] stripAll(String... strs) {
        return ContextUtil.stripAll(strs);
    }

    public final String[] stripAll(String[] strs, String stripChars) {
        return ContextUtil.stripAll(strs, stripChars);
    }

    public final String stripAccents(String input) {
        return ContextUtil.stripAccents(input);
    }

    public final boolean equals(CharSequence cs1, CharSequence cs2) {
        return ContextUtil.equals(cs1, cs2);
    }

    public final boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return ContextUtil.equalsIgnoreCase(str1, str2);
    }

    public final int indexOf(CharSequence seq, int searchChar) {
        return ContextUtil.indexOf(seq, searchChar);
    }

    public final int indexOf(CharSequence seq, int searchChar, int startPos) {
        return ContextUtil.indexOf(seq, searchChar, startPos);
    }

    public final int indexOf(CharSequence seq, CharSequence searchSeq) {
        return ContextUtil.indexOf(seq, searchSeq);
    }

    public final int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return ContextUtil.indexOf(seq, searchSeq, startPos);
    }

    public final int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return ContextUtil.ordinalIndexOf(str, searchStr, ordinal);
    }

    public final int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return ContextUtil.indexOfIgnoreCase(str, searchStr);
    }

    public final int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return ContextUtil.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public final int lastIndexOf(CharSequence seq, int searchChar) {
        return ContextUtil.lastIndexOf(seq, searchChar);
    }

    public final int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
        return ContextUtil.lastIndexOf(seq, searchChar, startPos);
    }

    public final int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
        return ContextUtil.lastIndexOf(seq, searchSeq);
    }

    public final int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return ContextUtil.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    public final int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return ContextUtil.lastIndexOf(seq, searchSeq, startPos);
    }

    public final int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return ContextUtil.lastIndexOfIgnoreCase(str, searchStr);
    }

    public final int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return ContextUtil.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    public final boolean contains(CharSequence seq, int searchChar) {
        return ContextUtil.contains(seq, searchChar);
    }

    public final boolean contains(CharSequence seq, CharSequence searchSeq) {
        return ContextUtil.contains(seq, searchSeq);
    }

    public final boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        return ContextUtil.containsIgnoreCase(str, searchStr);
    }

    public final boolean containsWhitespace(CharSequence seq) {
        return ContextUtil.containsWhitespace(seq);
    }

    public final int indexOfAny(CharSequence cs, char... searchChars) {
        return ContextUtil.indexOfAny(cs, searchChars);
    }

    public final int indexOfAny(CharSequence cs, String searchChars) {
        return ContextUtil.indexOfAny(cs, searchChars);
    }

    public final boolean containsAny(CharSequence cs, char... searchChars) {
        return ContextUtil.containsAny(cs, searchChars);
    }

    public final boolean containsAny(CharSequence cs, CharSequence searchChars) {
        return ContextUtil.containsAny(cs, searchChars);
    }

    public final boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
        return ContextUtil.containsAny(cs, searchCharSequences);
    }

    public final int indexOfAnyBut(CharSequence cs, char... searchChars) {
        return ContextUtil.indexOfAnyBut(cs, searchChars);
    }

    public final int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
        return ContextUtil.indexOfAnyBut(seq, searchChars);
    }

    public final boolean containsOnly(CharSequence cs, char... valid) {
        return ContextUtil.containsOnly(cs, valid);
    }

    public final boolean containsOnly(CharSequence cs, String validChars) {
        return ContextUtil.containsOnly(cs, validChars);
    }

    public final boolean containsNone(CharSequence cs, char... searchChars) {
        return ContextUtil.containsNone(cs, searchChars);
    }

    public final boolean containsNone(CharSequence cs, String invalidChars) {
        return ContextUtil.containsNone(cs, invalidChars);
    }

    public final int indexOfAny(CharSequence str, CharSequence... searchStrs) {
        return ContextUtil.indexOfAny(str, searchStrs);
    }

    public final int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
        return ContextUtil.lastIndexOfAny(str, searchStrs);
    }

    public final String substring(String str, int start) {
        return ContextUtil.substring(str, start);
    }

    public final String substring(String str, int start, int end) {
        return ContextUtil.substring(str, start, end);
    }

    public final String left(String str, int len) {
        return ContextUtil.left(str, len);
    }

    public final String right(String str, int len) {
        return ContextUtil.right(str, len);
    }

    public final String mid(String str, int pos, int len) {
        return ContextUtil.mid(str, pos, len);
    }

    public final String substringBefore(String str, String separator) {
        return ContextUtil.substringBefore(str, separator);
    }

    public final String substringAfter(String str, String separator) {
        return ContextUtil.substringAfter(str, separator);
    }

    public final String substringBeforeLast(String str, String separator) {
        return ContextUtil.substringBeforeLast(str, separator);
    }

    public final String substringAfterLast(String str, String separator) {
        return ContextUtil.substringAfterLast(str, separator);
    }

    public final String substringBetween(String str, String tag) {
        return ContextUtil.substringBetween(str, tag);
    }

    public final String substringBetween(String str, String open, String close) {
        return ContextUtil.substringBetween(str, open, close);
    }

    public final String[] substringsBetween(String str, String open, String close) {
        return ContextUtil.substringsBetween(str, open, close);
    }

    public final String[] split(String str) {
        return ContextUtil.split(str);
    }

    public final String[] split(String str, char separatorChar) {
        return ContextUtil.split(str, separatorChar);
    }

    public final String[] split(String str, String separatorChars) {
        return ContextUtil.split(str, separatorChars);
    }

    public final String[] split(String str, String separatorChars, int max) {
        return ContextUtil.split(str, separatorChars, max);
    }

    public final String[] splitByWholeSeparator(String str, String separator) {
        return ContextUtil.splitByWholeSeparator(str, separator);
    }

    public final String[] splitByWholeSeparator(String str, String separator, int max) {
        return ContextUtil.splitByWholeSeparator(str, separator, max);
    }

    public final String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return ContextUtil.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

    public final String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return ContextUtil.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

    public final String[] splitPreserveAllTokens(String str) {
        return ContextUtil.splitPreserveAllTokens(str);
    }

    public final String[] splitPreserveAllTokens(String str, char separatorChar) {
        return ContextUtil.splitPreserveAllTokens(str, separatorChar);
    }

    public final String[] splitPreserveAllTokens(String str, String separatorChars) {
        return ContextUtil.splitPreserveAllTokens(str, separatorChars);
    }

    public final String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return ContextUtil.splitPreserveAllTokens(str, separatorChars, max);
    }

    public final String[] splitByCharacterType(String str) {
        return ContextUtil.splitByCharacterType(str);
    }

    public final String[] splitByCharacterTypeCamelCase(String str) {
        return ContextUtil.splitByCharacterTypeCamelCase(str);
    }

    public final <T> String join(T... elements) {
        return ContextUtil.join(elements);
    }

    public final String join(Object[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(long[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(int[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(short[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(byte[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(char[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(float[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(double[] array, char separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(Object[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(long[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(int[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(byte[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(short[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(char[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(double[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(float[] array, char separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(Object[] array, String separator) {
        return ContextUtil.join(array, separator);
    }

    public final String join(Object[] array, String separator, int startIndex, int endIndex) {
        return ContextUtil.join(array, separator, startIndex, endIndex);
    }

    public final String join(Iterator<?> iterator, char separator) {
        return ContextUtil.join(iterator, separator);
    }

    public final String join(Iterator<?> iterator, String separator) {
        return ContextUtil.join(iterator, separator);
    }

    public final String join(Iterable<?> iterable, char separator) {
        return ContextUtil.join(iterable, separator);
    }

    public final String join(Iterable<?> iterable, String separator) {
        return ContextUtil.join(iterable, separator);
    }

    public final String deleteWhitespace(String str) {
        return ContextUtil.deleteWhitespace(str);
    }

    public final String removeStart(String str, String remove) {
        return ContextUtil.removeStart(str, remove);
    }

    public final String removeStartIgnoreCase(String str, String remove) {
        return ContextUtil.removeStartIgnoreCase(str, remove);
    }

    public final String removeEnd(String str, String remove) {
        return ContextUtil.removeEnd(str, remove);
    }

    public final String removeEndIgnoreCase(String str, String remove) {
        return ContextUtil.removeEndIgnoreCase(str, remove);
    }

    public final String remove(String str, String remove) {
        return ContextUtil.remove(str, remove);
    }

    public final String remove(String str, char remove) {
        return ContextUtil.remove(str, remove);
    }

    public final String replaceOnce(String text, String searchString, String replacement) {
        return ContextUtil.replaceOnce(text, searchString, replacement);
    }

    public final String replacePattern(String source, String regex, String replacement) {
        return ContextUtil.replacePattern(source, regex, replacement);
    }

    public final String removePattern(String source, String regex) {
        return ContextUtil.removePattern(source, regex);
    }

    public final String replace(String text, String searchString, String replacement) {
        return ContextUtil.replace(text, searchString, replacement);
    }

    public final String replace(String text, String searchString, String replacement, int max) {
        return ContextUtil.replace(text, searchString, replacement, max);
    }

    public final String replaceEach(String text, String[] searchList, String[] replacementList) {
        return ContextUtil.replaceEach(text, searchList, replacementList);
    }

    public final String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return ContextUtil.replaceEachRepeatedly(text, searchList, replacementList);
    }

    public final String replaceChars(String str, char searchChar, char replaceChar) {
        return ContextUtil.replaceChars(str, searchChar, replaceChar);
    }

    public final String replaceChars(String str, String searchChars, String replaceChars) {
        return ContextUtil.replaceChars(str, searchChars, replaceChars);
    }

    public final String overlay(String str, String overlay, int start, int end) {
        return ContextUtil.overlay(str, overlay, start, end);
    }

    public final String chomp(String str) {
        return ContextUtil.chomp(str);
    }

    @Deprecated
    public final String chomp(String str, String separator) {
        return ContextUtil.chomp(str, separator);
    }

    public final String chop(String str) {
        return ContextUtil.chop(str);
    }

    public final String repeat(String str, int repeat) {
        return ContextUtil.repeat(str, repeat);
    }

    public final String repeat(String str, String separator, int repeat) {
        return ContextUtil.repeat(str, separator, repeat);
    }

    public final String repeat(char ch, int repeat) {
        return ContextUtil.repeat(ch, repeat);
    }

    public final String rightPad(String str, int size) {
        return ContextUtil.rightPad(str, size);
    }

    public final String rightPad(String str, int size, char padChar) {
        return ContextUtil.rightPad(str, size, padChar);
    }

    public final String rightPad(String str, int size, String padStr) {
        return ContextUtil.rightPad(str, size, padStr);
    }

    public final String leftPad(String str, int size) {
        return ContextUtil.leftPad(str, size);
    }

    public final String leftPad(String str, int size, char padChar) {
        return ContextUtil.leftPad(str, size, padChar);
    }

    public final String leftPad(String str, int size, String padStr) {
        return ContextUtil.leftPad(str, size, padStr);
    }

    public final int length(CharSequence cs) {
        return ContextUtil.length(cs);
    }

    public final String center(String str, int size) {
        return ContextUtil.center(str, size);
    }

    public final String center(String str, int size, char padChar) {
        return ContextUtil.center(str, size, padChar);
    }

    public final String center(String str, int size, String padStr) {
        return ContextUtil.center(str, size, padStr);
    }

    public final String upperCase(String str) {
        return ContextUtil.upperCase(str);
    }

    public final String upperCase(String str, Locale locale) {
        return ContextUtil.upperCase(str, locale);
    }

    public final String lowerCase(String str) {
        return ContextUtil.lowerCase(str);
    }

    public final String lowerCase(String str, Locale locale) {
        return ContextUtil.lowerCase(str, locale);
    }

    public final String capitalize(String str) {
        return ContextUtil.capitalize(str);
    }

    public final String capitalize(String str, char... delimiters) {
        return ContextUtil.capitalize(str, delimiters);
    }

    public final String capitalizeFully(String str) {
        return ContextUtil.capitalizeFully(str);
    }

    public final String capitalizeFully(String str, char... delimiters) {
        return ContextUtil.capitalizeFully(str, delimiters);
    }

    public final String uncapitalize(String str) {
        return ContextUtil.uncapitalize(str);
    }

    public final String uncapitalize(String str, char... delimiters) {
        return ContextUtil.uncapitalize(str, delimiters);
    }

    public final String swapCase(String str) {
        return ContextUtil.swapCase(str);
    }

    public final String initials(String str) {
        return ContextUtil.initials(str);
    }

    public final String initials(String str, char... delimiters) {
        return ContextUtil.initials(str, delimiters);
    }

    public final int countMatches(CharSequence str, CharSequence sub) {
        return ContextUtil.countMatches(str, sub);
    }

    public final int countMatches(CharSequence str, char ch) {
        return ContextUtil.countMatches(str, ch);
    }

    public final boolean isAlpha(CharSequence cs) {
        return ContextUtil.isAlpha(cs);
    }

    public final boolean isAlphaSpace(CharSequence cs) {
        return ContextUtil.isAlphaSpace(cs);
    }

    public final boolean isAlphanumeric(CharSequence cs) {
        return ContextUtil.isAlphanumeric(cs);
    }

    public final boolean isAlphanumericSpace(CharSequence cs) {
        return ContextUtil.isAlphanumericSpace(cs);
    }

    public final boolean isAsciiPrintable(CharSequence cs) {
        return ContextUtil.isAsciiPrintable(cs);
    }

    public final boolean isNumeric(CharSequence cs) {
        return ContextUtil.isNumeric(cs);
    }

    public final boolean isNumericSpace(CharSequence cs) {
        return ContextUtil.isNumericSpace(cs);
    }

    public final boolean isWhitespace(CharSequence cs) {
        return ContextUtil.isWhitespace(cs);
    }

    public final boolean isAllLowerCase(CharSequence cs) {
        return ContextUtil.isAllLowerCase(cs);
    }

    public final boolean isAllUpperCase(CharSequence cs) {
        return ContextUtil.isAllUpperCase(cs);
    }

    public final String defaultString(String str) {
        return ContextUtil.defaultString(str);
    }

    public final String defaultString(String str, String defaultStr) {
        return ContextUtil.defaultString(str, defaultStr);
    }

    public final <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return ContextUtil.defaultIfBlank(str, defaultStr);
    }

    public final <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return ContextUtil.defaultIfEmpty(str, defaultStr);
    }

    public final String reverse(String str) {
        return ContextUtil.reverse(str);
    }

    public final String reverseDelimited(String str, char separatorChar) {
        return ContextUtil.reverseDelimited(str, separatorChar);
    }

    public final String abbreviate(String str, int maxWidth) {
        return ContextUtil.abbreviate(str, maxWidth);
    }

    public final String abbreviate(String str, int offset, int maxWidth) {
        return ContextUtil.abbreviate(str, offset, maxWidth);
    }

    public final String abbreviateMiddle(String str, String middle, int length) {
        return ContextUtil.abbreviateMiddle(str, middle, length);
    }

    public final String difference(String str1, String str2) {
        return ContextUtil.difference(str1, str2);
    }

    public final int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        return ContextUtil.indexOfDifference(cs1, cs2);
    }

    public final int indexOfDifference(CharSequence... css) {
        return ContextUtil.indexOfDifference(css);
    }

    public final String getCommonPrefix(String... strs) {
        return ContextUtil.getCommonPrefix(strs);
    }

    public final int getLevenshteinDistance(CharSequence s, CharSequence t) {
        return ContextUtil.getLevenshteinDistance(s, t);
    }

    public final int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
        return ContextUtil.getLevenshteinDistance(s, t, threshold);
    }

    public final double getJaroWinklerDistance(CharSequence first, CharSequence second) {
        return ContextUtil.getJaroWinklerDistance(first, second);
    }

    public final int getFuzzyDistance(CharSequence term, CharSequence query, Locale locale) {
        return ContextUtil.getFuzzyDistance(term, query, locale);
    }

    public final boolean startsWith(CharSequence str, CharSequence prefix) {
        return ContextUtil.startsWith(str, prefix);
    }

    public final boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return ContextUtil.startsWithIgnoreCase(str, prefix);
    }

    public final boolean startsWithAny(CharSequence string, CharSequence... searchStrings) {
        return ContextUtil.startsWithAny(string, searchStrings);
    }

    public final boolean endsWith(CharSequence str, CharSequence suffix) {
        return ContextUtil.endsWith(str, suffix);
    }

    public final boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return ContextUtil.endsWithIgnoreCase(str, suffix);
    }

    public final String normalizeSpace(String str) {
        return ContextUtil.normalizeSpace(str);
    }

    public final boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
        return ContextUtil.endsWithAny(string, searchStrings);
    }

    public final String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
        return ContextUtil.appendIfMissing(str, suffix, suffixes);
    }

    public final String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
        return ContextUtil.appendIfMissingIgnoreCase(str, suffix, suffixes);
    }

    public final String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
        return ContextUtil.prependIfMissing(str, prefix, prefixes);
    }

    public final String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
        return ContextUtil.prependIfMissingIgnoreCase(str, prefix, prefixes);
    }

    @Deprecated
    public final String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return ContextUtil.toString(bytes, charsetName);
    }

    public final String toEncodedString(byte[] bytes, Charset charset) {
        return ContextUtil.toEncodedString(bytes, charset);
    }

    public final String wrap(String str, char wrapWith) {
        return ContextUtil.wrap(str, wrapWith);
    }

    public final String wrap(String str, String wrapWith) {
        return ContextUtil.wrap(str, wrapWith);
    }

    public final int toInt(String str) {
        return ContextUtil.toInt(str);
    }

    public final int toInt(String str, int defaultValue) {
        return ContextUtil.toInt(str, defaultValue);
    }

    public final long toLong(String str) {
        return ContextUtil.toLong(str);
    }

    public final long toLong(String str, long defaultValue) {
        return ContextUtil.toLong(str, defaultValue);
    }

    public final float toFloat(String str) {
        return ContextUtil.toFloat(str);
    }

    public final float toFloat(String str, float defaultValue) {
        return ContextUtil.toFloat(str, defaultValue);
    }

    public final double toDouble(String str) {
        return ContextUtil.toDouble(str);
    }

    public final double toDouble(String str, double defaultValue) {
        return ContextUtil.toDouble(str, defaultValue);
    }

    public final byte toByte(String str) {
        return ContextUtil.toByte(str);
    }

    public final byte toByte(String str, byte defaultValue) {
        return ContextUtil.toByte(str, defaultValue);
    }

    public final short toShort(String str) {
        return ContextUtil.toShort(str);
    }

    public final short toShort(String str, short defaultValue) {
        return ContextUtil.toShort(str, defaultValue);
    }

    public final Number createNumber(String str) throws NumberFormatException {
        return ContextUtil.createNumber(str);
    }

    public final Float createFloat(String str) {
        return ContextUtil.createFloat(str);
    }

    public final Double createDouble(String str) {
        return ContextUtil.createDouble(str);
    }

    public final Integer createInteger(String str) {
        return ContextUtil.createInteger(str);
    }

    public final Long createLong(String str) {
        return ContextUtil.createLong(str);
    }

    public final BigInteger createBigInteger(String str) {
        return ContextUtil.createBigInteger(str);
    }

    public final BigDecimal createBigDecimal(String str) {
        return ContextUtil.createBigDecimal(str);
    }

    public final long min(long... array) {
        return ContextUtil.min(array);
    }

    public final int min(int... array) {
        return ContextUtil.min(array);
    }

    public final short min(short... array) {
        return ContextUtil.min(array);
    }

    public final byte min(byte... array) {
        return ContextUtil.min(array);
    }

    public final double min(double... array) {
        return ContextUtil.min(array);
    }

    public final float min(float... array) {
        return ContextUtil.min(array);
    }

    public final long max(long... array) {
        return ContextUtil.max(array);
    }

    public final int max(int... array) {
        return ContextUtil.max(array);
    }

    public final short max(short... array) {
        return ContextUtil.max(array);
    }

    public final byte max(byte... array) {
        return ContextUtil.max(array);
    }

    public final double max(double... array) {
        return ContextUtil.max(array);
    }

    public final float max(float... array) {
        return ContextUtil.max(array);
    }

    public final long min(long a, long b, long c) {
        return ContextUtil.min(a, b, c);
    }

    public final int min(int a, int b, int c) {
        return ContextUtil.min(a, b, c);
    }

    public final short min(short a, short b, short c) {
        return ContextUtil.min(a, b, c);
    }

    public final byte min(byte a, byte b, byte c) {
        return ContextUtil.min(a, b, c);
    }

    public final double min(double a, double b, double c) {
        return ContextUtil.min(a, b, c);
    }

    public final float min(float a, float b, float c) {
        return ContextUtil.min(a, b, c);
    }

    public final long max(long a, long b, long c) {
        return ContextUtil.max(a, b, c);
    }

    public final int max(int a, int b, int c) {
        return ContextUtil.max(a, b, c);
    }

    public final short max(short a, short b, short c) {
        return ContextUtil.max(a, b, c);
    }

    public final byte max(byte a, byte b, byte c) {
        return ContextUtil.max(a, b, c);
    }

    public final double max(double a, double b, double c) {
        return ContextUtil.max(a, b, c);
    }

    public final float max(float a, float b, float c) {
        return ContextUtil.max(a, b, c);
    }

    public final boolean isDigits(String str) {
        return ContextUtil.isDigits(str);
    }

    public final boolean isNumber(String str) {
        return ContextUtil.isNumber(str);
    }

    public final boolean isParsable(String str) {
        return ContextUtil.isParsable(str);
    }

    public final int compare(int x, int y) {
        return ContextUtil.compare(x, y);
    }

    public final int compare(long x, long y) {
        return ContextUtil.compare(x, y);
    }

    public final int compare(short x, short y) {
        return ContextUtil.compare(x, y);
    }

    public final int compare(byte x, byte y) {
        return ContextUtil.compare(x, y);
    }

    public final Boolean negate(Boolean bool) {
        return ContextUtil.negate(bool);
    }

    public final boolean isTrue(Boolean bool) {
        return ContextUtil.isTrue(bool);
    }

    public final boolean isNotTrue(Boolean bool) {
        return ContextUtil.isNotTrue(bool);
    }

    public final boolean isFalse(Boolean bool) {
        return ContextUtil.isFalse(bool);
    }

    public final boolean isNotFalse(Boolean bool) {
        return ContextUtil.isNotFalse(bool);
    }

    public final boolean toBoolean(Boolean bool) {
        return ContextUtil.toBoolean(bool);
    }

    public final boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull) {
        return ContextUtil.toBooleanDefaultIfNull(bool, valueIfNull);
    }

    public final boolean toBoolean(int value) {
        return ContextUtil.toBoolean(value);
    }

    public final Boolean toBooleanObject(int value) {
        return ContextUtil.toBooleanObject(value);
    }

    public final Boolean toBooleanObject(Integer value) {
        return ContextUtil.toBooleanObject(value);
    }

    public final boolean toBoolean(int value, int trueValue, int falseValue) {
        return ContextUtil.toBoolean(value, trueValue, falseValue);
    }

    public final boolean toBoolean(Integer value, Integer trueValue, Integer falseValue) {
        return ContextUtil.toBoolean(value, trueValue, falseValue);
    }

    public final Boolean toBooleanObject(int value, int trueValue, int falseValue, int nullValue) {
        return ContextUtil.toBooleanObject(value, trueValue, falseValue, nullValue);
    }

    public final Boolean toBooleanObject(Integer value, Integer trueValue, Integer falseValue, Integer nullValue) {
        return ContextUtil.toBooleanObject(value, trueValue, falseValue, nullValue);
    }

    public final int toInteger(boolean bool) {
        return ContextUtil.toInteger(bool);
    }

    public final Integer toIntegerObject(boolean bool) {
        return ContextUtil.toIntegerObject(bool);
    }

    public final Integer toIntegerObject(Boolean bool) {
        return ContextUtil.toIntegerObject(bool);
    }

    public final int toInteger(boolean bool, int trueValue, int falseValue) {
        return ContextUtil.toInteger(bool, trueValue, falseValue);
    }

    public final int toInteger(Boolean bool, int trueValue, int falseValue, int nullValue) {
        return ContextUtil.toInteger(bool, trueValue, falseValue, nullValue);
    }

    public final Integer toIntegerObject(boolean bool, Integer trueValue, Integer falseValue) {
        return ContextUtil.toIntegerObject(bool, trueValue, falseValue);
    }

    public final Integer toIntegerObject(Boolean bool, Integer trueValue, Integer falseValue, Integer nullValue) {
        return ContextUtil.toIntegerObject(bool, trueValue, falseValue, nullValue);
    }

    public final Boolean toBooleanObject(String str) {
        return ContextUtil.toBooleanObject(str);
    }

    public final Boolean toBooleanObject(String str, String trueString, String falseString, String nullString) {
        return ContextUtil.toBooleanObject(str, trueString, falseString, nullString);
    }

    public final boolean toBoolean(String str) {
        return ContextUtil.toBoolean(str);
    }

    public final boolean toBoolean(String str, String trueString, String falseString) {
        return ContextUtil.toBoolean(str, trueString, falseString);
    }

    public final String toStringTrueFalse(Boolean bool) {
        return ContextUtil.toStringTrueFalse(bool);
    }

    public final String toStringOnOff(Boolean bool) {
        return ContextUtil.toStringOnOff(bool);
    }

    public final String toStringYesNo(Boolean bool) {
        return ContextUtil.toStringYesNo(bool);
    }

    public final String toString(Boolean bool, String trueString, String falseString, String nullString) {
        return ContextUtil.toString(bool, trueString, falseString, nullString);
    }

    public final String toStringTrueFalse(boolean bool) {
        return ContextUtil.toStringTrueFalse(bool);
    }

    public final String toStringOnOff(boolean bool) {
        return ContextUtil.toStringOnOff(bool);
    }

    public final String toStringYesNo(boolean bool) {
        return ContextUtil.toStringYesNo(bool);
    }

    public final String toString(boolean bool, String trueString, String falseString) {
        return ContextUtil.toString(bool, trueString, falseString);
    }

    public final boolean and(boolean... array) {
        return ContextUtil.and(array);
    }

    public final Boolean and(Boolean... array) {
        return ContextUtil.and(array);
    }

    public final boolean or(boolean... array) {
        return ContextUtil.or(array);
    }

    public final Boolean or(Boolean... array) {
        return ContextUtil.or(array);
    }

    public final boolean xor(boolean... array) {
        return ContextUtil.xor(array);
    }

    public final Boolean xor(Boolean... array) {
        return ContextUtil.xor(array);
    }

    public final int compare(boolean x, boolean y) {
        return ContextUtil.compare(x, y);
    }

    public final boolean checkBoolean(Object _str) {
        return ContextUtil.checkBoolean(_str);
    }

    public final boolean checkBoolean(String _str) {
        return ContextUtil.checkBoolean(_str);
    }

    public final byte[] decodeHex(String data) throws DecoderException {
        return ContextUtil.decodeHex(data);
    }

    public final byte[] decodeHex(char[] data) throws DecoderException {
        return ContextUtil.decodeHex(data);
    }

    public final char[] encodeHex(byte[] data) {
        return ContextUtil.encodeHex(data);
    }

    public final char[] encodeHex(byte[] data, boolean toLowerCase) {
        return ContextUtil.encodeHex(data, toLowerCase);
    }

    public final String encodeHexString(byte[] data) {
        return ContextUtil.encodeHexString(data);
    }

    public final String encodeHexString(byte[] data, boolean toLowerCase) {
        return ContextUtil.encodeHexString(data, toLowerCase);
    }

    @SneakyThrows
    public final String encodePercent(String _raw) {
        return ContextUtil.encodePercent(_raw);
    }

    @SneakyThrows
    public final String decodePercent(String _cooked) {
        return ContextUtil.decodePercent(_cooked);
    }

    @SneakyThrows
    public final String encodeUrl(String _raw) {
        return ContextUtil.encodeUrl(_raw);
    }

    @SneakyThrows
    public final String decodeUrl(String _cooked) {
        return ContextUtil.decodeUrl(_cooked);
    }

    public final boolean isSameDay(Date date1, Date date2) {
        return ContextUtil.isSameDay(date1, date2);
    }

    public final boolean isSameDay(Calendar cal1, Calendar cal2) {
        return ContextUtil.isSameDay(cal1, cal2);
    }

    public final boolean isSameInstant(Date date1, Date date2) {
        return ContextUtil.isSameInstant(date1, date2);
    }

    public final boolean isSameInstant(Calendar cal1, Calendar cal2) {
        return ContextUtil.isSameInstant(cal1, cal2);
    }

    public final boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        return ContextUtil.isSameLocalTime(cal1, cal2);
    }

    public final Date parseDate(String str, String... parsePatterns) throws ParseException {
        return ContextUtil.parseDate(str, parsePatterns);
    }

    public final Date parseDate(String str, Locale locale, String... parsePatterns) throws ParseException {
        return ContextUtil.parseDate(str, locale, parsePatterns);
    }

    public final Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return ContextUtil.parseDateStrictly(str, parsePatterns);
    }

    public final Date parseDateStrictly(String str, Locale locale, String... parsePatterns) throws ParseException {
        return ContextUtil.parseDateStrictly(str, locale, parsePatterns);
    }

    public final Date addYears(Date date, int amount) {
        return ContextUtil.addYears(date, amount);
    }

    public final Date addMonths(Date date, int amount) {
        return ContextUtil.addMonths(date, amount);
    }

    public final Date addWeeks(Date date, int amount) {
        return ContextUtil.addWeeks(date, amount);
    }

    public final Date addDays(Date date, int amount) {
        return ContextUtil.addDays(date, amount);
    }

    public final Date addHours(Date date, int amount) {
        return ContextUtil.addHours(date, amount);
    }

    public final Date addMinutes(Date date, int amount) {
        return ContextUtil.addMinutes(date, amount);
    }

    public final Date addSeconds(Date date, int amount) {
        return ContextUtil.addSeconds(date, amount);
    }

    public final Date addMilliseconds(Date date, int amount) {
        return ContextUtil.addMilliseconds(date, amount);
    }

    public final Date setYears(Date date, int amount) {
        return ContextUtil.setYears(date, amount);
    }

    public final Date setMonths(Date date, int amount) {
        return ContextUtil.setMonths(date, amount);
    }

    public final Date setDays(Date date, int amount) {
        return ContextUtil.setDays(date, amount);
    }

    public final Date setHours(Date date, int amount) {
        return ContextUtil.setHours(date, amount);
    }

    public final Date setMinutes(Date date, int amount) {
        return ContextUtil.setMinutes(date, amount);
    }

    public final Date setSeconds(Date date, int amount) {
        return ContextUtil.setSeconds(date, amount);
    }

    public final Date setMilliseconds(Date date, int amount) {
        return ContextUtil.setMilliseconds(date, amount);
    }

    public final Calendar toCalendar(Date date) {
        return ContextUtil.toCalendar(date);
    }

    public final Calendar toCalendar(Date date, TimeZone tz) {
        return ContextUtil.toCalendar(date, tz);
    }

    public final Date dateRound(Date date, int field) {
        return ContextUtil.dateRound(date, field);
    }

    public final Calendar dateRound(Calendar date, int field) {
        return ContextUtil.dateRound(date, field);
    }

    public final Date dateRound(Object date, int field) {
        return ContextUtil.dateRound(date, field);
    }

    public final Date dateTruncate(Date date, int field) {
        return ContextUtil.dateTruncate(date, field);
    }

    public final Calendar dateTruncate(Calendar date, int field) {
        return ContextUtil.dateTruncate(date, field);
    }

    public final Date dateTruncate(Object date, int field) {
        return ContextUtil.dateTruncate(date, field);
    }

    public final Date dateCeiling(Date date, int field) {
        return ContextUtil.dateCeiling(date, field);
    }

    public final Calendar dateCeiling(Calendar date, int field) {
        return ContextUtil.dateCeiling(date, field);
    }

    public final Date dateCeiling(Object date, int field) {
        return ContextUtil.dateCeiling(date, field);
    }

    public final Iterator<Calendar> dateIterator(Date focus, int rangeStyle) {
        return ContextUtil.dateIterator(focus, rangeStyle);
    }

    public final Iterator<Calendar> dateIterator(Calendar focus, int rangeStyle) {
        return ContextUtil.dateIterator(focus, rangeStyle);
    }

    public final Iterator<?> dateIterator(Object focus, int rangeStyle) {
        return ContextUtil.dateIterator(focus, rangeStyle);
    }

    public final long getFragmentInMilliseconds(Date date, int fragment) {
        return ContextUtil.getFragmentInMilliseconds(date, fragment);
    }

    public final long getFragmentInSeconds(Date date, int fragment) {
        return ContextUtil.getFragmentInSeconds(date, fragment);
    }

    public final long getFragmentInMinutes(Date date, int fragment) {
        return ContextUtil.getFragmentInMinutes(date, fragment);
    }

    public final long getFragmentInHours(Date date, int fragment) {
        return ContextUtil.getFragmentInHours(date, fragment);
    }

    public final long getFragmentInDays(Date date, int fragment) {
        return ContextUtil.getFragmentInDays(date, fragment);
    }

    public final long getFragmentInMilliseconds(Calendar calendar, int fragment) {
        return ContextUtil.getFragmentInMilliseconds(calendar, fragment);
    }

    public final long getFragmentInSeconds(Calendar calendar, int fragment) {
        return ContextUtil.getFragmentInSeconds(calendar, fragment);
    }

    public final long getFragmentInMinutes(Calendar calendar, int fragment) {
        return ContextUtil.getFragmentInMinutes(calendar, fragment);
    }

    public final long getFragmentInHours(Calendar calendar, int fragment) {
        return ContextUtil.getFragmentInHours(calendar, fragment);
    }

    public final long getFragmentInDays(Calendar calendar, int fragment) {
        return ContextUtil.getFragmentInDays(calendar, fragment);
    }

    public final boolean dateTruncatedEquals(Calendar cal1, Calendar cal2, int field) {
        return ContextUtil.dateTruncatedEquals(cal1, cal2, field);
    }

    public final boolean dateTruncatedEquals(Date date1, Date date2, int field) {
        return ContextUtil.dateTruncatedEquals(date1, date2, field);
    }

    public final int dateTruncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
        return ContextUtil.dateTruncatedCompareTo(cal1, cal2, field);
    }

    public final int dateTruncatedCompareTo(Date date1, Date date2, int field) {
        return ContextUtil.dateTruncatedCompareTo(date1, date2, field);
    }

    @SneakyThrows
    public final Date dateToTime(String _format, String _text) {
        return ContextUtil.dateToTime(_format, _text);
    }

    @SneakyThrows
    public final Long dateToLong(String _format, String _text) {
        return ContextUtil.dateToLong(_format, _text);
    }

    public final Date getDate() {
        return ContextUtil.getDate();
    }

    public final Long getDateLong() {
        return ContextUtil.getDateLong();
    }

    public final String timeToDate(String _format, Date _time) {
        return ContextUtil.timeToDate(_format, _time);
    }

    public final String timeToDate(String _format, long _time) {
        return ContextUtil.timeToDate(_format, _time);
    }

    public final String toBase(int _base, long _n) {
        return ContextUtil.toBase(_base, _n);
    }

    public final String regReplace(String _text, String _expr, String _repl) {
        return ContextUtil.regReplace(_text, _expr, _repl);
    }

    public final String regReplace(String _text, String _expr, String _repl, int _count) {
        return ContextUtil.regReplace(_text, _expr, _repl, _count);
    }

    public final String decimalToAscii(String _text) {
        return ContextUtil.decimalToAscii(_text);
    }

    public final String decimalToHex(String _text) {
        return ContextUtil.decimalToHex(_text);
    }

    public final String decimalToHex(String _text, String _sep) {
        return ContextUtil.decimalToHex(_text, _sep);
    }

    public final String decimalToHex(String _text, String _sep, String _delim) {
        return ContextUtil.decimalToHex(_text, _sep, _delim);
    }

    public final String extract(String s, String rx) {
        return ContextUtil.extract(s, rx);
    }

    public final String[] extractN(String s, String rx) {
        return ContextUtil.extractN(s, rx);
    }

    public final String formatMsg(String _fmt, Object... _params) {
        return ContextUtil.formatMsg(_fmt, _params);
    }

    public final String format(String _fmt, Object... _params) {
        return ContextUtil.format(_fmt, _params);
    }

    public final boolean fnmatch(String a, String fx) {
        return ContextUtil.fnmatch(a, fx);
    }

    public final boolean wcmatch(String expr, String value) {
        return ContextUtil.wcmatch(expr, value);
    }

    public final String randomUUID() {
        return ContextUtil.randomUUID();
    }

    public final String randomGUID() {
        return ContextUtil.randomGUID();
    }

    public final String toGUID(long _n1, long _n2) {
        return ContextUtil.toGUID(_n1, _n2);
    }

    public final String toGUID(long _n1, long _n2, long _n3) {
        return ContextUtil.toGUID(_n1, _n2, _n3);
    }

    public final String toGUID(long _n1, long _n2, long _n3, long _n4) {
        return ContextUtil.toGUID(_n1, _n2, _n3, _n4);
    }

    public final String toGUID(long _n1, long _n2, long _n3, long _n4, long _n5) {
        return ContextUtil.toGUID(_n1, _n2, _n3, _n4, _n5);
    }

    public final String toGUID(String _name) {
        return ContextUtil.toGUID(_name);
    }

    public final String toHashGUID(String _name) {
        return ContextUtil.toHashGUID(_name);
    }

    public final String toGUID(String _name1, String _name2) {
        return ContextUtil.toGUID(_name1, _name2);
    }

    public final String toGUID(String _name1, String _name2, String _name3) {
        return ContextUtil.toGUID(_name1, _name2, _name3);
    }

    public final String toGUID(String _name1, String _name2, Long _l) {
        return ContextUtil.toGUID(_name1, _name2, _l);
    }

    public final String toGUID(String _name1, String _name2, String _name3, Long _l) {
        return ContextUtil.toGUID(_name1, _name2, _name3, _l);
    }

    public final String mformat(String _pattern, Object... _objs) {
        return ContextUtil.mformat(_pattern, _objs);
    }

    public final String sformat(String _pattern, Object... _objs) {
        return ContextUtil.sformat(_pattern, _objs);
    }

    public final String toHashGUID(String _name1, String _name2) {
        return ContextUtil.toHashGUID(_name1, _name2);
    }

    public final String toHashGUID(String _name1, String _name2, String _name3) {
        return ContextUtil.toHashGUID(_name1, _name2, _name3);
    }

    public final Map<String, Object> toMap(List<Object> _args) {
        return ContextUtil.toMap(_args);
    }

    public final Map<String, Object> toMap(Object... _args) {
        return ContextUtil.toMap(_args);
    }

    public final Map<String, Object> toMap(String _k, Object _v) {
        return ContextUtil.toMap(_k, _v);
    }

    public final Map<String, Object> fromYaml(String _source)
    {
        return ContextUtil.fromYaml(_source);
    }

    public final Map<String, Object> fromJson(String _source)
    {
        return ContextUtil.fromJson(_source);
    }

    public final Map<String, Object> fromHjson(String _source)
    {
        return ContextUtil.fromHjson(_source);
    }

    public final Map<String, Object> fromPdata(String _source)
    {
        return ContextUtil.fromPdata(_source);
    }
}

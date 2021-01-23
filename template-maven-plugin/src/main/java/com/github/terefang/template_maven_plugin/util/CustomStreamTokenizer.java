package com.github.terefang.template_maven_plugin.util;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
 * Original Code from github.com/apache/harmony/.../StreamTokenizer.java
 *
 * changes Copyright (c) 2021. terefang@gmail.com
 *
 */

import java.io.IOException;
import java.io.Reader;

/**
 * Parses a stream into a set of defined tokens, one at a time. The different
 * types of tokens that can be found are numbers, identifiers, quoted strings,
 * and different comment styles. The class can be used for limited processing
 * of source code of programming languages like Java, although it is nowhere
 * near a full parser.
 */
public class CustomStreamTokenizer {
    /**
     * Contains a number if the current token is a number ({@code ttype} ==
     * {@code TOKEN_TYPE_NUMBER}).
     */
    public double numValue;

    /**
     * Contains a string if the current token is a word ({@code ttype} ==
     * {@code TOKEN_TYPE_WORD}).
     */
    public String stringValue;

    /**
     * Contains a cadinal if the current token is a number ({@code ttype} ==
     * {@code TOKEN_TYPE_CADINAL}).
     */
    public long cardinalValue;
    /**
     * The constant representing the end of the stream.
     */
    public static final int TOKEN_TYPE_EOF = -1;

    /**
     * The constant representing the end of the line.
     */
    public static final int TOKEN_TYPE_EOL = '\n';

    /**
     * The constant representing a number token.
     */
    public static final int TOKEN_TYPE_NUMBER = -2;

    /**
     * The constant representing a word token.
     */
    public static final int TOKEN_TYPE_WORD = -3;

    /**
     * The constant representing a cardinal/integer token.
     */
    public static final int TOKEN_TYPE_CARDINAL = -4;

    /**
     * Internal representation of unknown state.
     */
    private static final int TOKEN_TYPE_UNKNOWN = -5;

    /**
     * After calling {@code nextToken()}, {@code ttype} contains the type of
     * token that has been read. When a single character is read, its value
     * converted to an integer is stored in {@code ttype}. For a quoted string,
     * the value is the quoted character. Otherwise, its value is one of the
     * following:
     * <ul>
     * <li> {@code TOKEN_TYPE_WORD} - the token is a word.</li>
     * <li> {@code TOKEN_TYPE_NUMBER} - the token is a number.</li>
     * <li> {@code TOKEN_TYPE_CARDINAL} - the token is a cardinal.</li>
     * <li> {@code TOKEN_TYPE_EOL} - the end of line has been reached. Depends on
     * whether {@code eolIsSignificant} is {@code true}.</li>
     * <li> {@code TOKEN_TYPE_EOF} - the end of the stream has been reached.</li>
     * </ul>
     */
    public int ttype = TOKEN_TYPE_UNKNOWN;

    /**
     * Internal character meanings, 0 implies TOKEN_ORDINARY
     */
    private byte tokenTypes[] = new byte[256];

    private static final byte TOKEN_COMMENT = 1;

    private static final byte TOKEN_QUOTE = 2;

    private static final byte TOKEN_WHITE = 4;

    private static final byte TOKEN_WORD = 8;

    private static final byte TOKEN_DIGIT = 16;

    private int lineNumber = 1;

    private boolean forceLowercase;

    private boolean isEOLSignificant;

    private boolean slashStarComments;

    private boolean slashSlashComments;

    private boolean pushBackToken;

    private boolean lastCr;

    private Reader inReader;

    private int peekChar = -2;

    /**
     * Private constructor to initialize the default values according to the
     * specification.
     */
    private CustomStreamTokenizer() {
        /*
         * Initialize the default state per specification. All byte values 'A'
         * through 'Z', 'a' through 'z', and '\u00A0' through '\u00FF' are
         * considered to be alphabetic.
         */
        wordChars('A', 'Z');
        wordChars('a', 'z');
        wordChars(160, 255);
        /**
         * All byte values '\u0000' through '\u0020' are considered to be white
         * space.
         */
        whitespaceChars(0, 32);
        /**
         * '/' is a comment character. Single quote '\'' and double quote '"'
         * are string quote characters.
         */
        commentChar('/');
        quoteChar('"');
        quoteChar('\'');
        /**
         * Numbers are parsed.
         */
        parseNumbers();
        /**
         * Ends of lines are treated as white space, not as separate tokens.
         * C-style and C++-style comments are not recognized. These are the
         * defaults and are not needed in constructor.
         */
    }

    /**
     * Constructs a new {@code CustomStreamTokenizer} with {@code r} as source reader.
     * The tokenizer's initial state is as follows:
     * <ul>
     * <li>All byte values 'A' through 'Z', 'a' through 'z', and '&#92;u00A0'
     * through '&#92;u00FF' are considered to be alphabetic.</li>
     * <li>All byte values '&#92;u0000' through '&#92;u0020' are considered to
     * be white space. '/' is a comment character.</li>
     * <li>Single quote '\'' and double quote '"' are string quote characters.
     * </li>
     * <li>Numbers are parsed.</li>
     * <li>End of lines are considered to be white space rather than separate
     * tokens.</li>
     * <li>C-style and C++-style comments are not recognized.</LI>
     * </ul>
     *
     * @param r
     *            the source reader from which to parse tokens.
     */
    public CustomStreamTokenizer(Reader r) {
        this();
        if (r == null) {
            throw new NullPointerException();
        }
        inReader = r;
    }

    /**
     * Specifies that the character {@code ch} shall be treated as a comment
     * character.
     *
     * @param ch
     *            the character to be considered a comment character.
     */
    public void commentChar(int ch) {
        if (0 <= ch && ch < tokenTypes.length) {
            tokenTypes[ch] = TOKEN_COMMENT;
        }
    }

    /**
     * Specifies whether the end of a line is significant and should be returned
     * as {@code TOKEN_TYPE_EOF} in {@code ttype} by this tokenizer.
     *
     * @param flag
     *            {@code true} if EOL is significant, {@code false} otherwise.
     */
    public void eolIsSignificant(boolean flag) {
        isEOLSignificant = flag;
    }

    /**
     * Returns the current line number.
     *
     * @return this tokenizer's current line number.
     */
    public int lineno() {
        return lineNumber;
    }

    /**
     * Specifies whether word tokens should be converted to lower case when they
     * are stored in {@code sval}.
     *
     * @param flag
     *            {@code true} if {@code sval} should be converted to lower
     *            case, {@code false} otherwise.
     */
    public void lowerCaseMode(boolean flag) {
        forceLowercase = flag;
    }

    /* get tokens as specific value */

    public String tokenAsString() { return this.stringValue; }
    public long tokenAsCardinal() { return this.cardinalValue; }
    public double tokenAsNumber() { return this.numValue; }

    /**
     * Parses the next token from this tokenizer's source stream or reader. The
     * type of the token is stored in the {@code ttype} field, additional
     * information may be stored in the {@code nval} or {@code sval} fields.
     *
     * @return the value of {@code ttype}.
     * @throws IOException
     *             if an I/O error occurs while parsing the next token.
     */
    public int nextToken() throws IOException {
        if (pushBackToken) {
            pushBackToken = false;
            if (ttype != TOKEN_TYPE_UNKNOWN) {
                return ttype;
            }
        }
        stringValue = null; // Always reset sval to null
        int currentChar = peekChar == -2 ? read() : peekChar;

        if (lastCr && currentChar == '\n') {
            lastCr = false;
            currentChar = read();
        }
        if (currentChar == -1) {
            return (ttype = TOKEN_TYPE_EOF);
        }

        byte currentType = currentChar > 255 ? TOKEN_WORD
                : tokenTypes[currentChar];
        while ((currentType & TOKEN_WHITE) != 0) {
            /**
             * Skip over white space until we hit a new line or a real token
             */
            if (currentChar == '\r') {
                lineNumber++;
                if (isEOLSignificant) {
                    lastCr = true;
                    peekChar = -2;
                    return (ttype = TOKEN_TYPE_EOL);
                }
                if ((currentChar = read()) == '\n') {
                    currentChar = read();
                }
            } else if (currentChar == '\n') {
                lineNumber++;
                if (isEOLSignificant) {
                    peekChar = -2;
                    return (ttype = TOKEN_TYPE_EOL);
                }
                currentChar = read();
            } else {
                // Advance over this white space character and try again.
                currentChar = read();
            }
            if (currentChar == -1) {
                return (ttype = TOKEN_TYPE_EOF);
            }
            currentType = currentChar > 255 ? TOKEN_WORD
                    : tokenTypes[currentChar];
        }

        /**
         * Check for digits before checking for words since digits can be
         * contained within words.
         */
        if ((currentType & TOKEN_DIGIT) != 0) {
            StringBuilder digits = new StringBuilder(20);
            boolean haveDecimal = false, checkJustNegative = currentChar == '-';
            while (true) {
                if (currentChar == '.') {
                    haveDecimal = true;
                }
                digits.append((char) currentChar);
                currentChar = read();
                if ((currentChar < '0' || currentChar > '9')
                        && (haveDecimal || currentChar != '.')) {
                    break;
                }
            }
            peekChar = currentChar;
            if (checkJustNegative && digits.length() == 1) {
                // Didn't get any other digits other than '-'
                return (ttype = '-');
            }
            try {
                if(haveDecimal)
                {
                    numValue = Double.valueOf(digits.toString()).doubleValue();
                }
                else
                {
                    this.cardinalValue = Long.valueOf(digits.toString()).longValue();
                }
            } catch (NumberFormatException e) {
                // Unsure what to do, will write test.
                numValue = 0;
            }
            return (ttype = (haveDecimal ? TOKEN_TYPE_NUMBER : TOKEN_TYPE_CARDINAL));
        }
        // Check for words
        if ((currentType & TOKEN_WORD) != 0) {
            StringBuilder word = new StringBuilder(20);
            while (true) {
                word.append((char) currentChar);
                currentChar = read();
                if (currentChar == -1
                        || (currentChar < 256 && (tokenTypes[currentChar] & (TOKEN_WORD | TOKEN_DIGIT)) == 0)) {
                    break;
                }
            }
            peekChar = currentChar;
            stringValue = forceLowercase ? word.toString().toLowerCase() : word
                    .toString();
            return (ttype = TOKEN_TYPE_WORD);
        }
        // Check for quoted character
        if (currentType == TOKEN_QUOTE) {
            int matchQuote = currentChar;
            StringBuilder quoteString = new StringBuilder();
            int peekOne = read();
            while (peekOne >= 0 && peekOne != matchQuote && peekOne != '\r'
                    && peekOne != '\n') {
                boolean readPeek = true;
                if (peekOne == '\\') {
                    int c1 = read();
                    // Check for quoted octal IE: \377
                    if (c1 <= '7' && c1 >= '0') {
                        int digitValue = c1 - '0';
                        c1 = read();
                        if (c1 > '7' || c1 < '0') {
                            readPeek = false;
                        } else {
                            digitValue = digitValue * 8 + (c1 - '0');
                            c1 = read();
                            // limit the digit value to a byte
                            if (digitValue > 037 || c1 > '7' || c1 < '0') {
                                readPeek = false;
                            } else {
                                digitValue = digitValue * 8 + (c1 - '0');
                            }
                        }
                        if (!readPeek) {
                            // We've consumed one to many
                            quoteString.append((char) digitValue);
                            peekOne = c1;
                        } else {
                            peekOne = digitValue;
                        }
                    } else {
                        switch (c1) {
                            case 'a':
                                peekOne = 0x7;
                                break;
                            case 'b':
                                peekOne = 0x8;
                                break;
                            case 'f':
                                peekOne = 0xc;
                                break;
                            case 'n':
                                peekOne = 0xA;
                                break;
                            case 'r':
                                peekOne = 0xD;
                                break;
                            case 't':
                                peekOne = 0x9;
                                break;
                            case 'v':
                                peekOne = 0xB;
                                break;
                            default:
                                peekOne = c1;
                        }
                    }
                }
                if (readPeek) {
                    quoteString.append((char) peekOne);
                    peekOne = read();
                }
            }
            if (peekOne == matchQuote) {
                peekOne = read();
            }
            peekChar = peekOne;
            ttype = matchQuote;
            stringValue = quoteString.toString();
            return ttype;
        }
        // Do comments, both "//" and "/*stuff*/"
        if (currentChar == '/' && (slashSlashComments || slashStarComments)) {
            if ((currentChar = read()) == '*' && slashStarComments) {
                int peekOne = read();
                while (true) {
                    currentChar = peekOne;
                    peekOne = read();
                    if (currentChar == -1) {
                        peekChar = -1;
                        return (ttype = TOKEN_TYPE_EOF);
                    }
                    if (currentChar == '\r') {
                        if (peekOne == '\n') {
                            peekOne = read();
                        }
                        lineNumber++;
                    } else if (currentChar == '\n') {
                        lineNumber++;
                    } else if (currentChar == '*' && peekOne == '/') {
                        peekChar = read();
                        return nextToken();
                    }
                }
            } else if (currentChar == '/' && slashSlashComments) {
                // Skip to EOF or new line then return the next token
                while ((currentChar = read()) >= 0 && currentChar != '\r'
                        && currentChar != '\n') {
                    // Intentionally empty
                }
                peekChar = currentChar;
                return nextToken();
            } else if (currentType != TOKEN_COMMENT) {
                // Was just a slash by itself
                peekChar = currentChar;
                return (ttype = '/');
            }
        }
        // Check for comment character
        if (currentType == TOKEN_COMMENT) {
            // Skip to EOF or new line then return the next token
            while ((currentChar = read()) >= 0 && currentChar != '\r'
                    && currentChar != '\n') {
                // Intentionally empty
            }
            peekChar = currentChar;
            return nextToken();
        }

        peekChar = read();
        return (ttype = currentChar);
    }

    /**
     * Specifies that the character {@code ch} shall be treated as an ordinary
     * character by this tokenizer. That is, it has no special meaning as a
     * comment character, word component, white space, string delimiter or
     * number.
     *
     * @param ch
     *            the character to be considered an ordinary character.
     */
    public void ordinaryChar(int ch) {
        if (0 <= ch && ch < tokenTypes.length) {
            tokenTypes[ch] = 0;
        }
    }

    /**
     * Specifies that the characters in the range from {@code low} to {@code hi}
     * shall be treated as an ordinary character by this tokenizer. That is,
     * they have no special meaning as a comment character, word component,
     * white space, string delimiter or number.
     *
     * @param low
     *            the first character in the range of ordinary characters.
     * @param hi
     *            the last character in the range of ordinary characters.
     */
    public void ordinaryChars(int low, int hi) {
        if (low < 0) {
            low = 0;
        }
        if (hi > tokenTypes.length) {
            hi = tokenTypes.length - 1;
        }
        for (int i = low; i <= hi; i++) {
            tokenTypes[i] = 0;
        }
    }

    /**
     * Specifies that this tokenizer shall parse numbers.
     */
    public void parseNumbers() {
        for (int i = '0'; i <= '9'; i++) {
            tokenTypes[i] |= TOKEN_DIGIT;
        }
        tokenTypes['.'] |= TOKEN_DIGIT;
        tokenTypes['-'] |= TOKEN_DIGIT;
    }

    /**
     * Indicates that the current token should be pushed back and returned again
     * the next time {@code nextToken()} is called.
     */
    public void pushBack() {
        pushBackToken = true;
    }

    /**
     * Specifies that the character {@code ch} shall be treated as a quote
     * character.
     *
     * @param ch
     *            the character to be considered a quote character.
     */
    public void quoteChar(int ch) {
        if (0 <= ch && ch < tokenTypes.length) {
            tokenTypes[ch] = TOKEN_QUOTE;
        }
    }

    private int read() throws IOException {
        return inReader.read();
    }

    /**
     * Specifies that all characters shall be treated as ordinary characters.
     */
    public void resetSyntax() {
        for (int i = 0; i < 256; i++) {
            tokenTypes[i] = 0;
        }
    }

    /**
     * Specifies whether "slash-slash" (C++-style) comments shall be recognized.
     * This kind of comment ends at the end of the line.
     *
     * @param flag
     *            {@code true} if {@code //} should be recognized as the start
     *            of a comment, {@code false} otherwise.
     */
    public void slashSlashComments(boolean flag) {
        slashSlashComments = flag;
    }

    /**
     * Specifies whether "slash-star" (C-style) comments shall be recognized.
     * Slash-star comments cannot be nested and end when a star-slash
     * combination is found.
     *
     * @param flag
     *            {@code true} if {@code /*} should be recognized as the start
     *            of a comment, {@code false} otherwise.
     */
    public void slashStarComments(boolean flag) {
        slashStarComments = flag;
    }

    /**
     * Returns the state of this tokenizer in a readable format.
     *
     * @return the current state of this tokenizer.
     */
    @Override
    public String toString() {
        // Values determined through experimentation
        StringBuilder result = new StringBuilder();
        result.append("Token["); //$NON-NLS-1$
        switch (ttype) {
            case TOKEN_TYPE_EOF:
                result.append("EOF"); //$NON-NLS-1$
                break;
            case TOKEN_TYPE_EOL:
                result.append("EOL"); //$NON-NLS-1$
                break;
            case TOKEN_TYPE_NUMBER:
                result.append("n="); //$NON-NLS-1$
                result.append(numValue);
                break;
            case TOKEN_TYPE_WORD:
                result.append(stringValue);
                break;
            default:
                if (ttype == TOKEN_TYPE_UNKNOWN || tokenTypes[ttype] == TOKEN_QUOTE) {
                    result.append(stringValue);
                } else {
                    result.append('\'');
                    result.append((char) ttype);
                    result.append('\'');
                }
        }
        result.append("], line "); //$NON-NLS-1$
        result.append(lineNumber);
        return result.toString();
    }

    /**
     * Specifies that the characters in the range from {@code low} to {@code hi}
     * shall be treated as whitespace characters by this tokenizer.
     *
     * @param low
     *            the first character in the range of whitespace characters.
     * @param hi
     *            the last character in the range of whitespace characters.
     */
    public void whitespaceChars(int low, int hi) {
        if (low < 0) {
            low = 0;
        }
        if (hi > tokenTypes.length) {
            hi = tokenTypes.length - 1;
        }
        for (int i = low; i <= hi; i++) {
            tokenTypes[i] = TOKEN_WHITE;
        }
    }

    /**
     * Specifies that the characters in the range from {@code low} to {@code hi}
     * shall be treated as word characters by this tokenizer. A word consists of
     * a word character followed by zero or more word or number characters.
     *
     * @param low
     *            the first character in the range of word characters.
     * @param hi
     *            the last character in the range of word characters.
     */
    public void wordChars(int low, int hi) {
        if (low < 0) {
            low = 0;
        }
        if (hi > tokenTypes.length) {
            hi = tokenTypes.length - 1;
        }
        for (int i = low; i <= hi; i++) {
            tokenTypes[i] |= TOKEN_WORD;
        }
    }

    public void wordChar(int low) {
        if (low < 0) {
            low = 0;
        }
        if (low > tokenTypes.length) {
            low = tokenTypes.length - 1;
        }
        tokenTypes[low] |= TOKEN_WORD;
    }

    public void wordChar(int... _chars) {
        for(int _c : _chars)
        {
            this.wordChar(_c);
        }
    }

}
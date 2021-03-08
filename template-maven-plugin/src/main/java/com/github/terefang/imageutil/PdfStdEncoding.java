package com.github.terefang.imageutil;

import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.font.encoding.DictionaryEncoding;
import org.apache.pdfbox.pdmodel.font.encoding.Encoding;
import org.apache.pdfbox.pdmodel.font.encoding.MacRomanEncoding;

public class PdfStdEncoding extends DictionaryEncoding
{
    static COSArray DIFS = new COSArray();
    static {
        DIFS.add(COSInteger.ZERO);
        DIFS.add(COSName.getPDFName("uni0000"));
        DIFS.add(COSName.getPDFName("controlSTX"));
        DIFS.add(COSName.getPDFName("controlSOT"));
        DIFS.add(COSName.getPDFName("controlETX"));
        DIFS.add(COSName.getPDFName("controlEOT"));
        DIFS.add(COSName.getPDFName("controlENQ"));
        DIFS.add(COSName.getPDFName("controlACK"));
        DIFS.add(COSName.getPDFName("controlBEL"));
        DIFS.add(COSName.getPDFName("controlBS"));
        DIFS.add(COSName.getPDFName("controlHT"));
        DIFS.add(COSName.getPDFName("controlLF"));
        DIFS.add(COSName.getPDFName("controlVT"));
        DIFS.add(COSName.getPDFName("controlFF"));
        DIFS.add(COSName.getPDFName("controlCR"));
        DIFS.add(COSName.getPDFName("controlSO"));
        DIFS.add(COSName.getPDFName("controlSI"));
        DIFS.add(COSName.getPDFName("controlDLE"));
        DIFS.add(COSName.getPDFName("controlDC1"));
        DIFS.add(COSName.getPDFName("controlDC2"));
        DIFS.add(COSName.getPDFName("controlDC3"));
        DIFS.add(COSName.getPDFName("controlDC4"));
        DIFS.add(COSName.getPDFName("controlNAK"));
        DIFS.add(COSName.getPDFName("controlSYN"));
        DIFS.add(COSName.getPDFName("controlETB"));
        DIFS.add(COSName.getPDFName("breve"));
        DIFS.add(COSName.getPDFName("caron"));
        DIFS.add(COSName.getPDFName("circumflex"));
        DIFS.add(COSName.getPDFName("dotaccent"));
        DIFS.add(COSName.getPDFName("hungarumlaut"));
        DIFS.add(COSName.getPDFName("ogonek"));
        DIFS.add(COSName.getPDFName("ring"));
        DIFS.add(COSName.getPDFName("tilde"));
        DIFS.add(COSName.getPDFName("space"));
        DIFS.add(COSName.getPDFName("exclam"));
        DIFS.add(COSName.getPDFName("quotedbl"));
        DIFS.add(COSName.getPDFName("numbersign"));
        DIFS.add(COSName.getPDFName("dollar"));
        DIFS.add(COSName.getPDFName("percent"));
        DIFS.add(COSName.getPDFName("ampersand"));
        DIFS.add(COSName.getPDFName("quotesingle"));
        DIFS.add(COSName.getPDFName("parenleft"));
        DIFS.add(COSName.getPDFName("parenright"));
        DIFS.add(COSName.getPDFName("asterisk"));
        DIFS.add(COSName.getPDFName("plus"));
        DIFS.add(COSName.getPDFName("comma"));
        DIFS.add(COSName.getPDFName("hyphen"));
        DIFS.add(COSName.getPDFName("period"));
        DIFS.add(COSName.getPDFName("slash"));
        DIFS.add(COSName.getPDFName("zero"));
        DIFS.add(COSName.getPDFName("one"));
        DIFS.add(COSName.getPDFName("two"));
        DIFS.add(COSName.getPDFName("three"));
        DIFS.add(COSName.getPDFName("four"));
        DIFS.add(COSName.getPDFName("five"));
        DIFS.add(COSName.getPDFName("six"));
        DIFS.add(COSName.getPDFName("seven"));
        DIFS.add(COSName.getPDFName("eight"));
        DIFS.add(COSName.getPDFName("nine"));
        DIFS.add(COSName.getPDFName("colon"));
        DIFS.add(COSName.getPDFName("semicolon"));
        DIFS.add(COSName.getPDFName("less"));
        DIFS.add(COSName.getPDFName("equal"));
        DIFS.add(COSName.getPDFName("greater"));
        DIFS.add(COSName.getPDFName("question"));
        DIFS.add(COSName.getPDFName("at"));
        DIFS.add(COSName.getPDFName("A"));
        DIFS.add(COSName.getPDFName("B"));
        DIFS.add(COSName.getPDFName("C"));
        DIFS.add(COSName.getPDFName("D"));
        DIFS.add(COSName.getPDFName("E"));
        DIFS.add(COSName.getPDFName("F"));
        DIFS.add(COSName.getPDFName("G"));
        DIFS.add(COSName.getPDFName("H"));
        DIFS.add(COSName.getPDFName("I"));
        DIFS.add(COSName.getPDFName("J"));
        DIFS.add(COSName.getPDFName("K"));
        DIFS.add(COSName.getPDFName("L"));
        DIFS.add(COSName.getPDFName("M"));
        DIFS.add(COSName.getPDFName("N"));
        DIFS.add(COSName.getPDFName("O"));
        DIFS.add(COSName.getPDFName("P"));
        DIFS.add(COSName.getPDFName("Q"));
        DIFS.add(COSName.getPDFName("R"));
        DIFS.add(COSName.getPDFName("S"));
        DIFS.add(COSName.getPDFName("T"));
        DIFS.add(COSName.getPDFName("U"));
        DIFS.add(COSName.getPDFName("V"));
        DIFS.add(COSName.getPDFName("W"));
        DIFS.add(COSName.getPDFName("X"));
        DIFS.add(COSName.getPDFName("Y"));
        DIFS.add(COSName.getPDFName("Z"));
        DIFS.add(COSName.getPDFName("bracketleft"));
        DIFS.add(COSName.getPDFName("backslash"));
        DIFS.add(COSName.getPDFName("bracketright"));
        DIFS.add(COSName.getPDFName("asciicircum"));
        DIFS.add(COSName.getPDFName("underscore"));
        DIFS.add(COSName.getPDFName("grave"));
        DIFS.add(COSName.getPDFName("a"));
        DIFS.add(COSName.getPDFName("b"));
        DIFS.add(COSName.getPDFName("c"));
        DIFS.add(COSName.getPDFName("d"));
        DIFS.add(COSName.getPDFName("e"));
        DIFS.add(COSName.getPDFName("f"));
        DIFS.add(COSName.getPDFName("g"));
        DIFS.add(COSName.getPDFName("h"));
        DIFS.add(COSName.getPDFName("i"));
        DIFS.add(COSName.getPDFName("j"));
        DIFS.add(COSName.getPDFName("k"));
        DIFS.add(COSName.getPDFName("l"));
        DIFS.add(COSName.getPDFName("m"));
        DIFS.add(COSName.getPDFName("n"));
        DIFS.add(COSName.getPDFName("o"));
        DIFS.add(COSName.getPDFName("p"));
        DIFS.add(COSName.getPDFName("q"));
        DIFS.add(COSName.getPDFName("r"));
        DIFS.add(COSName.getPDFName("s"));
        DIFS.add(COSName.getPDFName("t"));
        DIFS.add(COSName.getPDFName("u"));
        DIFS.add(COSName.getPDFName("v"));
        DIFS.add(COSName.getPDFName("w"));
        DIFS.add(COSName.getPDFName("x"));
        DIFS.add(COSName.getPDFName("y"));
        DIFS.add(COSName.getPDFName("z"));
        DIFS.add(COSName.getPDFName("braceleft"));
        DIFS.add(COSName.getPDFName("bar"));
        DIFS.add(COSName.getPDFName("braceright"));
        DIFS.add(COSName.getPDFName("asciitilde"));
        DIFS.add(COSName.getPDFName("uniFFFD"));
        DIFS.add(COSName.getPDFName("bullet"));
        DIFS.add(COSName.getPDFName("dagger"));
        DIFS.add(COSName.getPDFName("daggerdbl"));
        DIFS.add(COSName.getPDFName("ellipsis"));
        DIFS.add(COSName.getPDFName("emdash"));
        DIFS.add(COSName.getPDFName("endash"));
        DIFS.add(COSName.getPDFName("florin"));
        DIFS.add(COSName.getPDFName("fraction"));
        DIFS.add(COSName.getPDFName("guilsinglleft"));
        DIFS.add(COSName.getPDFName("guilsinglright"));
        DIFS.add(COSName.getPDFName("minus"));
        DIFS.add(COSName.getPDFName("perthousand"));
        DIFS.add(COSName.getPDFName("quotedblbase"));
        DIFS.add(COSName.getPDFName("quotedblleft"));
        DIFS.add(COSName.getPDFName("quotedblright"));
        DIFS.add(COSName.getPDFName("quoteleft"));
        DIFS.add(COSName.getPDFName("quoteright"));
        DIFS.add(COSName.getPDFName("quotesinglbase"));
        DIFS.add(COSName.getPDFName("trademark"));
        DIFS.add(COSName.getPDFName("uniFB01"));
        DIFS.add(COSName.getPDFName("uniFB02"));
        DIFS.add(COSName.getPDFName("Lslash"));
        DIFS.add(COSName.getPDFName("OE"));
        DIFS.add(COSName.getPDFName("Scaron"));
        DIFS.add(COSName.getPDFName("Ydieresis"));
        DIFS.add(COSName.getPDFName("Zcaron"));
        DIFS.add(COSName.getPDFName("dotlessi"));
        DIFS.add(COSName.getPDFName("lslash"));
        DIFS.add(COSName.getPDFName("oe"));
        DIFS.add(COSName.getPDFName("scaron"));
        DIFS.add(COSName.getPDFName("zcaron"));
        DIFS.add(COSName.getPDFName("uniFFFD"));
        DIFS.add(COSName.getPDFName("Euro"));
        DIFS.add(COSName.getPDFName("exclamdown"));
        DIFS.add(COSName.getPDFName("cent"));
        DIFS.add(COSName.getPDFName("sterling"));
        DIFS.add(COSName.getPDFName("currency"));
        DIFS.add(COSName.getPDFName("yen"));
        DIFS.add(COSName.getPDFName("brokenbar"));
        DIFS.add(COSName.getPDFName("section"));
        DIFS.add(COSName.getPDFName("dieresis"));
        DIFS.add(COSName.getPDFName("copyright"));
        DIFS.add(COSName.getPDFName("ordfeminine"));
        DIFS.add(COSName.getPDFName("guillemotleft"));
        DIFS.add(COSName.getPDFName("logicalnot"));
        DIFS.add(COSName.getPDFName("uni00AD"));
        DIFS.add(COSName.getPDFName("registered"));
        DIFS.add(COSName.getPDFName("macron"));
        DIFS.add(COSName.getPDFName("degree"));
        DIFS.add(COSName.getPDFName("plusminus"));
        DIFS.add(COSName.getPDFName("uni00B2"));
        DIFS.add(COSName.getPDFName("uni00B3"));
        DIFS.add(COSName.getPDFName("acute"));
        DIFS.add(COSName.getPDFName("mu"));
        DIFS.add(COSName.getPDFName("paragraph"));
        DIFS.add(COSName.getPDFName("periodcentered"));
        DIFS.add(COSName.getPDFName("cedilla"));
        DIFS.add(COSName.getPDFName("uni00B9"));
        DIFS.add(COSName.getPDFName("ordmasculine"));
        DIFS.add(COSName.getPDFName("guillemotright"));
        DIFS.add(COSName.getPDFName("onequarter"));
        DIFS.add(COSName.getPDFName("onehalf"));
        DIFS.add(COSName.getPDFName("threequarters"));
        DIFS.add(COSName.getPDFName("questiondown"));
        DIFS.add(COSName.getPDFName("Agrave"));
        DIFS.add(COSName.getPDFName("Aacute"));
        DIFS.add(COSName.getPDFName("Acircumflex"));
        DIFS.add(COSName.getPDFName("Atilde"));
        DIFS.add(COSName.getPDFName("Adieresis"));
        DIFS.add(COSName.getPDFName("Aring"));
        DIFS.add(COSName.getPDFName("AE"));
        DIFS.add(COSName.getPDFName("Ccedilla"));
        DIFS.add(COSName.getPDFName("Egrave"));
        DIFS.add(COSName.getPDFName("Eacute"));
        DIFS.add(COSName.getPDFName("Ecircumflex"));
        DIFS.add(COSName.getPDFName("Edieresis"));
        DIFS.add(COSName.getPDFName("Igrave"));
        DIFS.add(COSName.getPDFName("Iacute"));
        DIFS.add(COSName.getPDFName("Icircumflex"));
        DIFS.add(COSName.getPDFName("Idieresis"));
        DIFS.add(COSName.getPDFName("Eth"));
        DIFS.add(COSName.getPDFName("Ntilde"));
        DIFS.add(COSName.getPDFName("Ograve"));
        DIFS.add(COSName.getPDFName("Oacute"));
        DIFS.add(COSName.getPDFName("Ocircumflex"));
        DIFS.add(COSName.getPDFName("Otilde"));
        DIFS.add(COSName.getPDFName("Odieresis"));
        DIFS.add(COSName.getPDFName("multiply"));
        DIFS.add(COSName.getPDFName("Oslash"));
        DIFS.add(COSName.getPDFName("Ugrave"));
        DIFS.add(COSName.getPDFName("Uacute"));
        DIFS.add(COSName.getPDFName("Ucircumflex"));
        DIFS.add(COSName.getPDFName("Udieresis"));
        DIFS.add(COSName.getPDFName("Yacute"));
        DIFS.add(COSName.getPDFName("Thorn"));
        DIFS.add(COSName.getPDFName("germandbls"));
        DIFS.add(COSName.getPDFName("agrave"));
        DIFS.add(COSName.getPDFName("aacute"));
        DIFS.add(COSName.getPDFName("acircumflex"));
        DIFS.add(COSName.getPDFName("atilde"));
        DIFS.add(COSName.getPDFName("adieresis"));
        DIFS.add(COSName.getPDFName("aring"));
        DIFS.add(COSName.getPDFName("ae"));
        DIFS.add(COSName.getPDFName("ccedilla"));
        DIFS.add(COSName.getPDFName("egrave"));
        DIFS.add(COSName.getPDFName("eacute"));
        DIFS.add(COSName.getPDFName("ecircumflex"));
        DIFS.add(COSName.getPDFName("edieresis"));
        DIFS.add(COSName.getPDFName("igrave"));
        DIFS.add(COSName.getPDFName("iacute"));
        DIFS.add(COSName.getPDFName("icircumflex"));
        DIFS.add(COSName.getPDFName("idieresis"));
        DIFS.add(COSName.getPDFName("eth"));
        DIFS.add(COSName.getPDFName("ntilde"));
        DIFS.add(COSName.getPDFName("ograve"));
        DIFS.add(COSName.getPDFName("oacute"));
        DIFS.add(COSName.getPDFName("ocircumflex"));
        DIFS.add(COSName.getPDFName("otilde"));
        DIFS.add(COSName.getPDFName("odieresis"));
        DIFS.add(COSName.getPDFName("divide"));
        DIFS.add(COSName.getPDFName("oslash"));
        DIFS.add(COSName.getPDFName("ugrave"));
        DIFS.add(COSName.getPDFName("uacute"));
        DIFS.add(COSName.getPDFName("ucircumflex"));
        DIFS.add(COSName.getPDFName("udieresis"));
        DIFS.add(COSName.getPDFName("yacute"));
        DIFS.add(COSName.getPDFName("thorn"));
        DIFS.add(COSName.getPDFName("ydieresis"));
    }

    public PdfStdEncoding()
    {
        super(COSName.WIN_ANSI_ENCODING, DIFS);
    }
}

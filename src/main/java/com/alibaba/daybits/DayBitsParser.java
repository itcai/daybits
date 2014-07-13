package com.alibaba.daybits;

import java.util.Arrays;

import com.alibaba.daybits.DayBits.Quarter;
import com.alibaba.daybits.DayBits.Year;

public class DayBitsParser {

    public static final char[] CA      = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    public static final int[]  IA      = new int[256];
    static {
        Arrays.fill(IA, -1);
        for (int i = 0, iS = CA.length; i < iS; i++)
            IA[CA[i]] = i;
        IA['='] = 0;
    }

    private String             chars;
    private int                pos     = -1;
    private char               ch;

    private boolean            eof     = false;

    private DayBits            daybits = new DayBits();

    public DayBitsParser(String text){
        this.chars = text;
        next();
    }

    private void next() {
        ch = chars.charAt(++pos);
    }

    String read(char sep) {
        int index = chars.indexOf(sep, pos);
        if (index != -1) {
            String val = chars.substring(pos, index);
            pos = index + 1;
            ch = chars.charAt(pos);
            return val;
        } else {

            eof = true;
        }
        return null;
    }

    public boolean isEOF() {
        return eof || pos >= chars.length();
    }

    public DayBits parse() {
        while (!isEOF()) {
            Year year = parseYear();
            daybits.add(year);
        }
        return daybits;
    }
    
    public static DayBits parse(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        DayBitsParser parser = new DayBitsParser(text);
        return parser.parse();
    }

    Year parseYear() {
        if (ch == '#') {
            daybits.beforeYears = daybits.years;
            daybits.years = null;
            next();
        }

        if (ch == ';') {
            next();
            return null;
        }

        Year year = new Year();

        for (int i = 0; i < 4; ++i) {
            if (ch == ';' || ch == '#' || isEOF()) {
                break;
            }

            Quarter quarter = parseQuarter();
            if (i == 0) {
                year.spring = quarter;
            } else if (i == 1) {
                year.summer = quarter;
            } else if (i == 2) {
                year.autumn = quarter;
            } else if (i == 3) {
                year.winter = quarter;
            }
        }

        if (!isEOF()) {
            if (ch == '#') {
                // skip
            } else {
                if (ch != ';') {
                    throw new IllegalStateException();
                }
                next();
            }
        }

        return year;
    }

    Quarter parseQuarter() {
        if (ch == ',') {
            next();
            return null;
        }

        int sepIndex = pos;
        for (; sepIndex < chars.length(); ++sepIndex) {
            char ch = chars.charAt(sepIndex);
            if (ch == ',' || ch == ';' || ch == '#') {
                break;
            }
        }
        int charsLen = sepIndex - pos;

        byte[] bytes = this.decodeFast(pos, charsLen);

        pos = sepIndex;
        if (pos < chars.length()) {
            ch = chars.charAt(pos);
            if (ch == ',') {
                next();
            }
        } else {
            eof = true;
        }

        return new Quarter(bytes);
    }

    public final byte[] decodeFast(int offset, int charsLen) {
        // Check special case
        if (charsLen == 0) {
            return new byte[0];
        }

        int sIx = offset;
        int eIx = offset + charsLen - 1; // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IA[chars.charAt(sIx)] < 0) {
            sIx++;
        }

        // Trim illegal chars from end
        while (eIx > 0 && IA[chars.charAt(eIx)] < 0) {
            eIx--;
        }

        // get the padding count (=) (0, 1 or 2)
        int pad = chars.charAt(eIx) == '=' ? (chars.charAt(eIx - 1) == '=' ? 2 : 1) : 0; // Count '=' at end.
        int cCnt = eIx - sIx + 1; // Content count including possible separators
        int sepCnt = charsLen > 76 ? (chars.charAt(76) == '\r' ? cCnt / 78 : 0) << 1 : 0;

        int len = ((cCnt - sepCnt) * 6 >> 3) - pad; // The number of decoded bytes
        byte[] bytes = new byte[len]; // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen;) {
            // Assemble three bytes into an int from four "valid" characters.
            int i = IA[chars.charAt(sIx++)] << 18 | IA[chars.charAt(sIx++)] << 12 | IA[chars.charAt(sIx++)] << 6
                    | IA[chars.charAt(sIx++)];

            // Add the bytes
            bytes[d++] = (byte) (i >> 16);
            bytes[d++] = (byte) (i >> 8);
            bytes[d++] = (byte) i;

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            int i = 0;
            for (int j = 0; sIx <= eIx - pad; j++)
                i |= IA[chars.charAt(sIx++)] << (18 - j * 6);

            for (int r = 16; d < len; r -= 8)
                bytes[d++] = (byte) (i >> r);
        }

        return bytes;
    }
}

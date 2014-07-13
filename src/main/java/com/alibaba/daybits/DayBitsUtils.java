package com.alibaba.daybits;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.alibaba.daybits.DayBits.Quarter;
import com.alibaba.daybits.DayBits.Year;

public class DayBitsUtils {

    /**
     * This array is a lookup table that translates 6-bit positive integer index values into their "Base64 Alphabet"
     * equivalents as specified in Table 1 of RFC 2045.
     */
    public static final char intToBase64[]   = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };

    private static int[]     daySecondsCache = new int[10 * 12 * 31];           // 10 years
    private static int[]     dayCache        = new int[10 * 12 * 31];           // 10 years
    static {
        Calendar calendar_20100101 = Calendar.getInstance();
        calendar_20100101.set(Calendar.YEAR, 2010);
        calendar_20100101.set(Calendar.MONTH, 0);
        calendar_20100101.set(Calendar.DAY_OF_MONTH, 1);
        calendar_20100101.set(Calendar.HOUR_OF_DAY, 0);
        calendar_20100101.set(Calendar.MINUTE, 0);
        calendar_20100101.set(Calendar.SECOND, 0);
        calendar_20100101.set(Calendar.MILLISECOND, 0);

        int seconds_20100101 = (int) (calendar_20100101.getTimeInMillis() / (1000L));

        for (long i = 0; i < 366 * 10; ++i) {
            long millis = ((long) (seconds_20100101 * 1000L) + i * 24L * 3600L * 1000L);
            int seconds = (int) (millis / 1000L);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            if (year >= 2020) {
                break;
            }

            int valueIndex = (year - 2010) * (12 * 31) + month * 31 + (dayOfMonth - 1);
            daySecondsCache[valueIndex] = seconds;

            long quarterFirstDaySeconds = seconds(year, firstMonthOfQuarter(month + 1), 1);
            int dayOfQuarter = (int) ((seconds - quarterFirstDaySeconds) / (3600L * 24L));
            int quarterIndex = quarterIndex(month + 1);
            int dayCacheIndex = (year - 2010) * 31 * 12 + quarterIndex * 31 * 3 + dayOfQuarter;
            int dateValue = (year * 10000) + (month + 1) * 100 + dayOfMonth;
            dayCache[dayCacheIndex] = dateValue;
        }
    }

    static SimpleDateFormat  format          = new SimpleDateFormat("yyyyMMdd");

    public static int quarterIndex(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalStateException("illegal month : " + month);
        }

        if (month <= 3) {
            return 0;
        }

        if (month <= 6) {
            return 1;
        }
        if (month <= 9) {
            return 2;
        }
        return 3;
    }

    public static int firstMonthOfQuarter(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalStateException("illegal month : " + month);
        }

        if (month <= 3) {
            return 1;
        }

        if (month <= 6) {
            return 4;
        }
        if (month <= 9) {
            return 7;
        }
        return 10;
    }

    public static int getDateValue(int year, int quarterIndex, int dayOfQuarter) {
        if (year >= 2010 && year < 2020) {
            int dayCacheIndex = (year - 2010) * 31 * 12 + quarterIndex * 31 * 3 + dayOfQuarter;
            return dayCache[dayCacheIndex];
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, quarterIndex * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.DAY_OF_YEAR, dayOfQuarter);

        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return year * 10000 + month * 100 + dayOfMonth;
    }

    public static long seconds(int year, int month, int dayOfMonth) {
        if (year >= 2010 && year < 2020) {
            int valueIndex = (year - 2010) * (12 * 31) + (month - 1) * 31 + (dayOfMonth - 1);
            return daySecondsCache[valueIndex];
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() / 1000L;
    }

    public static long millis(int year, int month, int dayOfMonth) {
        if (year >= 2010 && year < 2020) {
            int valueIndex = (year - 2010) * (12 * 31) + (month - 1) * 31 + (dayOfMonth - 1);
            int seconds = daySecondsCache[valueIndex];
            return ((long) seconds) * 1000L;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static int dayOfQuarter(int year, int month, int dayOfMonth) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("illegal month : " + month);
        }

        int quarterFirstMonth;
        if (month < 4) {
            quarterFirstMonth = 1;
        } else if (month < 7) {
            quarterFirstMonth = 4;
        } else if (month < 10) {
            quarterFirstMonth = 7;
        } else {
            quarterFirstMonth = 10;
        }

        long quarterFirstDaySeconds = DayBitsUtils.seconds(year, quarterFirstMonth, 1);
        long daySeconds = DayBitsUtils.seconds(year, month, dayOfMonth);

        if (year == 1986 && month >= 4 && month <= 6) {
            if (month > 5 || (month == 5 && dayOfMonth >= 5)) {
                daySeconds += 3600L;
            }
        } else if (year == 1987 && month >= 4 && month <= 6) {
            if (month > 4 || (month == 4 && dayOfMonth >= 12)) {
                daySeconds += 3600L;
            }
        } else if (year == 1987 && month >= 4 && month <= 6) {
            if (month > 4 || (month == 4 && dayOfMonth >= 11)) {
                daySeconds += 3600L;
            }
        } else if (year == 1988 && month >= 4 && month <= 6) {
            if (month > 4 || (month == 4 && dayOfMonth >= 10)) {
                daySeconds += 3600L;
            }
        } else if (year == 1989 && month >= 4 && month <= 6) {
            if (month > 4 || (month == 4 && dayOfMonth >= 16)) {
                daySeconds += 3600L;
            }
        } else if (year == 1990 && month >= 4 && month <= 6) {
            if (month > 4 || (month == 4 && dayOfMonth >= 15)) {
                daySeconds += 3600L;
            }
        } else if (year == 1991 && month >= 4 && month <= 6) {
            if (month > 4 || (month == 4 && dayOfMonth >= 14)) {
                daySeconds += 3600L;
            }
        }

        int dayOfQuarter = (int) ((daySeconds - quarterFirstDaySeconds) / (24L * 3600L));
        return dayOfQuarter;
    }

    public static byte[] compact(byte[] bytes) {
        int i = bytes.length - 1;
        for (; i >= 0; --i) {
            if (bytes[i] != 0) {
                break;
            }
        }

        if (i != bytes.length - 1) {
            bytes = Arrays.copyOf(bytes, i + 1);
        }

        return bytes;
    }

    public static void check(int dateValue) {
        if (dateValue < 19700101) {
            throw new IllegalArgumentException("illegal arg : " + dateValue);
        }

        if (dateValue >= 21991231) {
            throw new IllegalArgumentException("illegal arg : " + dateValue);
        }
    }

    public static List<Year> and(List<Year> years_a, List<Year> years_b) {
        if (years_a == null || years_b == null) {
            return null;
        }

        if (years_a.size() <= years_b.size()) {
            for (int i = 0; i < years_a.size(); ++i) {
                years_a.set(i, and(years_a.get(i), years_b.get(i)));
            }
            return compact_years(years_a);
        } else {
            for (int i = 0; i < years_b.size(); ++i) {
                years_b.set(i, and(years_a.get(i), years_b.get(i)));
            }
            return compact_years(years_b);
        }
    }

    public static DayBits or(DayBits a, DayBits b) {
        if (a == null && b == null) {
            return null;
        }
        
        if (a == null) {
            return b;
        }
        
        if (b == null) {
            return a;
        }
        
        a.merge(b);
        return a;
    }

    public static Year and(Year a, Year b) {
        if (a == null || b == null) {
            return null;
        }
        a.and(b);
        return a;
    }

    public static Quarter and(Quarter a, Quarter b) {
        if (a == null || b == null) {
            return null;
        }
        a.and(b);
        return a;
    }

    public static byte[] and(byte[] bytes_a, byte[] bytes_b) {
        byte[] x, y;
        if (bytes_a.length > bytes_b.length) {
            x = bytes_a;
            y = bytes_b;
        } else {
            x = bytes_b;
            y = bytes_a;
        }

        for (int i = 0; i < y.length; ++i) {
            y[i] &= x[i];
        }

        return compact(y);
    }

    public static List<Year> compact_years(List<Year> years) {
        int i = years.size() - 1;
        for (; i >= 0; --i) {
            if (years.get(i) != null && !years.get(i).isEmpty()) {
                break;
            }
            years.remove(i);
        }

        return years;
    }
    
    public static String toString(DayBits daybits) {
        if (daybits == null) {
            return null;
        }
        
        String text = daybits.toString();
        
        if (text != null && text.isEmpty()) {
            return null;
        }
        
        return text;
    }

    public static DayBits parse(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        DayBitsParser parser = new DayBitsParser(text);
        return parser.parse();
    }
    
    public static class DayBitsParser {

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

}

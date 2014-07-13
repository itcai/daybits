package com.alibaba.daybits;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

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
}

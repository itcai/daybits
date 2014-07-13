package com.alibaba.daybits;

import static com.alibaba.daybits.DayBitsUtils.end;
import static com.alibaba.daybits.DayBitsUtils.start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayBits {

    public final static int START = 19700101;
    public final static int END   = 23991231;

    List<Year>              beforeYears;
    List<Year>              years;

    public DayBits(){
    }

    void add(Year year) {
        if (years == null) {
            years = new ArrayList<Year>(4);
        }

        years.add(year);
    }

    public void and(DayBits o) {
        this.beforeYears = DayBitsUtils.and(this.beforeYears, o.beforeYears);
        this.years = DayBitsUtils.and(this.years, o.years);
    }

    public int merge(DayBits o) {
        int changed = 0;

        if (this.beforeYears == null) {
            if (o.beforeYears != null) {
                this.beforeYears = o.beforeYears;
                for (Year year : o.beforeYears) {
                    if (year == null) {
                        continue;
                    }
                    changed += year.count();
                }
            }
        } else {
            if (o.beforeYears != null) {
                for (int i = 0; i < o.beforeYears.size(); ++i) {
                    Year otherYear = o.beforeYears.get(i);
                    if (i < this.beforeYears.size()) {
                        if (otherYear == null) {
                            continue;
                        }

                        Year thisYear = this.beforeYears.get(i);
                        if (thisYear == null) {
                            this.beforeYears.set(i, otherYear);
                            changed += otherYear.count();
                        } else {
                            changed += thisYear.merge(otherYear);
                        }
                    } else {
                        this.beforeYears.add(otherYear);
                        changed += otherYear.count();
                    }
                }
            }
        }

        if (this.years == null) {
            if (o.years != null) {
                this.years = o.years;
                for (Year year : o.years) {
                    if (year == null) {
                        continue;
                    }
                    changed += year.count();
                }
            }
        } else {
            if (o.years != null) {
                for (int i = 0; i < o.years.size(); ++i) {
                    Year otherYear = o.years.get(i);
                    if (i < this.years.size()) {
                        if (otherYear == null) {
                            continue;
                        }

                        Year thisYear = this.years.get(i);
                        if (thisYear == null) {
                            this.years.set(i, otherYear);
                            changed += otherYear.count();
                        } else {
                            changed += thisYear.merge(otherYear);
                        }
                    } else {
                        this.years.add(otherYear);
                        changed += otherYear.count();
                    }
                }
            }
        }

        return changed;
    }
    
    public boolean exists(String start, String end) {
        return first(start(start), end(end)) != null;
    }

    public boolean exists(Long start, Long end) {
        return first(start(start), end(end)) != null;
    }

    public Long first() {
        return first(START, END);
    }

    public Long first(String start, String end) {
        return first(start(start), end(end));
    }

    public Long first(Long start, Long end) {
        return first(start(start), end(end));
    }

    public Long first(int start, int end) {
        int first = -1;
        if (beforeYears != null) {
            for (int i = beforeYears.size() - 1; i >= 0; --i) {
                Year year = beforeYears.get(i);
                if (year == null) {
                    continue;
                }
                first = year.first(2012 - i, start, end);
                if (first != -1) {
                    return (long) first;
                }
            }
        }

        if (years != null) {
            for (int i = 0; i < years.size(); ++i) {
                Year year = years.get(i);
                if (year == null) {
                    continue;
                }

                first = year.first(2013 + i, start, end);
                if (first != -1) {
                    return (long) first;
                }
            }
        }

        if (first == -1) {
            return null;
        }

        return (long) first;
    }

    public Long last() {
        return last(START, END);
    }

    public Long last(String start, String end) {
        return last(start(start), end(end));
    }

    public Long last(Long start, Long end) {
        return last(start(start), end(end));
    }

    public Long last(int start, int end) {
        DayBitsUtils.check(start);
        DayBitsUtils.check(end);

        int last = -1;
        if (years != null) {
            for (int i = years.size() - 1; i >= 0; --i) {
                Year year = years.get(i);
                if (year == null) {
                    continue;
                }
                last = year.last(2013 + i, start, end);
                if (last != -1) {
                    return (long) last;
                }
            }
        }

        if (beforeYears != null) {
            for (int i = 0; i < beforeYears.size(); ++i) {
                Year year = beforeYears.get(i);
                if (year == null) {
                    continue;
                }

                last = year.last(2012 - i, start, end);
                if (last != -1) {
                    return (long) last;
                }
            }
        }

        if (last == -1) {
            return null;
        }

        return (long) last;
    }

    public Boolean get(String dateValue) {
        if (dateValue == null || dateValue.isEmpty()) {
            return null;
        }

        int intDateValue = Integer.parseInt(dateValue);
        return get(intDateValue);
    }
    
    public Boolean get(Long dateValue) {
        if (dateValue == null) {
            return null;
        }
        
        int intDateValue = dateValue.intValue();
        return get(intDateValue);
    }

    /**
     * @param dateValue 20120417
     */
    public boolean get(int dateValue) {
        DayBitsUtils.check(dateValue);

        int yearIndex = (dateValue / 10000) - 2013;
        Year year = getYear(yearIndex);

        if (year == null) {
            return false;
        }

        return year.get(dateValue);
    }

    public boolean set(String dateValue, boolean value) {
        if (dateValue == null || dateValue.isEmpty()) {
            return false;
        }

        int intDateValue = Integer.parseInt(dateValue);
        return set(intDateValue, value);
    }

    public boolean set(Long dateValue) {
        if (dateValue == null) {
            return false;
        }

        int intDateValue = dateValue.intValue();
        return set(intDateValue);
    }

    public boolean set(int dateValue) {
        return set(dateValue, true);
    }

    public boolean set(int dateValue, boolean value) {
        DayBitsUtils.check(dateValue);

        int yearIndex = (dateValue / 10000) - 2013;

        Year year;
        if (value) {
            year = getYear(yearIndex, true);
        } else {
            year = getYear(yearIndex, false);
            if (year == null) {
                return false;
            }
        }

        boolean changed = year.set(dateValue, value);

        if (changed) {
            if (yearIndex >= 0) {
                this.years = DayBitsUtils.compact_years(years);
            } else {
                this.beforeYears = DayBitsUtils.compact_years(beforeYears);
            }
        }

        return changed;
    }

    public String explain(String start, String end) {
        return explain(start(start), end(end));
    }
    
    public String explain(Long start, Long end) {
        return explain(start(start), end(end));
    }

    public String explain(int start, int end) {
        StringBuilder buf = new StringBuilder();

        if (beforeYears != null) {
            for (int i = beforeYears.size() - 1; i >= 0; --i) {
                Year year = beforeYears.get(i);
                if (year != null) {
                    year.explain(buf, 2012 - i, start, end);
                }
            }
        }

        if (years != null) {
            for (int i = 0; i < years.size(); ++i) {
                Year year = years.get(i);
                if (year != null) {
                    year.explain(buf, 2013 + i, start, end);
                }
            }
        }

        return buf.toString();
    }

    public String explain() {
        StringBuilder buf = new StringBuilder();

        if (beforeYears != null) {
            for (int i = beforeYears.size() - 1; i >= 0; --i) {
                Year year = beforeYears.get(i);
                if (year != null) {
                    year.explain(buf, 2012 - i);
                }
            }
        }

        if (years != null) {
            for (int i = 0; i < years.size(); ++i) {
                Year year = years.get(i);
                if (year != null) {
                    year.explain(buf, 2013 + i);
                }
            }
        }

        return buf.toString();
    }

    public int count() {
        int count = 0;
        if (this.beforeYears != null) {
            for (Year year : this.beforeYears) {
                if (year != null) {
                    count += year.count();
                }
            }
        }

        if (this.years != null) {
            for (Year year : this.years) {
                if (year != null) {
                    count += year.count();
                }
            }
        }

        return count;
    }

    public int count(Long start, Long end) {
        return count(start(start), end(end));
    }
    
    public int count(String start, String end) {
        return count(start(start), end(end));
    }

    public int count(int start, int end) {
        int count = 0;
        if (beforeYears != null) {
            for (int i = beforeYears.size() - 1; i >= 0; --i) {
                Year year = beforeYears.get(i);
                if (year != null) {
                    count += year.count(2012 - i, start, end);
                }
            }
        }

        if (years != null) {
            for (int i = 0; i < years.size(); ++i) {
                Year year = years.get(i);
                if (year != null) {
                    count += year.count(2013 + i, start, end);
                }
            }
        }

        return count;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        output(buf);
        return buf.toString();
    }

    public void output(StringBuilder buf) {
        if (beforeYears != null) {
            for (int i = 0; i < beforeYears.size(); ++i) {
                if (i != 0) {
                    buf.append(';');
                }
                Year year = beforeYears.get(i);
                if (year != null) {
                    year.output(buf);
                }
            }
        }

        if (years != null) {
            if (buf.length() > 0) {
                buf.append('#');
            }

            for (int i = 0; i < years.size(); ++i) {
                if (i != 0) {
                    buf.append(';');
                }
                Year year = years.get(i);
                if (year != null) {
                    year.output(buf);
                }
            }
        }
    }

    Year getYear(int yearIndex) {
        return getYear(yearIndex, false);
    }

    Year getYear(int yearIndex, boolean create) {
        if (yearIndex >= 0) {
            if (years == null) {
                if (!create) {
                    return null;
                } else {
                    years = new ArrayList<Year>(4);
                }
            }

            if (yearIndex >= years.size()) {
                if (!create) {
                    return null;
                } else {
                    for (int i = years.size(); i <= yearIndex; ++i) {
                        years.add(new Year());
                    }
                }
            }

            Year year = years.get(yearIndex);

            if (year == null) {
                year = new Year();
                years.set(yearIndex, year);
            }
            return year;
        } else {
            if (beforeYears == null) {
                if (!create) {
                    return null;
                }
                beforeYears = new ArrayList<Year>(4);
            }

            yearIndex = (-yearIndex) - 1;

            if (yearIndex >= beforeYears.size()) {
                if (!create) {
                    return null;
                }
                for (int i = beforeYears.size(); i <= yearIndex; ++i) {
                    beforeYears.add(new Year());
                }
            }

            Year year = beforeYears.get(yearIndex);

            if (year == null) {
                year = new Year();
                beforeYears.set(yearIndex, year);
            }
            return year;
        }
    }

    static class Year {

        Quarter spring;
        Quarter summer;
        Quarter autumn;
        Quarter winter;

        public Year(){

        }

        public Quarter getSpring() {
            return spring;
        }

        public int merge(Year o) {
            int changed = 0;

            if (spring == null) {
                if (o.spring != null) {
                    this.spring = o.spring;
                    changed += o.spring.count();
                }
            } else {
                if (o.spring != null) {
                    changed += this.spring.merge(o.spring);
                }
            }

            if (summer == null) {
                if (o.summer != null) {
                    this.summer = o.summer;
                    changed += o.summer.count();
                }
            } else {
                if (o.summer != null) {
                    changed += this.summer.merge(o.summer);
                }
            }

            if (autumn == null) {
                if (o.autumn != null) {
                    this.autumn = o.autumn;
                    changed += o.autumn.count();
                }
            } else {
                if (o.autumn != null) {
                    changed += this.autumn.merge(o.autumn);
                }
            }

            if (winter == null) {
                if (o.winter != null) {
                    this.winter = o.winter;
                    changed += o.winter.count();
                }
            } else {
                if (o.winter != null) {
                    changed += this.winter.merge(o.winter);
                }
            }

            return changed;
        }

        public void and(Year o) {
            this.spring = DayBitsUtils.and(this.spring, o.spring);
            this.summer = DayBitsUtils.and(this.summer, o.summer);
            this.autumn = DayBitsUtils.and(this.autumn, o.autumn);
            this.winter = DayBitsUtils.and(this.winter, o.winter);
        }

        public Quarter getSpring(boolean create) {
            if (create && spring == null) {
                spring = new Quarter(null);
            }
            return spring;
        }

        public void setSpring(Quarter spring) {
            this.spring = spring;
        }

        public Quarter getSummer() {
            return summer;
        }

        public Quarter getSummer(boolean create) {
            if (create && summer == null) {
                summer = new Quarter(null);
            }
            return summer;
        }

        public void setSummer(Quarter summer) {
            this.summer = summer;
        }

        public Quarter getAutumn() {
            return autumn;
        }

        public Quarter getAutumn(boolean create) {
            if (create && autumn == null) {
                autumn = new Quarter(null);
            }
            return autumn;
        }

        public void setAutumn(Quarter autumn) {
            this.autumn = autumn;
        }

        public Quarter getWinter() {
            return winter;
        }

        public Quarter getWinter(boolean create) {
            if (create && winter == null) {
                winter = new Quarter(null);
            }
            return winter;
        }

        public void setWinter(Quarter winter) {
            this.winter = winter;
        }

        public boolean set(int dateValue, boolean value) {
            int year = dateValue / 10000;
            int monthDayValue = dateValue % 10000;

            int month = monthDayValue / 100;

            if (month < 1 || month > 12) {
                throw new IllegalStateException("illegal dateValue : " + dateValue);
            }

            int dayOfMonth = monthDayValue % 100;

            if (dayOfMonth < 1 || dayOfMonth > 31) {
                throw new IllegalStateException("illegal dateValue : " + dateValue);
            }

            Quarter quarter;
            if (month <= 3) {
                quarter = getSpring(value);
            } else if (month <= 6) {
                quarter = getSummer(value);
            } else if (month <= 9) {
                quarter = getAutumn(value);
            } else {
                quarter = getWinter(value);
            }

            if (quarter == null) {
                return false;
            }

            int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, month, dayOfMonth);

            return quarter.set(dayOfQuarter, value);
        }

        public boolean get(int dateValue) {
            int year = dateValue / 10000;
            int monthDayValue = dateValue % 10000;

            int month = monthDayValue / 100;

            if (month < 1 || month > 12) {
                throw new IllegalStateException("illegal dateValue : " + dateValue);
            }

            int dayOfMonth = monthDayValue % 100;

            if (dayOfMonth < 1 || dayOfMonth > 31) {
                throw new IllegalStateException("illegal dateValue : " + dateValue);
            }

            Quarter quarter;
            if (month >= 1 && month <= 3) {
                quarter = spring;
            } else if (month >= 4 && month <= 6) {
                quarter = summer;
            } else if (month >= 7 && month <= 9) {
                quarter = autumn;
            } else {
                quarter = winter;
            }

            if (quarter == null) {
                return false;
            }

            int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, month, dayOfMonth);

            return quarter.get(dayOfQuarter);
        }

        public void explain(StringBuilder out, int year, int start, int end) {
            explain(out, year, 0, spring, start, end);
            explain(out, year, 1, summer, start, end);
            explain(out, year, 2, autumn, start, end);
            explain(out, year, 3, winter, start, end);
        }

        public void explain(StringBuilder out, int year) {
            explain(out, year, 0, spring);
            explain(out, year, 1, summer);
            explain(out, year, 2, autumn);
            explain(out, year, 3, winter);
        }

        private final void explain(StringBuilder out, int year, int quarterIndex, Quarter quarter) {
            if (quarter == null) {
                return;
            }
            quarter.explain(out, year, quarterIndex);
        }

        private final void explain(StringBuilder out, int year, int quarterIndex, Quarter quarter, int start, int end) {
            if (quarter == null) {
                return;
            }
            quarter.explain(out, year, quarterIndex, start, end);
        }

        public int first(int year, int start, int end) {
            int first = first(year, 0, spring, start, end);
            if (first == -1) {
                first = first(year, 1, summer, start, end);
            }
            if (first == -1) {
                first = first(year, 2, autumn, start, end);
            }
            if (first == -1) {
                first = first(year, 3, winter, start, end);
            }
            return first;
        }

        private int first(int year, int quarterIndex, Quarter quarter, int start, int end) {
            if (quarter == null) {
                return -1;
            }

            return quarter.first(year, quarterIndex, start, end);
        }

        public int last(int year, int start, int end) {
            int last = last(year, 3, winter, start, end);
            if (last == -1) {
                last = last(year, 2, autumn, start, end);
            }
            if (last == -1) {
                last = last(year, 1, summer, start, end);
            }
            if (last == -1) {
                last = last(year, 0, spring, start, end);
            }
            return last;
        }

        private int last(int year, int quarterIndex, Quarter quarter, int start, int end) {
            if (quarter == null) {
                return -1;
            }

            return quarter.last(year, quarterIndex, start, end);
        }

        public int count() {
            return count(spring) + count(summer) + count(autumn) + count(winter);
        }

        public boolean isEmpty() {
            return count() == 0;
        }

        private int count(Quarter quarter) {
            if (quarter == null) {
                return 0;
            }
            return quarter.count();
        }

        public int count(int year, int start, int end) {
            int count = count(year, 0, spring, start, end);
            count += count(year, 1, summer, start, end);
            count += count(year, 2, autumn, start, end);
            count += count(year, 3, winter, start, end);
            return count;
        }

        private int count(int year, int quarterIndex, Quarter quarter, int start, int end) {
            if (quarter == null) {
                return 0;
            }

            return quarter.count(year, quarterIndex, start, end);
        }

        public void output(StringBuilder out) {
            if (winter != null) {
                output(out, spring);
                out.append(',');
                output(out, summer);
                out.append(',');
                output(out, autumn);
                out.append(',');
                output(out, winter);
                return;
            }

            if (autumn != null) {
                output(out, spring);
                out.append(',');
                output(out, summer);
                out.append(',');
                output(out, autumn);
                return;
            }

            if (summer != null) {
                output(out, spring);
                out.append(',');
                output(out, summer);
                return;
            }

            output(out, spring);
        }

        private void output(StringBuilder out, Quarter quarter) {
            if (quarter == null) {
                return;
            }
            quarter.output(out);
        }

    }

    static class Quarter {

        private byte[] bytes;

        public Quarter(byte[] bytes){
            super();
            this.bytes = bytes;
        }

        public void output(StringBuilder out) {
            if (bytes == null || bytes.length == 0) {
                return;
            }

            int bytesLen = bytes.length;
            int numFullGroups = bytesLen / 3;
            int numBytesInPartialGroup = bytesLen - 3 * numFullGroups;
            int resultLen = 4 * ((bytesLen + 2) / 3);
            out.ensureCapacity(out.length() + resultLen);
            char[] intToAlpha = DayBitsUtils.intToBase64;

            // Translate all full groups from byte array elements to Base64
            int inCursor = 0;
            for (int i = 0; i < numFullGroups; i++) {
                int byte0 = bytes[inCursor++] & 0xff;
                int byte1 = bytes[inCursor++] & 0xff;
                int byte2 = bytes[inCursor++] & 0xff;
                out.append(intToAlpha[byte0 >> 2]);
                out.append(intToAlpha[(byte0 << 4) & 0x3f | (byte1 >> 4)]);
                out.append(intToAlpha[(byte1 << 2) & 0x3f | (byte2 >> 6)]);
                out.append(intToAlpha[byte2 & 0x3f]);
            }

            // Translate partial group if present
            if (numBytesInPartialGroup != 0) {
                int byte0 = bytes[inCursor++] & 0xff;
                out.append(intToAlpha[byte0 >> 2]);
                if (numBytesInPartialGroup == 1) {
                    out.append(intToAlpha[(byte0 << 4) & 0x3f]);
                    out.append("==");
                } else {
                    // assert numBytesInPartialGroup == 2;
                    int byte1 = bytes[inCursor++] & 0xff;
                    out.append(intToAlpha[(byte0 << 4) & 0x3f | (byte1 >> 4)]);
                    out.append(intToAlpha[(byte1 << 2) & 0x3f]);
                    out.append('=');
                }
            }
            // assert inCursor == a.length;
            // assert result.length() == resultLen;
        }

        public void explain(StringBuilder out, int year, int quarterIndex) {
            if (bytes == null) {
                return;
            }

            for (int i = 0; i < bytes.length; ++i) {
                byte value = bytes[i];

                for (int bitIndex = 0; bitIndex < 8; ++bitIndex) {
                    if (((value & (1 << bitIndex)) != 0)) {
                        int dayOfQuarter = i * 8 + bitIndex;
                        int dateValue = DayBitsUtils.getDateValue(year, quarterIndex, dayOfQuarter);
                        if (out.length() > 0) {
                            out.append(',');
                        }
                        out.append(dateValue);
                    }
                }
            }
        }

        public void explain(StringBuilder out, int year, int quarterIndex, int start, int end) {
            if (bytes == null) {
                return;
            }

            for (int i = 0; i < bytes.length; ++i) {
                byte value = bytes[i];

                for (int bitIndex = 0; bitIndex < 8; ++bitIndex) {
                    if (((value & (1 << bitIndex)) != 0)) {
                        int dayOfQuarter = i * 8 + bitIndex;
                        int dateValue = DayBitsUtils.getDateValue(year, quarterIndex, dayOfQuarter);
                        if (dateValue > end) {
                            break;
                        }
                        if (dateValue >= start && dateValue <= end) {
                            if (out.length() > 0) {
                                out.append(',');
                            }
                            out.append(dateValue);
                        }
                    }
                }
            }
        }

        public int first(int year, int quarterIndex, int start, int end) {
            if (bytes == null) {
                return -1;
            }

            for (int i = 0; i < bytes.length; ++i) {
                byte value = bytes[i];

                for (int bitIndex = 0; bitIndex < 8; ++bitIndex) {
                    if (((value & (1 << bitIndex)) != 0)) {
                        int dayOfQuarter = i * 8 + bitIndex;
                        int dateValue = DayBitsUtils.getDateValue(year, quarterIndex, dayOfQuarter);
                        if (dateValue >= start && dateValue <= end) {
                            return dateValue;
                        }
                    }
                }
            }

            return -1;
        }

        public int last(int year, int quarterIndex, int start, int end) {
            if (bytes == null) {
                return -1;
            }

            for (int i = bytes.length - 1; i >= 0; --i) {
                byte value = bytes[i];

                for (int bitIndex = 7; bitIndex >= 0; --bitIndex) {
                    if (((value & (1 << bitIndex)) != 0)) {
                        int dayOfQuarter = i * 8 + bitIndex;
                        int dateValue = DayBitsUtils.getDateValue(year, quarterIndex, dayOfQuarter);
                        if (dateValue >= start && dateValue <= end) {
                            return dateValue;
                        }
                    }
                }
            }

            return -1;
        }

        public boolean get(int dayOfQuarter) {
            if (bytes == null || bytes.length == 0) {
                return false;
            }

            int byteIndex = dayOfQuarter / 8;
            int bitIndex = dayOfQuarter % 8;
            if (bytes.length < byteIndex + 1) {
                return false;
            }

            return ((bytes[byteIndex] & (1 << bitIndex)) != 0);
        }

        public void and(Quarter o) {
            this.bytes = DayBitsUtils.and(this.bytes, o.bytes);
        }

        public boolean set(int dayOfQuarter) {
            return set(dayOfQuarter, true);
        }

        public boolean set(int dayIndex, boolean value) {
            int byte_index = dayIndex / 8;
            int bitIndex = dayIndex % 8;

            if (bytes == null) {
                bytes = new byte[byte_index + 1];
            } else if (bytes.length < byte_index + 1) {
                bytes = Arrays.copyOf(bytes, byte_index + 1);
            }

            boolean changed = ((bytes[byte_index] & (1 << bitIndex)) != 0) != value;
            if (value) {
                bytes[byte_index] |= 1 << bitIndex;
            } else {
                bytes[byte_index] &= ~(1 << bitIndex);
                bytes = DayBitsUtils.compact(bytes);
            }

            return changed;
        }

        public int count() {
            if (bytes == null || bytes.length == 0) {
                return 0;
            }

            int count = 0;
            for (int i = 0; i < bytes.length; ++i) {
                byte value = bytes[i];

                for (int bitIndex = 0; bitIndex < 8; ++bitIndex) {
                    if (((value & (1 << bitIndex)) != 0)) {
                        count++;
                    }
                }
            }
            return count;
        }

        public int count(int year, int quarterIndex, int start, int end) {
            if (bytes == null) {
                return 0;
            }

            int count = 0;
            for (int i = 0; i < bytes.length; ++i) {
                byte value = bytes[i];

                for (int bitIndex = 0; bitIndex < 8; ++bitIndex) {
                    if (((value & (1 << bitIndex)) != 0)) {
                        int dayOfQuarter = i * 8 + bitIndex;
                        int dateValue = DayBitsUtils.getDateValue(year, quarterIndex, dayOfQuarter);
                        if (dateValue > end) {
                            break;
                        }
                        if (dateValue >= start && dateValue <= end) {
                            count++;
                        }
                    }
                }
            }

            return count;
        }

        public int merge(Quarter o) {
            int changed = 0;
            if (this.bytes == null) {
                if (o.bytes != null) {
                    this.bytes = o.bytes;
                    changed = o.count();
                }
            } else {
                int count = this.count();

                if (o.bytes != null) {
                    if (this.bytes.length < o.bytes.length) {
                        this.bytes = Arrays.copyOf(this.bytes, o.bytes.length);
                    }
                    for (int i = 0; i < o.bytes.length; ++i) {
                        this.bytes[i] |= o.bytes[i];
                    }
                }
                changed += this.count() - count;
            }
            return changed;
        }
    }
}

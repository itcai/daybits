package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;

public class DayBitsCount extends UDF {

    public Long evaluate(String text) {
        if (text == null || text.isEmpty()) {
            return 0L;
        }

        DayBits daybits = DayBitsUtils.parse(text);
        return Long.valueOf(daybits.count());
    }

    public Long evaluate(String text, Long start, Long end) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return Long.valueOf(daybits.count(start, end));
    }

    public Long evaluate(String text, Long start) {
        return evaluate(text, start, null);
    }

    public Long evaluate(String text, String start, String end) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return Long.valueOf(daybits.count(start, end));
    }

    public Long evaluate(String text, String start) {
        return evaluate(text, start, null);
    }
}

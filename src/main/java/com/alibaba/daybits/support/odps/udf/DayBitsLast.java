package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;

public class DayBitsLast extends UDF {

    public DayBitsLast(){
    }

    public Long evaluate(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        DayBits daybits = DayBitsUtils.parse(text);
        return daybits.last();
    }

    public Long evaluate(String text, String startDate) {
        return evaluate(text, startDate, null);
    }

    public Long evaluate(String text, Long startDate) {
        return evaluate(text, startDate, null);
    }

    public Long evaluate(String text, String start, String end) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return daybits.last(start, end);
    }

    public Long evaluate(String text, Long start, Long end) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return daybits.last(start, end);
    }
}

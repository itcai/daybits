package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;
import com.aliyun.odps.udf.UDF;

public class DayBitsCount extends UDF {

    public Long evaluate(String text) {
        if (text == null || text.isEmpty()) {
            return 0L;
        }
        
        DayBitsParser parser = new DayBitsParser(text);
        DayBits daybits = parser.parse();
        return Long.valueOf(daybits.count());
    }

    public Long evaluate(String text, Long start, Long end) {
        if (text == null || text.isEmpty()) {
            return 0L;
        }
        
        DayBitsParser parser = new DayBitsParser(text);
        DayBits daybits = parser.parse();
        return Long.valueOf(daybits.count(start, end));
    }
    
    public Long evaluate(String text, Long start) {
        return evaluate(text, start, null);
    }
    
    public Long evaluate(String text, String start, String end) {
        Long startValue = start == null ? null : Long.parseLong(start);
        Long endValue = end == null ? null : Long.parseLong(end);
        return evaluate(text, startValue, endValue);
    }
    
    public Long evaluate(String text, String start) {
        return evaluate(text, start, null);
    }
}

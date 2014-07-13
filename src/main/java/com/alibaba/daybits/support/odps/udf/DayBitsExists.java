package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;

public class DayBitsExists extends UDF {

    public DayBitsExists(){
    }

    public Boolean evaluate(String text) {
        return evaluate(text, (Long) null, (Long) null);
    }
    
    public Boolean evaluate(String text, String startDate) {
        return evaluate(text, startDate, null);
    }

    public Boolean evaluate(String text, Long startDate) {
        return evaluate(text, startDate, null);
    }

    public Boolean evaluate(String text, String start, String end) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return false;
        }
        return daybits.exists(start, end);
    }

    public Boolean evaluate(String text, Long start, Long end) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return false;
        }
        return daybits.exists(start, end);
    }
}

package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;

public class DayBitsAnd extends UDF {

    public String evaluate(String a, String b) {
        DayBits daybits_a = DayBitsUtils.parse(a);
        DayBits daybits_b = DayBitsUtils.parse(b);
        
        if (a == null || daybits_b == null) {
            return null;
        }
        
        daybits_a.and(daybits_b);
        String text = daybits_a.toString();
        if (text != null && text.isEmpty()) {
            return null;
        }
        return text;
    }

    public String evaluate(String a, String b, String c) {
        DayBits daybits_a = DayBitsUtils.parse(a);
        DayBits daybits_b = DayBitsUtils.parse(b);
        DayBits daybits_c = DayBitsUtils.parse(c);
        
        if (a == null || daybits_b == null || daybits_c == null) {
            return null;
        }
        
        daybits_a.and(daybits_b);
        daybits_a.and(daybits_c);
        String text = daybits_a.toString();
        if (text != null && text.isEmpty()) {
            return null;
        }
        return text;
    }
    
    public String evaluate(String a, String b, String c, String d) {
        DayBits daybits_a = DayBitsUtils.parse(a);
        DayBits daybits_b = DayBitsUtils.parse(b);
        DayBits daybits_c = DayBitsUtils.parse(c);
        DayBits daybits_d = DayBitsUtils.parse(d);
        
        if (a == null || daybits_b == null || daybits_c == null) {
            return null;
        }
        
        daybits_a.and(daybits_b);
        daybits_a.and(daybits_c);
        daybits_a.and(daybits_d);
        String text = daybits_a.toString();
        if (text != null && text.isEmpty()) {
            return null;
        }
        return text;
    }
}

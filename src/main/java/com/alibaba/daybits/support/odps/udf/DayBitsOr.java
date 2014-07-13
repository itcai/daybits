package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;


public class DayBitsOr extends UDF {

    public String evaluate(String a, String b) {
        DayBits daybits_a = DayBitsUtils.parse(a);
        DayBits daybits_b = DayBitsUtils.parse(b);
        
        DayBits daybits_ab = DayBitsUtils.or(daybits_a, daybits_b);
        return DayBitsUtils.toString(daybits_ab);
    }

    public String evaluate(String a, String b, String c) {
        DayBits daybits_a = DayBitsUtils.parse(a);
        DayBits daybits_b = DayBitsUtils.parse(b);
        DayBits daybits_c = DayBitsUtils.parse(c);
        
        DayBits daybits_ab = DayBitsUtils.or(daybits_a, daybits_b);
        DayBits daybits_abc = DayBitsUtils.or(daybits_ab, daybits_c);
        return DayBitsUtils.toString(daybits_abc);
    }
    
    public String evaluate(String a, String b, String c, String d) {
        DayBits daybits_a = DayBitsUtils.parse(a);
        DayBits daybits_b = DayBitsUtils.parse(b);
        DayBits daybits_c = DayBitsUtils.parse(c);
        DayBits daybits_d = DayBitsUtils.parse(d);
        
        DayBits daybits_ab = DayBitsUtils.or(daybits_a, daybits_b);
        DayBits daybits_cd = DayBitsUtils.or(daybits_c, daybits_d);
        DayBits daybits_abcd = DayBitsUtils.or(daybits_ab, daybits_cd);
        return DayBitsUtils.toString(daybits_abcd);
    }
}


package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;

public class DayBitsFirst extends UDF {

    public DayBitsFirst(){
    }

    public Long evaluate(String text) {        
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return daybits.first();
    }
    
    public Long evaluate(String text, String start, String end) {        
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return daybits.first(start, end);
    }
    
    public Long evaluate(String text, Long start, Long end) {        
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return daybits.first(start, end);
    }
}

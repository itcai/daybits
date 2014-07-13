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
}

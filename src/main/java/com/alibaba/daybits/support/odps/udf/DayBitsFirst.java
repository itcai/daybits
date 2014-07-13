package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;
import com.aliyun.odps.udf.UDF;

public class DayBitsFirst extends UDF {

    public DayBitsFirst(){
    }

    public Long evaluate(String text) {
        DayBitsParser parser = new DayBitsParser(text);
        DayBits daybits = parser.parse();
        return Long.valueOf(daybits.first());
    }
}

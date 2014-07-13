package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;
import com.aliyun.odps.udf.UDF;

public class DayBitsSet extends UDF {

    public DayBitsSet(){
    }

    public String evaluate(String text, String date) {
        return evaluate(text, date, true);
    }

    public String evaluate(String text, String date, Boolean value) {
        DayBitsParser parser = new DayBitsParser(text);
        DayBits daybits = parser.parse();
        boolean changed = daybits.set(date, value);
        if (!changed) {
            return text;
        }

        return daybits.toString();
    }
}

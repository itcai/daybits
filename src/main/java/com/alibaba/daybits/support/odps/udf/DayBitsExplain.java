package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;
import com.aliyun.odps.udf.UDF;

public class DayBitsExplain extends UDF {

    public DayBitsExplain(){
    }

    public String evaluate(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        
        DayBitsParser parser = new DayBitsParser(text);
        DayBits daybits = parser.parse();
        return daybits.explain();
    }

    public String evaluate(String text, String startDate, String endDate) {
        DayBitsParser parser = new DayBitsParser(text);
        DayBits daybits = parser.parse();
        return daybits.explain(startDate, endDate);
    }
}

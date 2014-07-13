package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsUtils;
import com.aliyun.odps.udf.UDF;

public class DayBitsExplain extends UDF {

    public DayBitsExplain(){
    }

    public String evaluate(String text) {
        DayBits daybits = DayBitsUtils.parse(text);
        if (daybits == null) {
            return null;
        }
        return daybits.explain();
    }

    public String evaluate(String text, String startDate) {
        return evaluate(text, startDate, null);
    }

    public String evaluate(String text, String startDate, String endDate) {
        DayBits daybits = DayBitsUtils.parse(text);

        if (daybits == null) {
            return null;
        }

        return daybits.explain(startDate, endDate);
    }

    public String evaluate(String text, Long startDate) {
        return evaluate(text, startDate, null);
    }

    public String evaluate(String text, Long startDate, Long endDate) {
        DayBits daybits = DayBitsUtils.parse(text);

        if (daybits == null) {
            return null;
        }

        return daybits.explain(startDate, endDate);
    }
}

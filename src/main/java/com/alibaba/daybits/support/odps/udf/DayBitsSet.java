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
        DayBits daybits;
        if (text == null || text.isEmpty()) {
            if (date == null || value == null || !value) {
                return null;
            }
            daybits = new DayBits();
        } else {
            DayBitsParser parser = new DayBitsParser(text);
            daybits = parser.parse();
        }
        
        boolean changed = daybits.set(date, value);
        if (!changed) {
            return text;
        }

        return daybits.toString();
    }
    
    public String evaluate(String text, Long date) {
        return evaluate(text, date, true);
    }
    
    public String evaluate(String text, Long date, Boolean value) {
        DayBits daybits;
        if (text == null || text.isEmpty()) {
            if (date == null || value == null || !value) {
                return null;
            }
            daybits = new DayBits();
        } else {
            DayBitsParser parser = new DayBitsParser(text);
            daybits = parser.parse();
        }
        
        boolean changed = daybits.set(date.intValue(), value);
        if (!changed) {
            return text;
        }

        return daybits.toString();
    }
}

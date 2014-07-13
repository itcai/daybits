package com.alibaba.daybits.bvt.support.odps;

import junit.framework.TestCase;

import com.alibaba.daybits.support.odps.udf.DayBitsFirst;

public class DayBitsFirstTest extends TestCase {

    public void test_first() throws Exception {
        String text = ",,,AAAAAAAAAAAAAAAI;AQ==";
        DayBitsFirst udf = new DayBitsFirst();
        
        System.out.println(udf.evaluate(text));
    }
}

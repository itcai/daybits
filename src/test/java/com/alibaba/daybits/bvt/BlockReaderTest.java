package com.alibaba.daybits.bvt;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;

public class BlockReaderTest extends TestCase {

    public void test_0() throws Exception {
        DayBitsParser reader = new DayBitsParser(";AAAAAChCywMgAg==");
        DayBits daybits = reader.parse();
        Assert.assertFalse(daybits.get(20130101));
        Assert.assertFalse(daybits.get(20140101));
        Assert.assertFalse(daybits.get(20110205));
        Assert.assertTrue(daybits.get(20140205));

        Assert.assertEquals("20140205,20140207,20140211,20140216,20140218,20140219,20140221,20140224,20140225,20140226,20140227,20140311,20140315", daybits.explain());
    }

}

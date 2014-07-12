package com.alibaba.daybits.bvt;

import org.junit.Assert;

import junit.framework.TestCase;

import com.alibaba.daybits.DayBits;

public class DayBitsTest_0 extends TestCase {

    public void test_build() throws Exception {
        DayBits daybits = new DayBits();

        Assert.assertTrue(daybits.set(20140201));
        Assert.assertTrue(daybits.set(20130102));

        Assert.assertFalse(daybits.set(20140201)); // not changed
        Assert.assertFalse(daybits.set(20130102)); // no changed
        
        Assert.assertFalse(daybits.get(20140101));
        Assert.assertFalse(daybits.get(20110205));
        Assert.assertTrue(daybits.get(20140201));
        Assert.assertTrue(daybits.get(20130102));
    }
}

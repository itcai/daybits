package com.alibaba.daybits.bvt;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;

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
        
        Assert.assertTrue(daybits.set(20110205));
        Assert.assertFalse(daybits.set(20110205));
        Assert.assertTrue(daybits.get(20110205));
        
        Assert.assertTrue(daybits.set(20090321));
        Assert.assertFalse(daybits.set(20090321));
        Assert.assertTrue(daybits.get(20090321));
        
        Assert.assertTrue(daybits.set(20090416));
        Assert.assertFalse(daybits.set(20090416));
        Assert.assertTrue(daybits.get(20090416));
        
        Assert.assertEquals("20090321,20090416,20110205,20130102,20140201", daybits.explain());
//        System.out.println(daybits.explain());
        
        String text = daybits.toString();
//        System.out.println(text);
        DayBits daybits_2 = new DayBitsParser(text).parse();
        
        Assert.assertEquals(daybits.explain(), daybits_2.explain());
        Assert.assertEquals(daybits.toString(), daybits_2.toString());
    }
}

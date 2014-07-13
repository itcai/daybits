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

        Assert.assertEquals(2, daybits.count());
        Assert.assertEquals(2, daybits.count(20130102, 20140201));
        Assert.assertEquals(2, daybits.count(20010201, 20140201));
        Assert.assertEquals(1, daybits.count(20140201, 20140201));
        Assert.assertEquals(1, daybits.count(20130102, 20130102));
        Assert.assertEquals(0, daybits.count(20120102, 20120102));
        Assert.assertEquals(20130102, daybits.first().intValue());
        Assert.assertEquals(20140201, daybits.last().intValue());

        Assert.assertFalse(daybits.set(20140201)); // not changed
        Assert.assertFalse(daybits.set(20130102)); // no changed

        Assert.assertEquals(2, daybits.count());
        Assert.assertEquals(20130102, daybits.first().intValue());
        Assert.assertEquals(20140201, daybits.last().intValue());

        Assert.assertFalse(daybits.get(20140101));
        Assert.assertFalse(daybits.get(20110205));
        Assert.assertTrue(daybits.get(20140201));
        Assert.assertTrue(daybits.get(20130102));

        Assert.assertTrue(daybits.set(20110205));
        Assert.assertFalse(daybits.set(20110205));
        Assert.assertTrue(daybits.get(20110205));
        Assert.assertEquals(20110205, daybits.first().intValue());
        Assert.assertEquals(20140201, daybits.last().intValue());

        Assert.assertTrue(daybits.set(20090321));
        Assert.assertFalse(daybits.set(20090321));
        Assert.assertTrue(daybits.get(20090321));
        Assert.assertEquals(20090321, daybits.first().intValue());
        Assert.assertEquals(20140201, daybits.last().intValue());

        Assert.assertTrue(daybits.set(20090416));
        Assert.assertFalse(daybits.set(20090416));
        Assert.assertTrue(daybits.get(20090416));
        Assert.assertEquals(20090321, daybits.first().intValue());
        Assert.assertEquals(20140201, daybits.last().intValue());

        Assert.assertTrue(daybits.set(20090322));
        Assert.assertTrue(daybits.set(20090323));

        Assert.assertTrue(daybits.set(20140202));
        Assert.assertTrue(daybits.set(20140203));

        Assert.assertEquals(20090321, daybits.first().intValue());
        Assert.assertEquals(20140203, daybits.last().intValue());

        Assert.assertEquals("20090321,20090322,20090323,20090416,20110205,20130102,20140201,20140202,20140203",
                            daybits.explain());
        Assert.assertEquals(9, daybits.count());
        Assert.assertEquals(9, daybits.count(20090321, 20140203));
        Assert.assertEquals(8, daybits.count(20090321, 20140202));
        Assert.assertEquals(7, daybits.count(20090322, 20140202));
        // System.out.println(daybits.explain());

        // System.out.println(text);

        {
            String text = daybits.toString();
            DayBits daybits_2 = new DayBitsParser(text).parse();

            Assert.assertEquals(daybits.explain(), daybits_2.explain());
            Assert.assertEquals(daybits.toString(), daybits_2.toString());
            Assert.assertEquals(daybits.count(), daybits_2.count());
        }

        {
            DayBits daybits_3 = new DayBits();
            int changed = daybits.merge(daybits_3);
            Assert.assertEquals(0, changed);
            Assert.assertEquals(9, daybits.count());
        }
        
        {
            DayBits daybits_3 = new DayBits();
            daybits_3.set(20090321);
            int changed = daybits.merge(daybits_3);
            Assert.assertEquals(0, changed);
            Assert.assertEquals(9, daybits.count());
        }
        
        {
            DayBits daybits_3 = new DayBits();
            daybits_3.set(20090324);
            int changed = daybits.merge(daybits_3);
            Assert.assertEquals(1, changed);
            Assert.assertEquals(10, daybits.count());
            Assert.assertTrue(daybits.get(20090324));
        }
        
        {
            DayBits daybits_3 = new DayBits();
            daybits_3.set(20080324);
            daybits_3.set(20080325);
            int changed = daybits.merge(daybits_3);
            Assert.assertEquals(2, changed);
            Assert.assertEquals(12, daybits.count());
            Assert.assertTrue(daybits.get(20080324));
            Assert.assertTrue(daybits.get(20080325));
        }
        
        {
            DayBits daybits_3 = new DayBits();
            daybits_3.set(20060324);
            daybits_3.set(20060325);
            int changed = daybits.merge(daybits_3);
            Assert.assertEquals(2, changed);
            Assert.assertEquals(14, daybits.count());
            Assert.assertTrue(daybits.get(20060324));
            Assert.assertTrue(daybits.get(20060325));
        }
        
        {
            DayBits daybits_3 = new DayBits();
            daybits_3.set(20160324);
            daybits_3.set(20160325);
            int changed = daybits.merge(daybits_3);
            Assert.assertEquals(2, changed);
            Assert.assertEquals(16, daybits.count());
            Assert.assertTrue(daybits.get(20160324));
            Assert.assertTrue(daybits.get(20160325));
        }
    }
}

package com.alibaba.daybits.bvt;

import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.daybits.DayBits;

public class DayBitsSetTest extends TestCase {

    public void test_set() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        for (long i = 0; i < 366 * 200; ++i) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            int dateValue = year * 10000 + month * 100 + dayOfMonth;

            DayBits daybits = new DayBits();
            daybits.set(dateValue);

            Assert.assertEquals(1, daybits.count());
            if (dateValue != daybits.first().intValue()) {
                Long first = daybits.first();
//                System.out.println(DayBitsUtils.seconds(year, 4, 14));
//                System.out.println(DayBitsUtils.seconds(year, 4, 15));
//                System.out.println(DayBitsUtils.seconds(year, 4, 16));
//                System.out.println(DayBitsUtils.seconds(year, 4, 17));
                Assert.fail("explected : " + dateValue + ", but " + first);
            }

            Assert.assertEquals(dateValue, daybits.last().intValue());
            Assert.assertTrue(daybits.get(dateValue));
            Assert.assertTrue(daybits.get(Integer.toString(dateValue)));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
    }
}

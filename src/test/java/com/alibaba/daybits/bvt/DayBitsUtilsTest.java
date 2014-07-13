package com.alibaba.daybits.bvt;

import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.daybits.DayBitsUtils;

public class DayBitsUtilsTest extends TestCase {

    public void test_0() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        for (long i = 0; i < 366 * 30; ++i) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            long seconds = DayBitsUtils.seconds(year, month, dayOfMonth);
            long millis = ((long) seconds) * 1000L;
            if (millis != calendar.getTimeInMillis()) {
                DayBitsUtils.seconds(year, month, dayOfMonth);
                Assert.fail("date : " + calendar.getTime().toString());    
            }
            
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}

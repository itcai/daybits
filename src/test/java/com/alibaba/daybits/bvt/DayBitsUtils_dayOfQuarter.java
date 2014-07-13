package com.alibaba.daybits.bvt;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.daybits.DayBitsUtils;

public class DayBitsUtils_dayOfQuarter extends TestCase {

    @SuppressWarnings("unused")
    public void test_0() throws Exception {

        for (int year = 1970; year <= 2200; ++year) {
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 1, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 1, dayOfMonth);
                    // System.out.println(year + "-1-" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 28; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 2, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 2, dayOfMonth);
//                     System.out.println(year + "-2-" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 31);

                    lastSeconds = seconds;
                }
            }            
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 4, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 4, dayOfMonth);
                    // System.out.println(year + "04" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 5, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 5, dayOfMonth);
                    // System.out.println(year + "05" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 30);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 6, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 6, dayOfMonth);
                    // System.out.println(year + "06" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 61);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 7, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 7, dayOfMonth);
                    // System.out.println(year + "07" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 8, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 8, dayOfMonth);
                    // System.out.println(year + "08" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 31);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 9, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 9, dayOfMonth);
                    // System.out.println(year + "09" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 62);
                    
                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 10, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 10, dayOfMonth);
                    // System.out.println(year + "10" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 11, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 11, dayOfMonth);
//                     System.out.println(year + "-11-" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 31);

                    lastSeconds = seconds;
                }
            }
            {
                long lastSeconds = 0;
                for (int dayOfMonth = 1; dayOfMonth <= 30; ++dayOfMonth) {
                    int dayOfQuarter = DayBitsUtils.dayOfQuarter(year, 12, dayOfMonth);
                    long seconds = DayBitsUtils.seconds(year, 12, dayOfMonth);
                    // System.out.println(year + "12" + dayOfMonth + "\t->\t" + (seconds - lastSeconds));
                    Assert.assertEquals(dayOfMonth, dayOfQuarter + 1 - 61);

                    lastSeconds = seconds;
                }
            }
        }
    }
}

package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;
import com.aliyun.odps.udf.UDAF;
import com.aliyun.odps.udf.UDAFEvaluator;

public class DayBitsConcat extends UDAF {
    public static class DateBitsMergeEvaluator implements UDAFEvaluator {

        private DayBits daybits;

        @Override
        public void init() {
            daybits = new DayBits();
        }

        public void iterate(String date) {
            daybits.set(date);
        }
        
        public void iterate(Long date) {
            daybits.set(date);
        }

        public void merge(String pr) {
            DayBitsParser parser = new DayBitsParser(pr);
            DayBits prDaybits = parser.parse();
            if (this.daybits != null) {
                this.daybits.merge(prDaybits);
            } else {
                this.daybits = prDaybits;
            }
        }

        public String terminatePartial() {
            if (daybits == null) {
                return null;
            }
            
            return daybits.toString();
        }

        public String terminate() {
            if (daybits == null) {
                return null;
            }
            
            return daybits.toString();
        }

        public void setPartial(String pr) {
            DayBitsParser parser = new DayBitsParser(pr);
            this.daybits = parser.parse();
        }
    }
}

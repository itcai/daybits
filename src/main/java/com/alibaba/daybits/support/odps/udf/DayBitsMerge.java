package com.alibaba.daybits.support.odps.udf;

import com.alibaba.daybits.DayBits;
import com.alibaba.daybits.DayBitsParser;
import com.aliyun.odps.udf.UDAF;
import com.aliyun.odps.udf.UDAFEvaluator;

public class DayBitsMerge extends UDAF {

	public static class DateBitsMergeEvaluator implements UDAFEvaluator {
		private DayBits daybits;

		@Override
		public void init() {
		    daybits = new DayBits();
		}

		public void iterate(String text) {
		    if (text == null || text.isEmpty()) {
		        return;
		    }
		    
		    DayBitsParser parser = new DayBitsParser(text);
            DayBits other = parser.parse();
			daybits.merge(other);
		}

		public void merge(String pr) {
		    if (pr == null || pr.isEmpty()) {
                return;
            }
            
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
	            
	            String text = daybits.toString();
	            
	            if (text.isEmpty()) {
	                return null;
	            }
	            
	            return text;
	        }

	        public String terminate() {
	            if (daybits == null) {
	                return null;
	            }
	            
	            String text = daybits.toString();
	            
	            if (text.isEmpty()) {
	                return null;
	            }
	            
	            return text;
	        }

	        public void setPartial(String pr) {
	            if (pr == null || pr.isEmpty()) {
	                this.daybits = new DayBits();
	                return;
	            }
	            
	            DayBitsParser parser = new DayBitsParser(pr);
	            this.daybits = parser.parse();
	        }
	    }
}
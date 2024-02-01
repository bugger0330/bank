package com.tenco.bank.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
	
	
	// timestamp => String
	public static String timestampToString(Timestamp timestamp) {
		// YYYY-MM-dd HH:mm:ss
		SimpleDateFormat st = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		
		return st.format(timestamp);
	}
	
}

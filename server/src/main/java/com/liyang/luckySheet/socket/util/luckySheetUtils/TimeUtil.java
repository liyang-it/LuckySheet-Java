package com.liyang.luckySheet.socket.util.luckySheetUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhouhang
 */
public class TimeUtil {
	
	public static int getTodayBeginTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return (int) (calendar.getTime().getTime() / 1000);
	}
	
}

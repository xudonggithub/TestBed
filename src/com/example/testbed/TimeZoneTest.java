package com.example.testbed;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneTest {
	public static void test() {
		// Calendar在不手动设置时区时，是与系统默认时区相关的。在手动修改时区后，
		// 不能使用calendar.getTime方法来直接获取Date日期，因为此时的日期与setTime时的值相同，
		//想要正确获取修改时区后的时间，应该通过calendar的get方法
		Date date = new Date(1359641834000L);
		System.out.println(date);//Thu Jan 31 22:17:14 GMT+08:00 2013
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));  
		// 或者可以 Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));  
		calendar.setTime(date);
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));//14:17

		Calendar calendar2 = Calendar.getInstance();  
		calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

		System.out.println(calendar.getTime());  //Thu Jan 31 22:17:14 GMT+08:00 2013
		System.out.println(calendar2.getTime());//Thu Jan 31 14:17:14 GMT+08:00 2013

	}
}

package com.example.testbed;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneTest {
	public static void test() {
		// Calendar�ڲ��ֶ�����ʱ��ʱ������ϵͳĬ��ʱ����صġ����ֶ��޸�ʱ����
		// ����ʹ��calendar.getTime������ֱ�ӻ�ȡDate���ڣ���Ϊ��ʱ��������setTimeʱ��ֵ��ͬ��
		//��Ҫ��ȷ��ȡ�޸�ʱ�����ʱ�䣬Ӧ��ͨ��calendar��get����
		Date date = new Date(1359641834000L);
		System.out.println(date);//Thu Jan 31 22:17:14 GMT+08:00 2013
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));  
		// ���߿��� Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));  
		calendar.setTime(date);
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));//14:17

		Calendar calendar2 = Calendar.getInstance();  
		calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

		System.out.println(calendar.getTime());  //Thu Jan 31 22:17:14 GMT+08:00 2013
		System.out.println(calendar2.getTime());//Thu Jan 31 14:17:14 GMT+08:00 2013

	}
}

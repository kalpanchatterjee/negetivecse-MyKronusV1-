package com.example.demo.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Utilcollection {
	public static String getServerTimeZone(){	
		Calendar localTime = new GregorianCalendar(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
		//System.out.println("=============>"+localTime.get(localTime.ZONE_OFFSET));
		long localOffset = localTime.get(localTime.ZONE_OFFSET); //offset IST (seconds)
		long sec = TimeUnit.MILLISECONDS.toSeconds(localOffset);
		long time = TimeUnit.SECONDS.toMinutes(sec); //or offset/60
		long hour = time / 60;
		long min = Math.abs(time % 60);
		String hrStr = "";
		if (hour > 0 && hour < 10) {
			hrStr = "+0" + String.valueOf(hour);
		} else if (hour >= 10) {
			hrStr = "+" + String.valueOf(hour);
		} else if (hour < 0 && hour > -10) {
			hrStr = "-0" + String.valueOf(hour).substring(1);
		} else if(hour==0) {
			hrStr = "+0";
		} else {
			hrStr = String.valueOf(hour);
		}
		String minStr = String.valueOf(min);
		if (min < 10) {
			minStr = "0" + (time % 60);
		}
		String timeStr = hrStr + ":" + minStr;
		//System.out.println(timeStr);
		return timeStr;
	}
	public static String getlocalTimeInHrs(String lTZ){
		String timeStr="";
		try{
			long check =Long.parseLong(lTZ);
			long time = TimeUnit.SECONDS.toMinutes(check); //or offset/60
			long hour = time / 60;
			long min = Math.abs(time % 60);
			String hrStr = "";
			if (hour > 0 && hour < 10) {
				hrStr = "+0" + String.valueOf(hour);
			} else if (hour >= 10) {
				hrStr = "+" + String.valueOf(hour);
			} else if (hour < 0 && hour > -10) {
				hrStr = "-0" + String.valueOf(hour).substring(1);
			} else {
				hrStr = String.valueOf(hour);
			}
			String minStr = String.valueOf(min);
			if (min < 10) {
				minStr = "0" + (time % 60);
			}
			timeStr = hrStr + ":" + minStr;
		}catch(NumberFormatException e){
			System.out.println("NumberFormatException:"+lTZ);
		}
		// System.out.println("timeStr---->"+timeStr);
		return timeStr;
	}
	public static Date getDate(){
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}
	public static Time getTime(){
		java.util.Date utilDate = new java.util.Date();
		java.sql.Time sqlTime = new java.sql.Time(utilDate.getTime());
		return sqlTime;
	}
	public static Timestamp getTimeStamp(){
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(utilDate.getTime());
		return sqlTimeStamp;
	}
}

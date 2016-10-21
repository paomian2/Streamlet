package com.streamlet.common.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 格式化时间 如：12小时前
	 * 
	 * @param timestr
	 *            秒
	 */
	public static String fmttoCN(String timestr) {
		String timeText = null;
		if (null == timestr || "".equals(timestr)) {
			return "";
		}

		long time = Long.valueOf(timestr);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		// try {
		// time = dateFormat.parse(timestr).getTime();
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		Date dt = new Date();
		long nowSec = dt.getTime();
		long timediff = (nowSec - time) / 1000;
		if (timediff < 60) {
			// 小与1分钟显示 ‘刚刚’
			timeText = "刚刚";
		} else if (timediff >= 60 && timediff < 60 * 60) {
			// 小于1小时 显示‘分钟’
			timeText = String.valueOf((int) timediff / 60) + "分钟前";
		} else if (timediff >= 60 * 60 && timediff < 24 * 60 * 60) {
			// 小于24小时，则显示‘时’
			timeText = String.valueOf((int) timediff / (60 * 60)) + "小时前";
		} else if (timediff >= 24 * 60 * 60 && timediff < 30 * 24 * 60 * 60) {
			// 小于1个月，则显示‘天’
			timeText = String.valueOf((int) timediff / (24 * 60 * 60)) + "天前";
		} else if (timediff >= 30 * 24 * 60 * 60 && timediff < 12 * 30 * 24 * 60 * 60) {
			// 小于1年，则显示‘月’
			timeText = String.valueOf((int) timediff / (30 * 24 * 60 * 60)) + "个月前";
		} else if (timediff >= 12 * 30 * 24 * 60 * 60) {
			// 大于1年显示‘年’
			timeText = String.valueOf((int) timediff / (12 * 30 * 24 * 60 * 60)) + "年前";
		}

		return timeText;

	}

	//	public static String timeToStr(String timestr) {
	//		String timeText = null;
	//		if (null == timestr || "".equals(timestr)) {
	//			return "";
	//		}
	//		long time = Long.valueOf(timestr);
	//		if(isSameDay(time)){
	//			timeText = "今天"+longToTime(time, "kk:mm");
	//		}else{
	//			
	//		}
	//		Date dt = new Date();
	//		long nowSec = dt.getTime();
	//		long timediff = (nowSec - time) / 1000;
	//		if (timediff < 24 * 60 * 60) {
	//			timeText = "今天"+longToTime(time, "kk:mm");
	//		} else if (timediff >= 24 * 60 * 60 && timediff < 2 * 24 * 60 * 60) {
	//			timeText = "昨天"+longToTime(time, "kk:mm");
	//		} else{
	//			timeText = longToTime(time, "yyyy-MM-dd kk:mm");
	//		}
	//		return timeText;
	//	}

	/**
	 * @author LuoB.
	 * @param oldTime 较小的时间
	 * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比)
	 * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天.
	 * @throws ParseException 转换异常
	 */
	public static String timeToStr(String oldTime){


		try {
			Date today;
			//将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = format.format(new Date());
			today = format.parse(todayStr);
			long time = Long.valueOf(oldTime);
			Date olddate  =  new Date(time);
			//昨天 86400000=24*60*60*1000 一天
			if((today.getTime()-olddate.getTime())>0 && (today.getTime()-olddate.getTime())<=86400000) {
				return "昨天"+longToTime(time, "HH:mm");
			}
			else if((today.getTime()-olddate.getTime())<=0){ //至少是今天
				return "今天"+longToTime(time, "HH:mm");
			}
			else{ //至少是前天
				return longToTime(time, "yyyy-MM-dd HH:mm");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}


	/**
	 * @param time
	 *            时间
	 * @param level
	 *            参考Calendar
	 * @return "yyyy-MM-dd kk:mm:ss" 格式的时间
	 */
	public static String longToTime(long time, int level) {
		String format = "yyyy-MM-dd HH:mm:ss";
		switch (level) {
		case Calendar.MINUTE: {
			format = "yyyy-MM-dd HH:mm";
		}
		break;
		case Calendar.HOUR: {
			format = "yyyy-MM-dd HH";
		}
		break;
		case Calendar.DATE: {
			format = "yyyy-MM-dd";
		}
		break;
		case Calendar.MONTH: {
			format = "yyyy-MM";
		}
		break;
		case Calendar.YEAR: {
			format = "yyyy";
		}
		break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	public static String longToTime(long time, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	/** 时间转换为long */
	public static long timeToSecond(String time, int level) {
		String format = "yyyy-MM-dd HH:mm:ss";
		switch (level) {
		case Calendar.MINUTE: {
			format = "yyyy-MM-dd HH:mm";
		}
		break;
		case Calendar.HOUR: {
			format = "yyyy-MM-dd HH";
		}
		break;
		case Calendar.DATE: {
			format = "yyyy-MM-dd";
		}
		break;
		case Calendar.MONTH: {
			format = "yyyy-MM";
		}
		break;
		case Calendar.YEAR: {
			format = "yyyy";
		}
		break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		long second = 0;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			second = date.getTime();
		}
		return second;
	}


	/** 时间转换为long */
	public static long timeToSecond2(String time, int level) {
		String format = "yyyy年MM月dd日HH时:mm分:ss秒";
		switch (level) {
		case Calendar.MINUTE: {
			format = "yyyy年MM月dd日HH时mm分";
		}
		break;
		case Calendar.HOUR: {
			format = "yyyy年MM月dd日HH时";
		}
		break;
		case Calendar.DATE: {
			format = "yyyy年MM月dd日";
		}
		break;
		case Calendar.MONTH: {
			format = "yyyy年MM月";
		}
		break;
		case Calendar.YEAR: {
			format = "yyyy年";
		}
		break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		long second = 0;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			second = date.getTime();
		}
		return second;
	}
	
	/** 时间转换为long */
	public static long timeToSecond3(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		long second = 0;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			second = date.getTime();
		}
		return second;
	}

	public static String longToTime2(long time) {
		String format = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}



	/**
	 * 将秒转换为时间格式（00：00）
	 * 
	 * @param s
	 *            秒数
	 * @return
	 */
	public static String secToTime(int s) {
		String time = null;

		int m = s / 60; // 分
		s = s - (m * 60); // 秒

		time = String.format("%02d:%02d", m, s);

		return time;
	}

	// date
	public static String getMonth(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("M");
		formatter.format(date);
		return formatter.format(date);
	}

	// date
	public static String getDay(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("d");
		formatter.format(date);
		return formatter.format(date);
	}

	// date
	public static String getWeek(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
		formatter.format(date);
		return formatter.format(date);
	}

	public static String[] getDatas() {
		String[] datas = new String[60];
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 EEEE");
		datas[0] = format.format(calendar.getTime());
		for (int i = 1; i < 60; i++) {
			calendar.add(Calendar.DATE, 1);
			datas[i] = format.format(calendar.getTime());
		}

		return datas;

	}

	public static String formatAmPmToCN(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日ahh时mm分");
		return sdf.format(new Date(time));
	}
	public static String formatAmPmToCN2(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(new Date(time));
	}
	public static String formatAllAmPmToCN(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 a hh时mm分ss秒");
		return sdf.format(new Date(time));
	}


	/**今天时间*/
	public static String todayTime(String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 字符串转换成日期
	 * @param str:time
	 * @param pattern:格式
	 * @return date：date
	 */
	public static Date StrToDate(String str, String pattern) {

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**格式化时间*/
	public static String fmTime(Date date, String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**比较两个时间那个前，哪个后
	 * 
	 * @return 
	 *         1:DATE1在DATE2之前
	 *        -1:DATE1在DATE2之后
	 *         0:DATE1、DATE2同一时间
	 * 
	 * */
	public static int compare_date(String DATE1, String DATE2, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 判断当前时间是否在上午9点到下午9点内
	 * @return
     */
	public static boolean compareIn9(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		try {
			String today = todayTime("yyyy-MM-dd");
			Date dt1 = df.parse(today+" 09:00:00");
			Date dt2 = df.parse(today+" 20:00:00");
			Date now = new Date();
			if (now.getTime() >= dt1.getTime() && now.getTime() <=dt2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
	
	public static String hasTimeNow(long time){
		long today = new Date().getTime();
		long mis = today - time;
		
		int hour = (int)(mis/(3600*1000));
		int mu = (int) (mis/(60*1000));
		return hour>0?hour+"小时":mu+"分钟";		
	}
}

package cn.ccsit.common.utils;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateUtils {
	
	private static Logger logger = LogManager.getLogger(DateUtils.class);
	
	/**
	 *  获取当前系统日期（本地时间）
	 * @return 返回日期
	 */
	public static Date getCurrentDate() {
		
		Date date=getCurrentDateTime();		
		return datetimeToDate(date);
	}
	public static Date getCurrentDateUTC() {
		Date date=getCurrentDateTimeUTC();
		return datetimeToDate(date);
	}
	
	
	/**
	 *  获取当前系统时间（本地时间）
	 * @return 返回日期及时间
	 */
	public static Date getCurrentDateTime() {		
		Calendar calendar=Calendar.getInstance();
		return calendar.getTime();
	}
	
	/**
	 * 获取当前系统时间（UTC）
	 */
	public static Date getCurrentDateTimeUTC() {
		Calendar calendar = Calendar.getInstance();  	
		int offset = calendar.get(Calendar.ZONE_OFFSET); 	
		int dstoff = calendar.get(Calendar.DST_OFFSET);
		calendar.add(Calendar.MILLISECOND, - (offset + dstoff));
		return calendar.getTime();	
	}
	
	/**
	 *  获取字符串格式的当前系统日期（本地时间）
	 * @return 返回日期，格式为yyyy-MM-dd 
	 */
	public static String formatCurrentDateToShortLineDate() {		
		Date date=getCurrentDateTime();
		return formatDateToShortLineDate(date);
	}
	
	/**
	 *  获取字符串格式的当前系统日期（UTC）
	 * @return 返回日期，格式为yyyy-MM-dd 
	 */
	public static String formatCurrentDateToShortLineDateUTC() {		
		Date date=getCurrentDateTimeUTC();
		return formatDateToShortLineDate(date);
	}
	/**
	 *  获取字符串格式的当前系统日期及时间（本地时间）
	 * @return 返回日期，格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String formatCurrentDateToLongDate() {
		
		Date date=getCurrentDateTime();
		return formatDateToLongDate(date);
	}
	
	/**
	 *  获取字符串格式的当前系统日期及时间（UTC）
	 * @return 返回日期，格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String formatCurrentDateToLongDateUTC() {
		
		Date date=getCurrentDateTimeUTC();
		return formatDateToLongDate(date);
	}
	
	/**
	 *  获取字符串格式的当前系统时间（本地时间）
	 * @return 返回日期，格式为HH:mm:ss
	 */
	public static String formatCurrentDateToTime() {
		
		Date date=getCurrentDateTime();
		return formatDateToTime(date);
	}
	
	/**
	 *  获取字符串格式的当前系统时间（本地时间）
	 * @return 返回日期，格式为HH:mm:ss
	 */
	public static String formatCurrentDateToTimeUTC() {
		
		Date date=getCurrentDateTimeUTC();
		return formatDateToTime(date);
	}
	
	/**
	 *  获取字符串格式的当前系统日期（本地时间）
	 * @return 返回日期，格式为yyyy年MM月dd日
	 */
	public static String formatCurrentDateToChineseDate() {
		
		Date date=getCurrentDateTime();
		return formatDateToChineseDate(date);
	}
	
	/**
	 *  获取字符串格式的当前系统日期（UTC）
	 * @return 返回日期，格式为yyyy年MM月dd日
	 */
	public static String formatCurrentDateToChineseDateUTC() {
		
		Date date=getCurrentDateTimeUTC();
		return formatDateToChineseDate(date);
	}
	
	/**
	 *  获取字符串格式的当前系统时间（本地时间）
	 * @return 返回日期，yyyy年MM月dd日 HH时mm分ss秒
	 */
	public static String formatCurrentDateToChineseDateTime() {
		
		Date date=getCurrentDateTime();
		return formatDateToChineseDateTime(date);
	}
	
	/**
	 *  获取字符串格式的当前系统时间（UTC）
	 * @return 返回日期，yyyy年MM月dd日 HH时mm分ss秒
	 */
	public static String formatCurrentDateToChineseDateTimeUTC() {
		
		Date date=getCurrentDateTimeUTC();
		return formatDateToChineseDateTime(date);
	}
	
	/**
	 *  获取字符串格式的当前系统时间的4位年份（本地时间）
	 * @return 返回4位年
	 */
	public static String formatCurrentDateToYear() {
		
		Date date=getCurrentDateTime();
		return formatDate(date,"yyyy");
	}
	
	/**
	 *  获取字符串格式的当前系统时间的4位年份（UTC）
	 * @return 返回4位年
	 */
	public static String formatCurrentDateToYearUTC() {
		
		Date date=getCurrentDateTimeUTC();
		return formatDate(date,"yyyy");
	}
	
	/**
	 *  获取字符串格式的当前系统时间的2位年份（本地时间）
	 * @return 返回2位年
	 */
	public static String formatCurrentDateToYear2() {
		
		Date date=getCurrentDateTime();
		return formatDate(date,"yy");
	}
	
	/**
	 *  获取字符串格式的当前系统时间的2位年份（UTC）
	 * @return 返回2位年
	 */
	public static String formatCurrentDateToYear2UTC() {
		
		Date date=getCurrentDateTimeUTC();
		return formatDate(date,"yy");
	}
	
	/**
	 *  获取当前系统时间的2位月份（本地时间）
	 * @return 返回2位月
	 */
	public static String formatCurrentDateToMonth() {		
		Date date=getCurrentDateTime();
		return formatDate(date,"MM");
	}
	
	/**
	 *  获取当前系统时间的2位月份（UTC）
	 * @return 返回2位月
	 */
	public static String formatCurrentDateToMonthUTC() {		
		Date date=getCurrentDateTimeUTC();
		return formatDate(date,"MM");
	}
	
	
	/**
	 *  获取当前系统时间的2位天（本地时间）
	 * @return 返回2位天
	 */
	public static String formatCurrentDateToDay() {		
		Date date=getCurrentDateTime();
		return formatDate(date,"dd");
	}
	
	/**
	 *  获取当前系统时间的2位天（UTC）
	 * @return 返回2位天
	 */
	public static String formatCurrentDateToDayUTC() {		
		Date date=getCurrentDateTime();
		return formatDate(date,"dd");
	}
	
	/**
	 * 获取当前系统时间的yyyy年MM月（本地时间）
	 * @return
	 */
	public static String formatCurrentDateToChineseYearMonth(){
		Date date=getCurrentDateTime();
		return formatDate(date,"yyyy年MM月");
	}
	
	/**
	 * 获取当前系统时间的yyyy年MM月（UTC）
	 * @return
	 */
	public static String formatCurrentDateToChineseYearMonthUTC(){
		Date date=getCurrentDateTimeUTC();
		return formatDate(date,"yyyy年MM月");
	}
	
	/**
	 * 返回指定日期的年或月或日,调用此方法返回月份时，必须加1，因为月是从0开始   
	 * @param date 日期
	 * @param field  Calendar.YEAR 或 Calendar.Month 或 Calendar.DAY_OF_MONTH
	 * @return
	 */
	private static int getYearOrMonthOrDay(Date date,int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field) ;
	}
	
	/**
	 * 获取当前日期的年（本地时间）
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		int imonth = calendar.get(Calendar.YEAR) ;
		return imonth;
	}
	
	/**
	 * 获取当前日期的年（UTC）
	 * @return
	 */
	public static int getCurrentYearUTC() {
		Calendar calendar = Calendar.getInstance();
		Date date=getCurrentDateTimeUTC();
		calendar.setTime(date);
		int imonth = calendar.get(Calendar.YEAR) ;
		return imonth;
	}
	
	/**
	 * 获取当前日期的月份（本地时间）
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		int imonth = calendar.get(Calendar.MONTH)+1 ;
		return imonth;
	}

	/**
	 * 获取当前日期的月份（UTC）
	 * @return
	 */
	public static int getCurrentMonthUTC() {
		Calendar calendar = Calendar.getInstance();
		Date date=getCurrentDateTimeUTC();
		calendar.setTime(date);
		int imonth = calendar.get(Calendar.MONTH)+1 ;
		return imonth;
	}
	
	/**
	 * 获取当前日期为当月的第几天（本地时间）
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		int idate = calendar.get(Calendar.DAY_OF_MONTH);
		return idate;
	}
	
	/**
	 * 获取当前日期为当月的第几天（UTC）
	 * @return
	 */
	public static int getCurrentDayUTC() {
		Calendar calendar = Calendar.getInstance();
		Date date=getCurrentDateTimeUTC();
		calendar.setTime(date);
		int idate = calendar.get(Calendar.DAY_OF_MONTH);
		return idate;
	}
	
	/**
	 * 获取指定日期的年份
	 * @return
	 */
	public static int getYearOfDate(Date date) {	
		return getYearOrMonthOrDay(date,Calendar.YEAR);
	}
	
	/**
	 * 获取指定日期的当年的第一天
	 * @param date 日期
	 * @return 返回不带时间的日期
	 */
	public static Date getFirstYearDate(Date date) {	
		int year=getYearOrMonthOrDay(date,Calendar.YEAR);
		return parse(String.valueOf(year)+"-01-01","yyyy-MM-dd");
	}
	
	/**
	 * 获取指定日期的当年的最后一天
	 * @param date 日期
	 * @return 返回不带时间的日期
	 */
	public static Date getLastYearDate(Date date) {	
		int year=getYearOrMonthOrDay(date,Calendar.YEAR);
		return parse(String.valueOf(year)+"-12-31","yyyy-MM-dd");
	}
	
	/**
	 * 获取指定日期的前或后指定间隔年的日期
	 * @param date 指定日期
	 * @param n 间隔年数，负数表示返回小于指定日期的日期，正数表示返回大于指定日期的日期
	 * @return 返回计算后的日期
	 */
	public Date getDateOfYears(Date date,int n){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, n);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期的月份 
	 * @return 
	 */
	public static int getMonthOfDate(Date date) {
		return getYearOrMonthOrDay(date,Calendar.MONTH)+1; //注（月份从0开始)
	}

	/**
	 * 获取指定日期的当前月的第一天
	 * @param date 日期
	 * @return 返回不带时间的日期
	 */
	public static Date getFirstMonthDate(Date date) {
		String yearMonth=formatDateToShortLineYearMonth(date);
		return parse(yearMonth+"-01","yyyy-MM-dd");
	}
	
	/**
	 * 获取指定日期的当前月的最后一天,不含时间
	 * @param date 日期
	 * @return 返回不带时间的日期
	 */
	public static Date getLastMonthDate(Date date) {
		Date firstDate=getFirstMonthDate(date);
		Date nextMonthFirstDate=getDateAddMonths(firstDate,1);
		return getDateAddDays(nextMonthFirstDate,-1);
	}
	
	/**
	 * 获取指定日期的前或后指定间隔月数的日期
	 * @param date 指定日期
	 * @param n 间隔月数，负数表示返回小于指定日期的日期，正数表示返回大于指定日期的日期
	 * @return 返回计算后的日期
	 */
	public static Date getDateAddMonths(Date date,int n){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);
		return calendar.getTime();
	}
	
	
	/**
	 * 获取指定日期为当月的第几天
	 * @return
	 */
	public static int getDayOfDate(Date date) {
		return getYearOrMonthOrDay(date,Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取指定日期的前或后指定间隔天数的日期
	 * @param date 指定日期
	 * @param n 间隔天数，负数表示返回小于指定日期的日期，正数表示返回大于指定日期的日期
	 * @return 返回计算后的日期
	 */
	public static Date getDateAddDays(Date date,int n){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);
		return calendar.getTime();
	}
	
	
	/**
	 * 将带时间的日期转换为不带时间的日期 
	 * @param datetime  带时间的日期
	 * @return 不带时间的日期
	 */
	public static Date  datetimeToDate(Date datetime) {
		try {
			DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
			String source=dfmt.format(datetime);		
			SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(source);
		}  catch (Exception ex) {
			logger.error("字符串转换为日期错误,错误信息:"+ex.getMessage());
			throw new RuntimeException("日期转换错误");
		}		
	}
	
	/**
	 *  格式化日期为字符串
	 * @param format  日期格式
	 * @param Locale  表示地区
	 * @return 返回时间字符串
	 */
	public static String formatDate(Date date,String format,Locale locale) {		
		try {
			if (locale==null) {
				locale=Locale.getDefault();
			}
			DateFormat dfmt = new SimpleDateFormat(format,locale);
			return dfmt.format(date);
		} catch (Exception ex) {
			logger.error("日期转化错误,错误信息:" + ex.getMessage());
			throw new RuntimeException("转换日期错误");
		}
	}

	/**
	 *  格式化日期为字符串
	 * @param format  日期格式
	 * @return 返回时间字符串
	 */
	public static String formatDate(Date date,String format) {		
		return formatDate(date,format,null);
	}
	
	/**
	 * 日期格式化成yyyy-MM-dd HH:mm:ss格式
	 * @param date
	 * @return
	 */
	public static String formatDateToLongDate(Date date) {		
		return formatDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 日期格式化成yyyy年MM月dd日 的格式
	 * @param date
	 * @return
	 */
	public static String formatDateToChineseDate(Date date) {		
		return formatDate(date,"yyyy年MM月dd日");
	}
	
	/**
	 * 日期格式化成yyyy年MM月dd日 HH时mm分ss秒的格式
	 * @param date 
	 * @return
	 */
	public static String formatDateToChineseDateTime(Date date) {		
		return formatDate(date,"yyyy年MM月dd日 HH时mm分ss秒");
	}
	
	/**
	 * 日期格式化成MMM,dd yyyy 格式的字符串
	 * @param date
	 * @return
	 */
	public static String formatDateToEnglishDate(Date date) {		
		
		return formatDate(date,"MMM dd,yyyy",Locale.ENGLISH);
	}
	/**
	 * 日期格式化成MMM,dd yyyy HH:mm:ss格式的字符串
	 * @param date 日期
	 * @return
	 */
	public static String formatDateToEnglishDateTime(Date date) {		
		return formatDate(date,"MMM dd,yyyy HH:mm:ss",Locale.ENGLISH);
	}
	
	
	
	/**
	 * 日期格式化成yyyyMMdd格式的字符串
	 * @param date
	 * @return
	 */
	public static String formatDateToShortDate(Date date){		
		return formatDate(date,"yyyyMMdd");
	}
	
	/**
	 * 日期格式化成yyyyMM格式的字符串
	 * @param date 日期
	 * @return
	 */
	public static String formatDateToShortYearMonth(Date date){		
		return formatDate(date,"yyyyMM");
	}
	
	/**
	 * 日期格式化成yyyy-MM格式字符串
	 * @param date
	 * @return
	 */
	public static String formatDateToShortLineYearMonth(Date date){		
		return formatDate(date,"yyyy-MM");
	}
	
	/**
	 * 日期格式化成yyyy年MM月格式的字符串
	 * @param date 指定日期
	 * @return
	 */
	public static String formatDateToChineseYearMonth(Date date){		
		return formatDate(date,"yyyy年MM月");
	}
	
	
	/**
	 * 日期格式化成yyyyMMddHHmmss格式的字符串
	 * @param date 日期
	 * @return
	 */
	public static String formatDateToShortDateTime(Date date){		
		return formatDate(date,"yyyyMMddHHmmss");
	}
	
	/**
	 * 日期格式化成yyyy-MM-dd格式的字符串
	 * @param date 日期
	 * @return
	 */
	public static String formatDateToShortLineDate(Date date){						
			return formatDate(date,"yyyy-MM-dd");	
	}
	
	/**
	 * 日期格式化成HH:mm:ss格式的字符串
	 * @param date 日期
	 * @return
	 */
	public static String formatDateToTime(Date date){						
			return formatDate(date,"HH:mm:ss");	
	}
	
	/**
	 *  获取指定日期的4位年份
	 * @return 返回4位年
	 */
	public static String formatDateToYear(Date date) {
		
		return formatDate(date,"yyyy");
	}
	
	/**
	 *  获取指定日期的2位月份
	 * @return 返回2位月
	 */
	public static String formatDateToMonth(Date date) {		
		return formatDate(date,"MM");
	}
	/**
	 *  获取指定日期的2位天
	 * @return 返回2位天
	 */
	public static String formatDateToDay(Date date) {
		return formatDate(date,"dd");
	}
	
	/**
	 * 获得两个时间差，结果为秒
	 * 
	 * @param d1 开始时间
	 * @param d2 结束时间
	 * @return
	 */
	public static long getDateDiffWithSecond(Date d1, Date d2) {
		try {
			long diff = d1.getTime() - d2.getTime();
			long seconds = diff / 1000;
			return seconds;
		} catch (Exception e) {
			logger.error("计算两个日期间的秒数查错误,错误信息:"+e.getMessage());
			throw new RuntimeException("计算日期秒差错误");
		}
	}
	
	
	
	/**
	 * 获得两个时间差，结果为天 
	 * 
	 * @param d1 开始时间
	 * @param d2 结束时间
	 * @return
	 */
	public static Long getDateDiffWithDay(Date d1, Date d2) {
		try {
			long diff = d1.getTime() - d2.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			return days;
		} catch (Exception e) {
			logger.error("计算两个日期间的天数差错误,错误信息:"+e.getMessage());
			throw new RuntimeException("计算日期差值错误");
		}
	}

	/**
	 * 判断当前时间是否在两个时间之间（本地时间）
	 * @param dt1  带时间的日期或不带时间的日期
	 * @param dt2  带时间的日期或不带时间的日期
	 * @return true 符合,false 不符合
	 */
	public static boolean currentDateTimeBetweenDates(Date dt1,Date dt2) {
		Date date=getCurrentDateTime();
		return dateTimeBetweenDates(date,dt1,dt2);
	}
	
	/**
	 * 判断指定时间是否在两个时间之间
	 * @param dt  带时间的日期或不带时间的日期
	 * @param dt1   带时间的日期或不带时间的日期
	 * @param dt2  带时间的日期或不带时间的日期
	 * @return true 符合 ,false 不符合
	 */
	public static boolean dateTimeBetweenDates(Date dt,Date dt1,Date dt2) {
		return (dt.compareTo(dt1)>=0 & dt.compareTo(dt2)<=0);
	}
	
	/**
	 * 比较开始时间是否早于或等于结束时间，,早于返回true,不早于返回false 
	 * @param 带时间的日期或不带时间的日期
	 * @param 带时间的日期或不带时间的日期
	 * @return 该算法是将日期转换为long 型数据进行比较，带毫秒的日期会影响比较结果
	 */
	public static boolean compareDate(Date dt1, Date dt2) {
		long ld1=dt1.getTime();
		long ld2=dt2.getTime();
		return (ld1<=ld2);
	}
	

	/**
	 * 获取指定日期所在年份的前或后指定间隔的年份列表
	 * @param date
	 * @param n 间隔数，不能大于50或小于50
	 * @return 返回年份列表，从小到大排序
	 */
	public List<Integer> getYearsOfDate(Date date,int n){
		List<Integer> years=new ArrayList<Integer>();
		if (n<-50 ||n>50) {
			throw new RuntimeException("时间间隔不能大于或小于50");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iYear=calendar.get(Calendar.YEAR);
		int iStart=iYear;
		int iEnd=iYear;
		if (n>0) {
			iEnd=iYear+n;
		}else {
			iStart=iYear+n;
		}
		for(int i=iStart;i<iEnd;i++){
			years.add(Integer.valueOf(i));
		}
		return years;
	}
	
	
	/**
	 * 字符串转换为日期
	 * @param data
	 * @return
	 */
	private static Date parse(String date,String format) {
		if (StringUtils.isNullOrEmpty(date)) {
			throw new RuntimeException("输入日期不能为空");
		}
		if (StringUtils.isNullOrEmpty(format)) {
			throw new RuntimeException("输入日期格式定义不能为空");
		}
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.parse(date);
		} catch (Exception ex) {
			logger.error("字符串转换为日期错误,错误信息:" + ex.getMessage());
			throw new RuntimeException("字符串转换日期错误");
		}
	}

	/**
	 * 将yyyy年MM月dd日格式字符串转换为日期
	 * @param data
	 * @return
	 */
	public static Date parseChineseDateFormat(String date) {
		
		return parse(date,"yyyy年MM月dd日");
	}
	
	/**
	 * 将yyyy年MM月dd日 HH:mm:ss格式字符串转换为日期
	 * @param data
	 * @return
	 */
	public static Date parseChineseDateTimeFormat(String date) {
		
		return parse(date,"yyyy年MM月dd日 HH时mm分ss秒");
	}
	
	/**
	 * 将yyyyMMdd格式字符串转换为日期
	 * @param data
	 * @return
	 */
	public static Date parseShortDateFormat(String date) {
		
		return parse(date,"yyyyMMdd");
	}
	
	/**
	 * 将yyyyMMddHHmmss格式字符串转换为日期
	 * @param data
	 * @return
	 */
	public static Date parseShortDateTimeFormat(String date) {
		return parse(date,"yyyyMMddHHmmss");
	}
	/**
	 * 将yyyy-MM-dd格式字符串转换为日期
	 * @param data 日期字符串
	 * @return
	 */
	public static Date parseShortLineDateFormat(String date) {
		
		return parse(date,"yyyy-MM-dd");
	}
	/**
	 * 将yyyyMMddHHmmss格式字符串转换为日期
	 * @param data 日期及时间字符串
	 * @return
	 */
	public static Date parseLongDateTimeFormat(String date) {
		
		return parse(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 在一个日期上增加或减少天数
	 * @param time 输入的时间
	 * @param n 与输入时间相差的天数,负数返回比输入时间小的日期，正数返回比输入时间大的日期
	 * @param withTime  true 表示不需要格式化输入的时间，返回日期和时间，false 表示需要对输入时间进行格式化，返回日期，不带时间
	 * @return
	 */
	public static Date appendDate(Date time, int n,boolean withTime) {
		Calendar calendar=Calendar.getInstance();		
		if (withTime) {
			calendar.setTime(time);
		} else {
			try {
				DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
				String source=dfmt.format(time);		
				SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
				calendar.setTime(format.parse(source));
			} catch (ParseException e) {
				throw new RuntimeException("日期转换错误");
			}
		}		
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return calendar.getTime();
	}

	/**
	 * 在一个日期上增加或减少毫秒数 TODO 
	 * @param time 输入的时间
	 * @param amount 与输入时间相差的秒数，负数返回比输入时间小的时间，正数返回比输入时间大的时间	 
	 * @return
	 */
	public static Date appendTime(Date time, int amount) {
		Calendar calendar=Calendar.getInstance();		
		calendar.setTime(time);	
		calendar.add(Calendar.MILLISECOND, amount);
		return calendar.getTime();
	}
	
	/**
	 * 毫秒转日期
	 * @param milliSecond
	 * @return
	 */
	public static Date formatDate(long milliSecond) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
		return calendar.getTime();
	}

	/**
	 * 本地时间转UTC时间
	 * @param localDate
	 * @return
	 */
	public static Date localToUTC(Date localDate,BigDecimal timeZone) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(localDate);
		BigDecimal multiply = timeZone.multiply(BigDecimal.valueOf(60));
		int minutes = multiply.intValue();
		instance.add(Calendar.MINUTE,-minutes);
		return instance.getTime();
	}
	public static Date utcToLocal(Date utcDate,BigDecimal timeZone){
		Calendar instance = Calendar.getInstance();
		instance.setTime(utcDate);
		BigDecimal multiply = timeZone.multiply(BigDecimal.valueOf(60));
		int minutes = multiply.intValue();
		instance.add(Calendar.MINUTE,minutes);
		return instance.getTime();
	}
	public static void main(String args[]) {
		Date date = localToUTC(new Date(), BigDecimal.valueOf(8.0));
		System.out.println(date);

	}

	
}

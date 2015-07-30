package com.fa.yapp;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import 	android.text.format.DateUtils;

public class Parameter 
	{
	private double lat;
	private double lon;
	private boolean st;
	private Locale locale;
	private TimeZone timeZone;
	Calendar date ;
	
	
	public void init(){
		timeZone = TimeZone.getDefault();
		System.out.print(timeZone+" in Params");
		st = timeZone.inDaylightTime( new Date() );
		date = new GregorianCalendar();
		lat = 48.137222222222;
		lon = 11.575277777778;
		//NewYork
		lat = 40.712778;
		lon = -74.005833;
	}
	
	//Setter
	public void setTs(long v) {
		date.setTimeInMillis(v);
	}
	
	public void setLat(double v) {
		lat = v;
	}
	public void setLong(double v) {
		lon = v;
	}
	public void setYear(int v) {
		date.set(Calendar.YEAR, v);
	}
	public void setMonth(int v) {
		date.set(Calendar.MONTH, v);
	}
	public void setDay(int v) {
		date.set(Calendar.DAY_OF_MONTH, v);
	}
	public void setHour(int v) {
		date.set(Calendar.HOUR_OF_DAY, v);
	}
	public void setMinute(int v) {
		date.set(Calendar.MINUTE, v);
	}
	public void setSec(int v) {
		date.set(Calendar.SECOND, v);
	}
	public void setTimezone(String ZoneId) {
		date.setTimeZone(TimeZone.getTimeZone(ZoneId));
	}
	public void setSummertime(boolean v) {
		st = v;
	}
	
	//Getter
	public long getTs(){
		return date.getTimeInMillis();
	}
	public double getLat() {
		return lat;
	}
	public double getLong() {
		return lon;
	}
	public int getYear() {
		return date.get(Calendar.YEAR);
	}
	
	public int getMonth() {
		return date.get(Calendar.MONTH);
	}
	public int getDay() {
		return date.get(Calendar.DAY_OF_MONTH);
	}
	public int getHour() {
		return date.get(Calendar.HOUR_OF_DAY);
	}	
	public int getMinute() {
		return date.get(Calendar.MINUTE);
	}
	public int getSec() {
		return date.get(Calendar.SECOND);
	}
	
	public double getTimezone() {
		return date.get(Calendar.ZONE_OFFSET)  + 3600 * 1000;
	}
	public boolean getSummertime() {		
		return timeZone.inDaylightTime( new Date() );
	}
	
	public String getDateString(){	
		
		return  DateUtils.formatDateTime(MainActivity.getAppContext(), date.getTimeInMillis(), 
				 DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);	
				
	}	
	
	public String getTimeString(){		
		Formatter formatter = DateUtils.formatDateRange(MainActivity.getAppContext(), new Formatter(), this.getTs(), this.getTs(), 
				DateUtils.FORMAT_SHOW_TIME, timeZone.getID());
		return formatter.toString();
	}
	
	public String debug(){
		System.out.print("\n h: "+getHour()+" m: "+getMinute()+" "+getSec()+" -- d:" +getDay()+" mon: "+getMonth()+" Y "+getYear()+" länge: "+getLong()+" breite: "+getLat()+" Zeitzone: "+getTimezone()+" Sommerzeitt: "+getSummertime()+"\n");
		return "\n h: "+getHour()+" m: "+getMinute()+" "+getSec()+" -- d:" +getDay()+" mon: "+getMonth()+" Y "+getYear()+" länge: "+getLong()+" breite: "+getLat()+" Zeitzone: "+getTimezone()+" Sommerzeit: "+getSummertime()+"\n";
	}

}

package wys.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WysDateConversioHelper {

	public static String getStringFormatedBeginDate(long value)
	{
		Date date1 = new Date(value);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String dateSTring = sdf.format(date1);
		return dateSTring;
	}
	
	public static String getStringFormatedEndDate(long value){
		Date date1 = new Date(value);
		Calendar c = Calendar.getInstance(); 
		c.setTime(date1); 
		c.add(Calendar.HOUR, 24);
		date1 = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String dateSTring = sdf.format(date1);
		return dateSTring;
	}
	
	
}

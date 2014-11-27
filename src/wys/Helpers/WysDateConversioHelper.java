package wys.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WysDateConversioHelper {

	public static String getStringFormatedDate(long value)
	{
		Date date1 = new Date(value);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String dateSTring = sdf.format(date1);
		return dateSTring;
	}
	
	
	
}

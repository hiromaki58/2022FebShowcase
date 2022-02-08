package jp.co.warehouse.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Change the date format
 */
public class DateFormatChanger {

	//Change the format from date to "yyyy-MM-dd"
	public String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);

		return strDate;
	}
}

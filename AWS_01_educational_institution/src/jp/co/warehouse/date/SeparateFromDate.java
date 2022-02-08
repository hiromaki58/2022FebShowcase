package jp.co.warehouse.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *This class provides the function to pick up one of year, month or day from date format data
 */
public class SeparateFromDate {

	//Pick up the year
	public String SeparateYear(Date date) {
		SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
		String year = ysdf.format(date);

		return year;
	}

	//Pick up the month
	public String SeparateMonth(Date date) {
		SimpleDateFormat msdf = new SimpleDateFormat("MM");
		String month = msdf.format(date);

		return month;
	}

	//Pick up the day
	public String SeparateDay(Date date) {
		SimpleDateFormat dsdf = new SimpleDateFormat("dd");
		String day = dsdf.format(date);

		return day;
	}
}
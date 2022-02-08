package jp.co.warehouse.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/*
 * This class basically is handling something related to Date.
 */
public class CompareDate {

	/*
	 *This method determines the article should be in public or not.
	 *If the present time is between the release period, the article will be shown in the public page.
	 */
	public boolean checkPublicity(Date openingDate, Date closingDate) {
		boolean result;
		boolean before;
		boolean after;

		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second

		/*
		 *Prepare to compare the present time, the first day of the article in public and the last
		 *day of the article in public.
		 *
		 *Comparison is happened in Calendar.
		 */
		Calendar calendarOpeningDate = Calendar.getInstance();
		Calendar calendarClosingDate = Calendar.getInstance();
		calendarOpeningDate.setTime(openingDate);
		calendarClosingDate.setTime(closingDate);

		/*
		 *"-1" means even the present day is same as the first day of the article in public,
		 *still make the article be shown in the public.
		 */
		calendarOpeningDate.add(Calendar.DATE, -1);
		calendarClosingDate.add(Calendar.DATE, 1);
		Date subtractedOpeningDate = calendarOpeningDate.getTime();
		Date addedClosingDate = calendarClosingDate.getTime();

		//Check today is after the first day when the article is in public.
		if(truncToday.after(subtractedOpeningDate)) {
			after = true;
		}
		else {
			after = false;
		}
		//Check today is before the last day when the article is in public.
		if(truncToday.before(addedClosingDate)) {
			before = true;
		}
		else {
			before = false;
		}
		//Determine today is between after the first day and before the last.
		if(before && after) {
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}

	/*
	 * If the article schedule is selected as a open schedule, "2100-12-31" is assigned.
	 * "2100-12-31" is the benchmark to tell the article is open schedule.
	 */
	public boolean checkFixedDay(Date DateFromDB) {
		boolean result;

		//If the date is 2100-12-31 it means the start date is not specified
		String benchMarkDay = "2100-12-31";
		Date cdDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cdDate = sdf.parse(benchMarkDay);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		//If it is true, the article start day is not specified.
		if(cdDate.equals(DateFromDB)) {
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}

	/*
	 * Determine today is before the day of the article is in public or not
	 *
	 * If true today is "after" the opening day, and the article should be in public.
	 * On the other hand "false" means the article is not released yet.
	 */
	public boolean checkBefore(Date openingDate) {
		boolean after;

		//Get today
		Date today = new Date();
		//Roll down the mill second
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);

		/*
		 *Prepare to compare the present time, the first day of the article in public and the last
		 *day of the article in public.
		 *
		 *Comparison is happened in Calendar.
		 */
		Calendar calendarOpeningDate = Calendar.getInstance();
		calendarOpeningDate.setTime(openingDate);

		/*
		 *"-1" means even the present day is same as the first day of the article in public,
		 *still make the article be shown in the public.
		 */
		calendarOpeningDate.add(Calendar.DATE, -1);
		Date subtractedOpeningDate = calendarOpeningDate.getTime();

		//Check today is after the first day when the article is in public.
		if(truncToday.after(subtractedOpeningDate)) {
			after = true;
		}
		else {
			after = false;
		}
		return after;
	}

	/*
	 * Determine today is after the day of the article is in public or not
	 *
	 * If true today is still "before" the article closing day, and the article should be in public.
	 * On the other hand "false" means the article is already beyond the day of application dead line.
	 * Shouldn't be in public.
	 */
	public boolean checkAfter(Date closingDate) {
		boolean before;

		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second

		/*
		 *Prepare to compare the present time, the first day of the article in public and the last
		 *day of the article in public.
		 *
		 *Comparison is happened in Calendar.
		 */
		Calendar calendarClosingDate = Calendar.getInstance();
		calendarClosingDate.setTime(closingDate);

		/*
		 * Plus one day to the bench mark date so that it is enable even today is the closing day still
		 *  the article is in public.
		 */
		calendarClosingDate.add(Calendar.DATE, 1);
		Date addedClosingDate = calendarClosingDate.getTime();


		//Check today is before the last day when the article is in public.
		if(truncToday.before(addedClosingDate)) {
			before = true;
		}
		else {
			before = false;
		}
		return before;
	}
}
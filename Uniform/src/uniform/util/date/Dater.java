package uniform.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Dater {
	
	public static List<Date> getMonthListBetweenDates(Date first, Date last) {
		
		List<Date> dateList = new ArrayList<Date>();
		
		if (first == null || last == null) {
			return dateList;
		}
		
		GregorianCalendar calendarForFirst = new GregorianCalendar();
		calendarForFirst.setTime(first);
		GregorianCalendar calendarForLast = new GregorianCalendar();
		calendarForLast.setTime(last);
		
		if (calendarForFirst.get(Calendar.YEAR) == calendarForLast.get(Calendar.YEAR)) {
			for (int month = calendarForFirst.get(Calendar.MONTH); month <= calendarForLast.get(Calendar.MONTH); month++) {
				dateList.add(getNewDate(month, calendarForFirst.get(Calendar.YEAR)));
			}
		} else {
			for (int year = calendarForFirst.get(Calendar.YEAR); year <= calendarForLast.get(Calendar.YEAR); year++) {
				if (year == calendarForFirst.get(Calendar.YEAR)) {
					for (int month = calendarForFirst.get(Calendar.MONTH); month <= 12; month++) {
						dateList.add(getNewDate(month, year));
					}
				} else if (year == calendarForLast.get(Calendar.YEAR)) {
					for (int month = 1; month <= calendarForLast.get(Calendar.MONTH); month++) {
						dateList.add(getNewDate(month, year));
					}
				} else {
					for (int month = 1; month <= 12; month++) {
						dateList.add(getNewDate(month, year));
					}
				}
			}
		}
		
		return dateList;
	}
	
	private static Date getNewDate(int month, int year) {
		GregorianCalendar newDate = new GregorianCalendar();
		newDate.set(Calendar.YEAR, year);
		newDate.set(Calendar.MONTH, month);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		return newDate.getTime();
	}
	
}

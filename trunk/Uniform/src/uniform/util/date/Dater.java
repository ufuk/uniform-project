package uniform.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Dater {
	
	private final static String[] monthsInTurkish = {
			"Ocak",
			"Þubat",
			"Mart",
			"Nisan",
			"Mayýs",
			"Haziran",
			"Temmuz",
			"Aðustos",
			"Eylül",
			"Ekim",
			"Kasým",
			"Aralýk"
	};
	
	public static String monthInTurkish(Date date) {
		if (date == null) {
			return "";
		}
		return monthsInTurkish[partOfDate(Calendar.MONTH, date)];
	}
	
	public static List<Date> getMonthListBetweenDates(Date first, Date last) {
		
		List<Date> dateList = new ArrayList<Date>();
		
		if (first == null || last == null) {
			return dateList;
		}
		
		if (partOfDate(Calendar.YEAR, first) == partOfDate(Calendar.YEAR, last)) {
			for (int month = partOfDate(Calendar.MONTH, first); month <= partOfDate(Calendar.MONTH, last); month++) {
				dateList.add(startingOfMonth(month, partOfDate(Calendar.YEAR, first)));
			}
		} else {
			for (int year = partOfDate(Calendar.YEAR, first); year <= partOfDate(Calendar.YEAR, last); year++) {
				if (year == partOfDate(Calendar.YEAR, first)) {
					for (int month = partOfDate(Calendar.MONTH, first); month <= 12; month++) {
						dateList.add(startingOfMonth(month, year));
					}
				} else if (year == partOfDate(Calendar.YEAR, last)) {
					for (int month = 1; month <= partOfDate(Calendar.MONTH, last); month++) {
						dateList.add(startingOfMonth(month, year));
					}
				} else {
					for (int month = 1; month <= 12; month++) {
						dateList.add(startingOfMonth(month, year));
					}
				}
			}
		}
		
		return dateList;
	}
	
	public static Date startingOfMonth(int month, int year) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}
	
	public static Date endingOfMonth(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
		gc.set(Calendar.HOUR_OF_DAY, 23);
		gc.set(Calendar.MINUTE, 59);
		gc.set(Calendar.SECOND, 59);
		return gc.getTime();
	}
	
	public static int partOfDate(int part, Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(part);
	}
		
}

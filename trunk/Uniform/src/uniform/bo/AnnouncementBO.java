package uniform.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uniform.dao.AnnouncementDAO;
import uniform.entity.Announcement;
import uniform.util.date.Dater;

public class AnnouncementBO {

	private AnnouncementDAO announcementDAO = new AnnouncementDAO();
	
	public void saveOrUpdate(Announcement announcement) {
		announcementDAO.saveOrUpdate(announcement);
	}
	
	public Long save(Announcement announcement) {
		return announcementDAO.save(announcement);
	}
	
	public void delete(Announcement announcement) {
		announcementDAO.delete(announcement);
	}
	
	public Announcement getById(Long id) {
		return announcementDAO.getById(id);
	}
		
	public List<Announcement> getAll() {
		return announcementDAO.getAll();
	}
	
	public List<Announcement> getAllAndPaginate(int firstResult, int maxResult) {
		return announcementDAO.getAllAndPaginate(firstResult, maxResult);
	}
	
	public List<String> getMonthsAsStringListForArchive() {
		Date firstsPublishedDate = announcementDAO.getFirstsPublishedDate();
		Date lastsPublishedDate = announcementDAO.getLastsPublishedDate();
		
		List<String> months = new ArrayList<String>();
		
		for (Date date : Dater.getMonthListBetweenDates(firstsPublishedDate, lastsPublishedDate)) {
			months.add(Dater.monthInTurkish(date) + " " + Dater.partOfDate(Calendar.YEAR, date));
		}
		
		return months;
	}
	
	public List<Date> getMonthsAsDateListForArchive() {
		Date firstsPublishedDate = announcementDAO.getFirstsPublishedDate();
		Date lastsPublishedDate = announcementDAO.getLastsPublishedDate();
		
		return Dater.getMonthListBetweenDates(firstsPublishedDate, lastsPublishedDate);
	}
	
	public List<Announcement> getAllBetweenDates(Date starting, Date ending) {
		return announcementDAO.getAllBetweenDates(starting, ending);
	}

	public Long getCountBetweenDates(Date starting, Date ending) {
		return announcementDAO.getCountBetweenDates(starting, ending);
	}
	
}

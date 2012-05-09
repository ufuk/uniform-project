package uniform.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uniform.dao.NewsDAO;
import uniform.entity.News;
import uniform.util.date.Dater;

public class NewsBO {

	private NewsDAO newsDAO = new NewsDAO();
	
	public void saveOrUpdate(News news) {
		newsDAO.saveOrUpdate(news);
	}
	
	public Long save(News news) {
		return newsDAO.save(news);
	}
	
	public void delete(News news) {
		newsDAO.delete(news);
	}
	
	public News getById(Long id) {
		return newsDAO.getById(id);
	}
		
	public List<News> getAll() {
		return newsDAO.getAll();
	}
	
	public List<News> getAllAndPaginate(int firstResult, int maxResult) {
		return newsDAO.getAllAndPaginate(firstResult, maxResult);
	}
	
	public List<String> getMonthsAsStringListForArchive() {
		Date firstsPublishedDate = newsDAO.getFirstsPublishedDate();
		Date lastsPublishedDate = newsDAO.getLastsPublishedDate();
		
		List<String> months = new ArrayList<String>();
		
		for (Date date : Dater.getMonthListBetweenDates(firstsPublishedDate, lastsPublishedDate)) {
			months.add(Dater.monthInTurkish(date) + " " + Dater.partOfDate(Calendar.YEAR, date));
		}
		
		return months;
	}
	
	public List<Date> getMonthsAsDateListForArchive() {
		Date firstsPublishedDate = newsDAO.getFirstsPublishedDate();
		Date lastsPublishedDate = newsDAO.getLastsPublishedDate();
		
		return Dater.getMonthListBetweenDates(firstsPublishedDate, lastsPublishedDate);
	}
	
	public List<News> getAllBetweenDates(Date starting, Date ending) {
		return newsDAO.getAllBetweenDates(starting, ending);
	}

	public Long getCountBetweenDates(Date starting, Date ending) {
		return newsDAO.getCountBetweenDates(starting, ending);
	}
	
}

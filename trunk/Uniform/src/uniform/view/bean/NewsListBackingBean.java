package uniform.view.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.NewsBO;
import uniform.entity.News;
import uniform.util.date.Dater;
import uniform.util.paginator.Paginator;

@ManagedBean
public class NewsListBackingBean extends AbstractBackingBean {

	private List<News> newsList = new ArrayList<News>();
	
	private Paginator paginator;
	
	private List<String> monthsAsStringList = new ArrayList<String>();
	
	private List<Date> monthsAsDateList = new ArrayList<Date>();
	
	private Boolean archived = false;
	
	private Integer month;
	
	private Integer year;
	
	private String currentArchive = "";
	
	public NewsListBackingBean() {
		NewsBO newsBO = new NewsBO();
		
		monthsAsDateList = newsBO.getMonthsAsDateListForArchive();
		
		try {
			month = Integer.valueOf(getRequestParameterMap().get("month"));
			year = Integer.valueOf(getRequestParameterMap().get("year"));
			
			Date starting = Dater.startingOfMonth(month - 1, year);
			
			Boolean isInList = false;
			for (Date date : monthsAsDateList) {
				if ((Dater.partOfDate(Calendar.MONTH, starting) == Dater.partOfDate(Calendar.MONTH, date))
					&& (Dater.partOfDate(Calendar.YEAR, starting) == Dater.partOfDate(Calendar.YEAR, date))) {
					isInList = true;
					break;
				}
			}
			if (isInList) {
				newsList = newsBO.getAllBetweenDates(starting, Dater.endingOfMonth(starting));
				archived = true;
				currentArchive = Dater.partOfDate(Calendar.YEAR, starting) + " yýlý "
								+ Dater.monthInTurkish(starting) + " ayý";
			} else {
				newsList = newsBO.getAll();
			}
		} catch (Exception e) {
			newsList = newsBO.getAll();
		}
		
		monthsAsStringList = newsBO.getMonthsAsStringListForArchive();		
		paginator = new Paginator(newsList);
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	
	public Boolean getArchived() {
		return archived;
	}
	
	public String getCurrentArchive() {
		return currentArchive;
	}
	
	public Integer getMonth() {
		return month;
	}

	public Integer getYear() {
		return year;
	}

	public List<String> getMonthsAsStringList() {
		return monthsAsStringList;
	}
	
	public void toArchiveAction(String month) {
		int index = monthsAsStringList.indexOf(month);
		Date dateOfMonth = monthsAsDateList.get(index);
		
		String outcome = "newsList?faces-redirect=true&month="
						+ (Dater.partOfDate(Calendar.MONTH, dateOfMonth) + 1)
						+ "&year="
						+ Dater.partOfDate(Calendar.YEAR, dateOfMonth);
		navigate(outcome);
	}
	
	public Long getNewsCountByMonth(String month) {
		int index = monthsAsStringList.indexOf(month);
		Date dateOfMonth = monthsAsDateList.get(index);
		
		NewsBO newsBO = new NewsBO();
		Long count = newsBO.getCountBetweenDates(dateOfMonth, Dater.endingOfMonth(dateOfMonth));
		
		return count;
	}
	
}

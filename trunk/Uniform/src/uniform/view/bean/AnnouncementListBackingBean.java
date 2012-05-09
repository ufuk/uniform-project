package uniform.view.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.entity.Announcement;
import uniform.util.date.Dater;
import uniform.util.paginator.Paginator;

@ManagedBean
public class AnnouncementListBackingBean extends AbstractBackingBean {

	private List<Announcement> announcementList = new ArrayList<Announcement>();
	
	private Paginator paginator;
	
	private List<String> monthsAsStringList = new ArrayList<String>();
	
	private List<Date> monthsAsDateList = new ArrayList<Date>();
	
	private Boolean archived = false;
	
	private Integer month;
	
	private Integer year;
	
	private String currentArchive = "";
	
	public AnnouncementListBackingBean() {
		AnnouncementBO announcementBO = new AnnouncementBO();
		
		monthsAsDateList = announcementBO.getMonthsAsDateListForArchive();
		
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
				announcementList = announcementBO.getAllBetweenDates(starting, Dater.endingOfMonth(starting));
				archived = true;
				currentArchive = Dater.partOfDate(Calendar.YEAR, starting) + " yýlý "
								+ Dater.monthInTurkish(starting) + " ayý";
			} else {
				announcementList = announcementBO.getAll();
			}
		} catch (Exception e) {
			announcementList = announcementBO.getAll();
		}
		
		monthsAsStringList = announcementBO.getMonthsAsStringListForArchive();		
		paginator = new Paginator(announcementList);
	}

	public List<Announcement> getAnnouncementList() {
		return announcementList;
	}

	public void setAnnouncementList(List<Announcement> announcementList) {
		this.announcementList = announcementList;
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
		
		String outcome = "announcementList?faces-redirect=true&month="
						+ (Dater.partOfDate(Calendar.MONTH, dateOfMonth) + 1)
						+ "&year="
						+ Dater.partOfDate(Calendar.YEAR, dateOfMonth);
		navigate(outcome);
	}
	
	public Long getAnnouncementCountByMonth(String month) {
		int index = monthsAsStringList.indexOf(month);
		Date dateOfMonth = monthsAsDateList.get(index);
		
		AnnouncementBO announcementBO = new AnnouncementBO();
		Long count = announcementBO.getCountBetweenDates(dateOfMonth, Dater.endingOfMonth(dateOfMonth));
		
		return count;
	}
	
}

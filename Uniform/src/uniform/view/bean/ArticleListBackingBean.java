package uniform.view.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.ArticleBO;
import uniform.bo.CategoryBO;
import uniform.entity.Article;
import uniform.entity.Category;
import uniform.util.date.Dater;
import uniform.util.paginator.Paginator;

@ManagedBean
public class ArticleListBackingBean extends AbstractBackingBean {

	private List<Article> articleList = new ArrayList<Article>();

	private List<Category> categoryList = new ArrayList<Category>();
	
	private List<String> monthsAsStringList = new ArrayList<String>();
	
	private List<Date> monthsAsDateList = new ArrayList<Date>();

	private Category category;
	
	private Paginator paginator;
	
	private Boolean archived = false;
	
	private Integer month;
	
	private Integer year;
	
	private String currentArchive = "";

	public ArticleListBackingBean() {
		ArticleBO articleBO = new ArticleBO();
		CategoryBO categoryBO = new CategoryBO();
		
		monthsAsDateList = articleBO.getMonthsAsDateListForArchive();
		
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
				articleList = articleBO.getAllBetweenDates(starting, Dater.endingOfMonth(starting));
				archived = true;
				currentArchive = Dater.partOfDate(Calendar.YEAR, starting) + " yýlý "
								+ Dater.monthInTurkish(starting) + " ayý";
			} else {
				articleList = articleBO.getAll();
			}
		} catch (Exception e1) {
			try {
				Long categoryId = Long.valueOf(getRequestParameterMap().get("categoryId"));
				
				category = categoryBO.getById(categoryId);
				if (category == null) {
					articleList = articleBO.getAll();
				} else {
					articleList = articleBO.getAllConfirmedByCategory(category);
				}
			} catch (Exception e2) {
				articleList = articleBO.getAll();
			}
		}
		
		monthsAsStringList = articleBO.getMonthsAsStringListForArchive();
		categoryList = categoryBO.getAll();
		paginator = new Paginator(articleList);
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public Category getCategory() {
		return category;
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
		
		String outcome = "articleList?faces-redirect=true&month="
						+ (Dater.partOfDate(Calendar.MONTH, dateOfMonth) + 1)
						+ "&year="
						+ Dater.partOfDate(Calendar.YEAR, dateOfMonth);
		navigate(outcome);
	}
	
	public Long getArticleCountByMonth(String month) {
		int index = monthsAsStringList.indexOf(month);
		Date dateOfMonth = monthsAsDateList.get(index);
		
		ArticleBO articleBO = new ArticleBO();
		Long count = articleBO.getCountBetweenDates(dateOfMonth, Dater.endingOfMonth(dateOfMonth));
		
		return count;
	}
	
}

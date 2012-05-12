package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import uniform.bo.SearchEngineBO;
import uniform.entity.Announcement;
import uniform.entity.Article;
import uniform.entity.News;

@ManagedBean
@SessionScoped
public class SearchEngineBackingBean extends AbstractBackingBean {

	private String rawKeywords = "";
	
	private List<Article> searchResultsForArticle = new ArrayList<Article>();
	
	private List<News> searchResultsForNews = new ArrayList<News>();
	
	private List<Announcement> searchResultsForAnnouncement = new ArrayList<Announcement>();
	
	public String searchAction() {		
		SearchEngineBO searchEngineBO = new SearchEngineBO();
		searchResultsForArticle = searchEngineBO.searchInArticles(rawKeywords);
		searchResultsForNews = searchEngineBO.searchInNews(rawKeywords);
		searchResultsForAnnouncement = searchEngineBO.searchInAnnouncements(rawKeywords);
		
		rawKeywords = "";
		
		return "searchResults?faces-redirect=true";
	}

	public String getRawKeywords() {
		return rawKeywords;
	}

	public void setRawKeywords(String rawKeywords) {
		this.rawKeywords = rawKeywords;
	}

	public List<Article> getSearchResultsForArticle() {
		return searchResultsForArticle;
	}

	public List<News> getSearchResultsForNews() {
		return searchResultsForNews;
	}

	public void setSearchResultsForNews(List<News> searchResultsForNews) {
		this.searchResultsForNews = searchResultsForNews;
	}

	public List<Announcement> getSearchResultsForAnnouncement() {
		return searchResultsForAnnouncement;
	}

	public void setSearchResultsForAnnouncement(
			List<Announcement> searchResultsForAnnouncement) {
		this.searchResultsForAnnouncement = searchResultsForAnnouncement;
	}
	
}

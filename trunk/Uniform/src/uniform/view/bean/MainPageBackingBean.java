package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.bo.ArticleBO;
import uniform.bo.NewsBO;
import uniform.entity.Announcement;
import uniform.entity.Article;
import uniform.entity.News;

@ManagedBean
public class MainPageBackingBean extends AbstractBackingBean {
	
	private List<Article> lastArticles = new ArrayList<Article>();
	
	private List<News> lastNews = new ArrayList<News>();
	
	private List<Announcement> lastAnnouncements = new ArrayList<Announcement>();
	
	public MainPageBackingBean() {
		ArticleBO articleBO = new ArticleBO();
		lastArticles = articleBO.getAllAndPaginate(0, 5);
		
		NewsBO newsBO = new NewsBO();
		lastNews = newsBO.getAllAndPaginate(0, 5);
		
		AnnouncementBO announcementBO = new AnnouncementBO();
		lastAnnouncements = announcementBO.getAllAndPaginate(0, 5);
	}

	public List<Article> getLastArticles() {
		return lastArticles;
	}

	public List<News> getLastNews() {
		return lastNews;
	}

	public List<Announcement> getLastAnnouncements() {
		return lastAnnouncements;
	}

}

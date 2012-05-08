package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.NewsBO;
import uniform.entity.News;

@ManagedBean
public class ModifyNewsBackingBean extends AbstractBackingBean {
	
	private News news;
	
	private String title;
	
	private String content;
	
	public ModifyNewsBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("newsId"));
		NewsBO newsBO = new NewsBO();
		news = newsBO.getById(userId);
		if (news == null) {
			navigateToErrorPage();
		}
	}
	
	public void init() {
		title = news.getTitle();
		content = news.getContent();
	}
		
	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String modifyAction() {
		NewsBO newsBO = new NewsBO();
		news.setTitle(title);
		news.setContent(content);
		newsBO.saveOrUpdate(news);
		return "newsDetail?faces-redirect=true&newsId=" + news.getId();
	}	
	
}

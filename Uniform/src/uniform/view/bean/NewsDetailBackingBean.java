package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.NewsBO;
import uniform.entity.News;

@ManagedBean
public class NewsDetailBackingBean extends AbstractBackingBean {
	
	private News news;

	public NewsDetailBackingBean() {
		Long newsId = Long.valueOf(getRequestParameterMap().get("newsId"));
		NewsBO newsBO = new NewsBO();
		news = newsBO.getById(newsId);
		if (news == null) {
			navigateToErrorPage();
		}
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
	
	public String deleteAction() {
		NewsBO newsBO = new NewsBO();
		newsBO.delete(news);
		// TODO: redirect somewhere after delete
		return "newsList?faces-redirect=true";
	}
	
}

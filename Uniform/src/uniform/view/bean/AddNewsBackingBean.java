package uniform.view.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import uniform.bo.NewsBO;
import uniform.entity.News;

@ManagedBean
public class AddNewsBackingBean extends AbstractBackingBean {
	
	private String title;
	
	private String content;

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
	
	public String addNewsAction() {
		NewsBO newsBO = new NewsBO();
		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		news.setPublishedDate(new Date((new Date()).getTime()));
		Long newNewsId = newsBO.save(news);
		return "newsDetail?faces-redirect=true&newsId=" + newNewsId;
	}

}

package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.NewsBO;
import uniform.entity.News;

@ManagedBean
public class AdministerNewsBackingBean extends AbstractBackingBean {

	private List<News> newsList = new ArrayList<News>();
	
	public AdministerNewsBackingBean() {
		NewsBO newsBO = new NewsBO();
		newsList = newsBO.getAll();
	}
	
	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}
	
	public String deleteAction(News news) {
		NewsBO newsBO = new NewsBO();
		newsBO.delete(news);
		return "administerNews?faces-redirect=true";
	}

}

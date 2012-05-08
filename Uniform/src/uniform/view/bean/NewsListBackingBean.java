package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.NewsBO;
import uniform.entity.News;
import uniform.util.paginator.Paginator;

@ManagedBean
public class NewsListBackingBean extends AbstractBackingBean {

	private List<News> newsList = new ArrayList<News>();
	
	private Paginator paginator;
	
	public NewsListBackingBean() {
		NewsBO newsBO = new NewsBO();
		newsList = newsBO.getAll();
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
	
}

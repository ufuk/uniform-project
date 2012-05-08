package uniform.bo;

import java.util.List;

import uniform.dao.NewsDAO;
import uniform.entity.News;

public class NewsBO {

	private NewsDAO newsDAO = new NewsDAO();
	
	public void saveOrUpdate(News news) {
		newsDAO.saveOrUpdate(news);
	}
	
	public Long save(News news) {
		return newsDAO.save(news);
	}
	
	public void delete(News news) {
		newsDAO.delete(news);
	}
	
	public News getById(Long id) {
		return newsDAO.getById(id);
	}
		
	public List<News> getAll() {
		return newsDAO.getAll();
	}
	
	public List<News> getAllAndPaginate(int firstResult, int maxResult) {
		return newsDAO.getAllAndPaginate(firstResult, maxResult);
	}
	
}

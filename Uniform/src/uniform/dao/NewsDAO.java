package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import uniform.entity.News;

public class NewsDAO extends AbstractDAO {
	
	public void saveOrUpdate(News news) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(news);
		session.getTransaction().commit();
	}
	
	public Long save(News news) {
		session = getCurrentSession();
		session.beginTransaction();
		Long id = (Long) session.save(news);
		session.getTransaction().commit();
		return id;
	}
	
	public void delete(News news) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(news);
		session.getTransaction().commit();
	}
	
	public News getById(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		News news = (News) session.get(News.class, id);
		session.getTransaction().commit();
		return news;
	}
	
	@SuppressWarnings("unchecked")
	public List<News> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		List<News> news = (ArrayList<News>) session.createCriteria(News.class)
											.addOrder(Order.desc("publishedDate"))
											.list();
		session.getTransaction().commit();
		return news;
	}

	@SuppressWarnings("unchecked")
	public List<News> getAllAndPaginate(int firstResult, int maxResult) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(News.class).addOrder(Order.desc("publishedDate"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		List<News> news = (ArrayList<News>) criteria.list();
		session.getTransaction().commit();
		return news;
	}
	
}

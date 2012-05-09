package uniform.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
	
	public Date getLastsPublishedDate() {
		session = getCurrentSession();
		session.beginTransaction();
		News news = (News) session.createCriteria(News.class)
										.addOrder(Order.desc("publishedDate"))
										.setMaxResults(1)
										.uniqueResult();
		if (news == null) {
			return null;
		}
		return news.getPublishedDate();
	}
	
	public Date getFirstsPublishedDate() {
		session = getCurrentSession();
		session.beginTransaction();
		News news = (News) session.createCriteria(News.class)
										.addOrder(Order.asc("publishedDate"))
										.setMaxResults(1)
										.uniqueResult();
		if (news == null) {
			return null;
		}
		return news.getPublishedDate();
	}
	
	@SuppressWarnings("unchecked")
	public List<News> getAllBetweenDates(Date starting, Date ending) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(News.class)
								.add(Restrictions.ge("publishedDate", starting))
								.add(Restrictions.le("publishedDate", ending))
								.addOrder(Order.desc("publishedDate"));
		List<News> news = (ArrayList<News>) criteria.list();
		session.getTransaction().commit();
		return news;
	}

	public Long getCountBetweenDates(Date starting, Date ending) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(News.class)
								.add(Restrictions.ge("publishedDate", starting))
								.add(Restrictions.le("publishedDate", ending));
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return count;
	}
	
}

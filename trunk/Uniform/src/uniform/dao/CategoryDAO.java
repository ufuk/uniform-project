package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Category;
import uniform.entity.User;

public class CategoryDAO extends AbstractDAO {
	
	public void saveOrUpdate(Category category) {
		session = openSession();
		session.beginTransaction();
		session.saveOrUpdate(category);
		session.getTransaction().commit();
	}
	
	public void delete(Category category) {
		session = openSession();
		session.beginTransaction();
		session.delete(category);
		session.getTransaction().commit();
	}
	
	public Category getById(Long id) {
		session = openSession();
		session.beginTransaction();
		Category category = (Category) session.get(Category.class, id);
		session.getTransaction().commit();
		return category;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getAll() {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Category.class)
										.addOrder(Order.asc("title"));
		List<Category> categories = (ArrayList<Category>) criteria.list();
		session.getTransaction().commit();
		return categories;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getByModerator(User moderator) {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Category.class)
										.add(Restrictions.eq("moderator", moderator));
		List<Category> categoryList = (ArrayList<Category>) criteria.list();
		session.getTransaction().commit();
		return categoryList;
	}
	
	public Category getByTitle(String title) {
		session = openSession();
		session.beginTransaction();
		Category category = (Category) session.createCriteria(Category.class)
												.add(Restrictions.eq("title", title))
												.uniqueResult();
		session.getTransaction().commit();
		return category;
	}
	
	public Long getModeratingCount(User moderator) {
		session = openSession();
		session.beginTransaction();
		Long count = (Long) session.createCriteria(Category.class)
									.add(Restrictions.eq("moderator", moderator))
									.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return count;
	}
	
}

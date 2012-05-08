package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import uniform.entity.Announcement;

public class AnnouncementDAO extends AbstractDAO {
	
	public void saveOrUpdate(Announcement announcement) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(announcement);
		session.getTransaction().commit();
	}
	
	public Long save(Announcement announcement) {
		session = getCurrentSession();
		session.beginTransaction();
		Long id = (Long) session.save(announcement);
		session.getTransaction().commit();
		return id;
	}
	
	public void delete(Announcement announcement) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(announcement);
		session.getTransaction().commit();
	}
	
	public Announcement getById(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		Announcement announcement = (Announcement) session.get(Announcement.class, id);
		session.getTransaction().commit();
		return announcement;
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Announcement.class)
										.addOrder(Order.desc("publishedDate"));
		List<Announcement> announcements = (ArrayList<Announcement>) criteria.list();
		session.getTransaction().commit();
		return announcements;
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> getAllAndPaginate(int firstResult, int maxResult) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Announcement.class).addOrder(Order.desc("publishedDate"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		List<Announcement> announcements = (ArrayList<Announcement>) criteria.list();
		session.getTransaction().commit();
		return announcements;
	}
	
}

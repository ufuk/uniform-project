package uniform.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Announcement;

public class AnnouncementDAO extends AbstractDAO {
	
	public void saveOrUpdate(Announcement announcement) {
		session = openSession();
		session.beginTransaction();
		session.saveOrUpdate(announcement);
		session.getTransaction().commit();
	}
	
	public Long save(Announcement announcement) {
		session = openSession();
		session.beginTransaction();
		Long id = (Long) session.save(announcement);
		session.getTransaction().commit();
		return id;
	}
	
	public void delete(Announcement announcement) {
		session = openSession();
		session.beginTransaction();
		session.delete(announcement);
		session.getTransaction().commit();
	}
	
	public Announcement getById(Long id) {
		session = openSession();
		session.beginTransaction();
		Announcement announcement = (Announcement) session.get(Announcement.class, id);
		session.getTransaction().commit();
		return announcement;
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> getAll() {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Announcement.class)
										.addOrder(Order.desc("publishedDate"));
		List<Announcement> announcements = (ArrayList<Announcement>) criteria.list();
		session.getTransaction().commit();
		return announcements;
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> getAllAndPaginate(int firstResult, int maxResult) {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Announcement.class).addOrder(Order.desc("publishedDate"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		List<Announcement> announcements = (ArrayList<Announcement>) criteria.list();
		session.getTransaction().commit();
		return announcements;
	}
	
	public Date getLastsPublishedDate() {
		session = openSession();
		session.beginTransaction();
		Announcement announcement = (Announcement) session.createCriteria(Announcement.class)
										.addOrder(Order.desc("publishedDate"))
										.setMaxResults(1)
										.uniqueResult();
		if (announcement == null) {
			return null;
		}
		return announcement.getPublishedDate();
	}
	
	public Date getFirstsPublishedDate() {
		session = openSession();
		session.beginTransaction();
		Announcement announcement = (Announcement) session.createCriteria(Announcement.class)
										.addOrder(Order.asc("publishedDate"))
										.setMaxResults(1)
										.uniqueResult();
		if (announcement == null) {
			return null;
		}
		return announcement.getPublishedDate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> getAllBetweenDates(Date starting, Date ending) {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Announcement.class)
								.add(Restrictions.ge("publishedDate", starting))
								.add(Restrictions.le("publishedDate", ending))
								.addOrder(Order.desc("publishedDate"));
		List<Announcement> announcements = (ArrayList<Announcement>) criteria.list();
		session.getTransaction().commit();
		return announcements;
	}

	public Long getCountBetweenDates(Date starting, Date ending) {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Announcement.class)
								.add(Restrictions.ge("publishedDate", starting))
								.add(Restrictions.le("publishedDate", ending));
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return count;
	}
	
}

package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Department;
import uniform.entity.ManagementRole;
import uniform.entity.User;

public class UserDAO extends AbstractDAO {
	
	public void saveOrUpdate(User user) {
		session = openSession();
		session.beginTransaction();
		session.saveOrUpdate(user);
		session.getTransaction().commit();
	}
	
	public void delete(User user) {
		session = openSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
	}
	
	public User getById(Long id) {
		session = openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, id);
		session.getTransaction().commit();
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		session = openSession();
		session.beginTransaction();
		List<User> users = (ArrayList<User>) session.createCriteria(User.class).list();
		session.getTransaction().commit();
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllByDeparment(Department department) {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class)
										.add(Restrictions.eq("department", department));
		List<User> users = (ArrayList<User>) criteria.list();
		session.getTransaction().commit();
		return users;
	}
	
	public User getByEmail(String email) {
		session = openSession();
		session.beginTransaction();
		User user = (User) session.createCriteria(User.class)
								.add(Restrictions.eq("email", email)).uniqueResult();
		session.getTransaction().commit();
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getSystemAdmins() {
		session = openSession();
		session.beginTransaction();
		List<User> users = (ArrayList<User>) session.createCriteria(User.class)
								.add(Restrictions.eq("managementRole", ManagementRole.SYSTEM_ADMIN))
								.list();
		session.getTransaction().commit();
		return users;
	}
	
	public Long getCount() {
		session = openSession();
		session.beginTransaction();
		Long count = (Long) session.createCriteria(User.class)
									.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return count;
	}
	
}

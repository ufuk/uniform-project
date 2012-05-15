package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Department;

public class DepartmentDAO extends AbstractDAO {
	
	public void saveOrUpdate(Department department) {
		session = openSession();
		session.beginTransaction();
		session.merge(department);
		session.getTransaction().commit();
	}
	
	public void delete(Department department) {
		session = openSession();
		session.beginTransaction();
		session.delete(department);
		session.getTransaction().commit();
	}
	
	public Department getById(Long id) {
		session = openSession();
		session.beginTransaction();
		Department department = (Department) session.get(Department.class, id);
		session.getTransaction().commit();
		return department;
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> getAll() {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Department.class)
										.addOrder(Order.asc("name"));
		List<Department> departments = (ArrayList<Department>) criteria.list();
		session.getTransaction().commit();
		return departments;
	}
	
	public Department getByName(String name) {
		session = openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Department.class)
										.add(Restrictions.eq("name", name));
		Department department = (Department) criteria.uniqueResult();
		session.getTransaction().commit();
		return department;
	}
	
}

package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import uniform.entity.Tag;

public class TagDAO extends AbstractDAO {
	
	public void saveOrUpdate(Tag tag) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(tag);
		session.getTransaction().commit();
	}
	
	public void delete(Tag tag) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(tag);
		session.getTransaction().commit();
	}
	
	public Tag getById(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		Tag tag = (Tag) session.get(Tag.class, id);
		session.getTransaction().commit();
		return tag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tag> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		List<Tag> tags = (ArrayList<Tag>) session.createCriteria(Tag.class).list();
		session.getTransaction().commit();
		return tags;
	}
	
}

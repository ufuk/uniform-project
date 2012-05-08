package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Message;
import uniform.entity.Reply;

public class ReplyDAO extends AbstractDAO {
	
	public void saveOrUpdate(Reply reply) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(reply);
		session.getTransaction().commit();
	}
	
	public Long save(Reply reply) {
		session = getCurrentSession();
		session.beginTransaction();
		Long id = (Long) session.save(reply);
		session.getTransaction().commit();
		return id;
	}
	
	public void delete(Reply reply) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(reply);
		session.getTransaction().commit();
	}
	
	public Reply getById(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		Reply reply = (Reply) session.get(Reply.class, id);
		session.getTransaction().commit();
		return reply;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reply> getByMessage(Message message) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Reply.class)
										.add(Restrictions.eq("message", message))
										.addOrder(Order.asc("sentDate"));
		List<Reply> replies = (ArrayList<Reply>) criteria.list();
		session.getTransaction().commit();
		return replies;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reply> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Reply.class)
										.addOrder(Order.desc("sentDate"));
		List<Reply> replies = (ArrayList<Reply>) criteria.list();
		session.getTransaction().commit();
		return replies;
	}
	
}

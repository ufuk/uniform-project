package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Message;
import uniform.entity.User;

public class MessageDAO extends AbstractDAO {
	
	public void saveOrUpdate(Message message) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(message);
		session.getTransaction().commit();
	}
	
	public Long save(Message message) {
		session = getCurrentSession();
		session.beginTransaction();
		Long id = (Long) session.save(message);
		session.getTransaction().commit();
		return id;
	}
	
	public void delete(Message message) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(message);
		session.getTransaction().commit();
	}
	
	public Message getById(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		Message message = (Message) session.get(Message.class, id);
		Hibernate.initialize(message.getReplies());
		session.getTransaction().commit();
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		List<Message> messages = (ArrayList<Message>) session.createCriteria(Message.class).list();
		session.getTransaction().commit();
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getAllActiveByUser(User user) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Message.class)
										.add(Restrictions.or(
												Restrictions.and(
													Restrictions.eq("sender", user),
													Restrictions.eq("deletedBySender", false)),
												Restrictions.and(
														Restrictions.eq("receiver", user),
														Restrictions.eq("deletedByReceiver", false))))
										.addOrder(Order.desc("sentDate"));
		List<Message> messages = (ArrayList<Message>) criteria.list();
		session.getTransaction().commit();
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getAllByUser(User user) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Message.class)
										.add(Restrictions.or(
												Restrictions.eq("sender", user),
												Restrictions.eq("receiver", user)));
		List<Message> messages = (ArrayList<Message>) criteria.list();
		session.getTransaction().commit();
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getAllDeletedByUser(User user) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Message.class)
						.add(Restrictions.or(
								Restrictions.and(
										Restrictions.and(
												Restrictions.eq("sender", user), 
												Restrictions.eq("deletedBySender", true)
												),
												Restrictions.eq("deletedPermanentlyBySender", false)
										),
								Restrictions.and(
										Restrictions.and(
												Restrictions.eq("receiver", user), 
												Restrictions.eq("deletedByReceiver", true)
												),
												Restrictions.eq("deletedPermanentlyByReceiver", false)
										)
							)
						)
						.addOrder(Order.desc("sentDate"));
		List<Message> messages = (ArrayList<Message>) criteria.list();
		session.getTransaction().commit();
		return messages;
	}
	
	public Long getNotReadedMessagesCountByUser(User user) {
		session = getCurrentSession();
		session.beginTransaction();
		Long count = (Long) session.createCriteria(Message.class)
									.add(Restrictions.or(
											Restrictions.and(
													Restrictions.eq("receiver", user),
													Restrictions.eq("readedByReceiver", false)
													),
											Restrictions.and(
													Restrictions.eq("sender", user),
													Restrictions.eq("readedBySender", false)
													)
											)
										)
									.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return count;
	}
	
}

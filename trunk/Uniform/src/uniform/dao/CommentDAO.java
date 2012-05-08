package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Article;
import uniform.entity.Comment;
import uniform.entity.Confirmation;
import uniform.entity.User;

public class CommentDAO extends AbstractDAO {
	
	public void saveOrUpdate(Comment comment) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(comment);
		session.getTransaction().commit();
	}
	
	public void delete(Comment comment) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(comment);
		session.getTransaction().commit();
	}
	
	public Comment getById(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		Comment comment = (Comment) session.get(Comment.class, id);
		Hibernate.initialize(comment.getArticle());
		session.getTransaction().commit();
		return comment;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		List<Comment> comments = (ArrayList<Comment>) session.createCriteria(Comment.class).list();
		session.getTransaction().commit();
		return comments;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getAllByArticle(Article article) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class)
										.add(Restrictions.eq("article", article))
										.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.desc("publishedDate"));
		List<Comment> comments = (ArrayList<Comment>) criteria.list();
		session.getTransaction().commit();
		return comments;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAllByConfirmation(Confirmation confirmation) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class)
										.add(Restrictions.eq("confirmationStatus", confirmation))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.desc("publishedDate"));
		List<Comment> articles = (ArrayList<Comment>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAllByModerator(Confirmation confirmation, User moderator) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class)
									.add(Restrictions.eq("confirmationStatus", confirmation))
									.add(Restrictions.eq("deleted", false))
									.addOrder(Order.desc("publishedDate"))
									.createCriteria("article")
									.createCriteria("category")
									.add(Restrictions.eq("moderator", moderator));
		List<Comment> articles = (ArrayList<Comment>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAllDeleted() {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class)
										.add(Restrictions.eq("deleted", true))
										.addOrder(Order.desc("publishedDate"));
		List<Comment> articles = (ArrayList<Comment>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAllDeletedByModerator(User moderator) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class)
									.add(Restrictions.eq("deleted", true))
									.addOrder(Order.desc("publishedDate"))
									.createCriteria("article")
									.createCriteria("category")
									.add(Restrictions.eq("moderator", moderator));
		List<Comment> articles = (ArrayList<Comment>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getAllByAuthor(User author) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class)
									.add(Restrictions.eq("author", author));
		List<Comment> articles = (ArrayList<Comment>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
}

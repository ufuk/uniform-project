package uniform.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Article;
import uniform.entity.Category;
import uniform.entity.Confirmation;
import uniform.entity.User;

public class ArticleDAO extends AbstractDAO {
	
	public void saveOrUpdate(Article article) {
		session = getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(article);
		session.getTransaction().commit();
	}
	
	public Long save(Article article) {
		session = getCurrentSession();
		session.beginTransaction();
		Long id = (Long) session.save(article);
		session.getTransaction().commit();
		return id;
	}
	
	public void delete(Article article) {
		session = getCurrentSession();
		session.beginTransaction();
		session.delete(article);
		session.getTransaction().commit();
	}
	
	public Article getById(Long id, Boolean deleted) {
		session = getCurrentSession();
		session.beginTransaction();
		Article article = (Article) session.createCriteria(Article.class)
								.add(Restrictions.eq("deleted", deleted))
								.add(Restrictions.eq("id", id))
								.addOrder(Order.desc("publishedDate"))
								.uniqueResult();
		// TODO: also initialize tags
		Hibernate.initialize(article.getLikedUsers());
		session.getTransaction().commit();
		return article;
	}
	
	public Article getByIdAndInitializeLikedUsers(Long id) {
		session = getCurrentSession();
		session.beginTransaction();
		Article article = (Article) session.get(Article.class, id);
		Hibernate.initialize(article.getLikedUsers());
		session.getTransaction().commit();
		return article;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAll() {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.desc("publishedDate"));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllAndPaginate(int firstResult, int maxResult) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
								.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
								.add(Restrictions.eq("deleted", false))
								.addOrder(Order.desc("publishedDate"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByCategory(Category category) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("category", category));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllConfirmedByCategory(Category category) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
										.add(Restrictions.eq("category", category))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.desc("publishedDate"));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByConfirmation(Confirmation confirmation) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("confirmationStatus", confirmation))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.desc("publishedDate"));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByModerator(Confirmation confirmation, User moderator) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
									.add(Restrictions.eq("confirmationStatus", confirmation))
									.add(Restrictions.eq("deleted", false))
									.addOrder(Order.desc("publishedDate"))
									.createCriteria("category")
									.add(Restrictions.eq("moderator", moderator));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllDeleted() {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("deleted", true))
										.addOrder(Order.desc("publishedDate"));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllDeletedByModerator(User moderator) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
									.add(Restrictions.eq("deleted", true))
									.addOrder(Order.desc("publishedDate"))
									.createCriteria("category")
									.add(Restrictions.eq("moderator", moderator));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByAuthor(User author) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("author", author));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByAuthor(User author, Confirmation confirmation, Boolean deleted) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
										.add(Restrictions.eq("author", author))
										.add(Restrictions.eq("confirmationStatus", confirmation))
										.add(Restrictions.eq("deleted", deleted))
										.addOrder(Order.desc("publishedDate"));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByAuthorAndPaginate(User author, int firstResult, int maxResult) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
								.add(Restrictions.eq("author", author))
								.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
								.add(Restrictions.eq("deleted", false))
								.addOrder(Order.desc("publishedDate"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}

	@SuppressWarnings("unchecked")
	public List<Article> getAllLikedByUser(User user) {
		session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Article.class)
									.addOrder(Order.desc("publishedDate"))
									.createCriteria("likedUsers")
									.add(Restrictions.eq("id", user.getId()));
		List<Article> articles = (ArrayList<Article>) criteria.list();
		session.getTransaction().commit();
		return articles;
	}
	
	public Date getLastArticlesPublishedDate() {
		session = getCurrentSession();
		session.beginTransaction();
		Article article = (Article) session.createCriteria(Article.class)
										.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.desc("publishedDate"))
										.setMaxResults(1)
										.uniqueResult();
		if (article == null) {
			return null;
		}
		return article.getPublishedDate();
	}
	
	public Date getFirstArticlesPublishedDate() {
		session = getCurrentSession();
		session.beginTransaction();
		Article article = (Article) session.createCriteria(Article.class)
										.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
										.add(Restrictions.eq("deleted", false))
										.addOrder(Order.asc("publishedDate"))
										.setMaxResults(1)
										.uniqueResult();
		if (article == null) {
			return null;
		}
		return article.getPublishedDate();
	}
	
}

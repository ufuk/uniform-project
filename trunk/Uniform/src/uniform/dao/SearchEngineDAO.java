package uniform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import uniform.entity.Announcement;
import uniform.entity.Article;
import uniform.entity.Confirmation;
import uniform.entity.News;

public class SearchEngineDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<Article> searchInArticles(List<String> keywords) {
		session = openSession();
		session.beginTransaction();
		
		Criteria criteriaForSearch = session.createCriteria(Article.class)
									.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
									.add(Restrictions.eq("deleted", false))
									.addOrder(Order.desc("publishedDate"));
		
		for (String keyword : keywords) {
			String pattern = "%" + keyword + "%";
			criteriaForSearch.add(Restrictions.or(Restrictions.ilike("title", pattern, MatchMode.ANYWHERE),
												Restrictions.ilike("content", pattern, MatchMode.ANYWHERE)));
		}
		
		List<Article> searchResult = (ArrayList<Article>) criteriaForSearch.list();
		
		Criteria criteriaForCount = session.createCriteria(Article.class)
									.add(Restrictions.eq("confirmationStatus", Confirmation.CONFIRMED))
									.add(Restrictions.eq("deleted", false))
									.setProjection(Projections.rowCount());

		if (searchResult.size() > 1 && searchResult.size() == ((Long) criteriaForCount.uniqueResult()).intValue()) {
			session.getTransaction().commit();
			return new ArrayList<Article>();
		}
		
		session.getTransaction().commit();
		
		return searchResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<News> searchInNews(List<String> keywords) {
		session = openSession();
		session.beginTransaction();
		
		Criteria criteriaForSearch = session.createCriteria(News.class)
									.addOrder(Order.desc("publishedDate"));
		
		for (String keyword : keywords) {
			String pattern = "%" + keyword + "%";
			criteriaForSearch.add(Restrictions.or(
										Restrictions.ilike("title", pattern, MatchMode.ANYWHERE),
										Restrictions.ilike("content", pattern, MatchMode.ANYWHERE)));
		}
		
		List<News> searchResult = (ArrayList<News>) criteriaForSearch.list();
		
		Criteria criteriaForCount = session.createCriteria(News.class)
									.setProjection(Projections.rowCount());

		if (searchResult.size() > 1 && searchResult.size() == ((Long) criteriaForCount.uniqueResult()).intValue()) {
			session.getTransaction().commit();
			return new ArrayList<News>();
		}
		
		session.getTransaction().commit();
		
		return searchResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> searchInAnnouncements(List<String> keywords) {
		session = openSession();
		session.beginTransaction();
		
		Criteria criteriaForSearch = session.createCriteria(Announcement.class)
									.addOrder(Order.desc("publishedDate"));
		
		for (String keyword : keywords) {
			String pattern = "%" + keyword + "%";
			criteriaForSearch.add(Restrictions.or(
										Restrictions.ilike("title", pattern, MatchMode.ANYWHERE),
										Restrictions.ilike("content", pattern, MatchMode.ANYWHERE)));
		}
		
		List<Announcement> searchResult = (ArrayList<Announcement>) criteriaForSearch.list();
		
		Criteria criteriaForCount = session.createCriteria(Announcement.class)
									.setProjection(Projections.rowCount());

		if (searchResult.size() > 1 && searchResult.size() == ((Long) criteriaForCount.uniqueResult()).intValue()) {
			session.getTransaction().commit();
			return new ArrayList<Announcement>();
		}
		
		session.getTransaction().commit();
		
		return searchResult;
	}
	
}

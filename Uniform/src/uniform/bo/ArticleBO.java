package uniform.bo;

import java.util.Date;
import java.util.List;

import uniform.dao.ArticleDAO;
import uniform.entity.Article;
import uniform.entity.Category;
import uniform.entity.Comment;
import uniform.entity.Confirmation;
import uniform.entity.User;
import uniform.util.date.Dater;

public class ArticleBO {

	private ArticleDAO articleDAO = new ArticleDAO();
	
	public void saveOrUpdate(Article article) {
		articleDAO.saveOrUpdate(article);
	}
	
	public Long save(Article article) {
		return articleDAO.save(article);
	}
	
	public void delete(Article article) {
		CommentBO commentBO = new CommentBO();
		for (Comment comment : commentBO.getAllByArticle(article)) {
			commentBO.delete(comment);
		}
		articleDAO.delete(article);
	}
	
	public Article getById(Long id, Boolean deleted) {
		return articleDAO.getById(id, deleted);
	}
		
	public List<Article> getAll() {
		return articleDAO.getAll();
	}
	
	public List<Article> getAllAndPaginate(int firstResult, int maxResult) {
		return articleDAO.getAllAndPaginate(firstResult, maxResult);
	}
	
	public List<Article> getAllByCategory(Category category) {
		return articleDAO.getAllByCategory(category);
	}
	
	public List<Article> getAllConfirmedByCategory(Category category) {
		return articleDAO.getAllConfirmedByCategory(category);
	}
	
	public List<Article> getAllByConfirmation(Confirmation confirmation) {
		return articleDAO.getAllByConfirmation(confirmation);
	}
	
	public List<Article> getAllByModerator(Confirmation confirmation, User moderator) {
		return articleDAO.getAllByModerator(confirmation, moderator);
	}
	
	public List<Article> getAllDeleted() {
		return articleDAO.getAllDeleted();
	}
	
	public List<Article> getAllDeletedByModerator(User moderator) {
		return articleDAO.getAllDeletedByModerator(moderator);
	}
	
	public List<Article> getAllByAuthor(User author) {
		return articleDAO.getAllByAuthor(author);
	}
	
	public List<Article> getAllByAuthor(User author, Confirmation confirmation, Boolean deleted) {
		return articleDAO.getAllByAuthor(author, confirmation, deleted);
	}
	
	public List<Article> getAllByAuthorAndPaginate(User author, int firstResult, int maxResult) {
		return articleDAO.getAllByAuthorAndPaginate(author, firstResult, maxResult);
	}
	
	public List<Article> getAllLikedByUser(User user) {
		return articleDAO.getAllLikedByUser(user);
	}
	
	public Boolean isThisArticleLikedByThisUserBefore(Long articleId, User user) {
		Article article = articleDAO.getByIdAndInitializeLikedUsers(articleId);
		for (User likedUser : article.getLikedUsers()) {
			if (likedUser.getId().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public void likeArticleByThisUser(Long articleId, User user) {
		Article article = articleDAO.getByIdAndInitializeLikedUsers(articleId);
		article.getLikedUsers().add(user);
		articleDAO.saveOrUpdate(article);
	}
	
	public void cancelToLikeArticleByThisUser(Long articleId, User user) {
		Article article = articleDAO.getByIdAndInitializeLikedUsers(articleId);
		User willBeDeletedUser = null;
		for (User likedUser : article.getLikedUsers()) {
			if (likedUser.getId().equals(user.getId())) {
				willBeDeletedUser = likedUser;
			}
		}
		article.getLikedUsers().remove(willBeDeletedUser);
		articleDAO.saveOrUpdate(article);
	}
	
	public List<Date> getDateListForArchive() {
		Date firstArticlesPublishedDate = articleDAO.getFirstArticlesPublishedDate();
		Date lastArticlesPublishedDate = articleDAO.getLastArticlesPublishedDate();		
		
		for (Date date : Dater.getMonthListBetweenDates(firstArticlesPublishedDate, lastArticlesPublishedDate)) {
			System.out.println(date);
		}
		
		return null;
	}
	
}

package uniform.bo;

import java.util.List;

import uniform.dao.UserDAO;
import uniform.entity.Article;
import uniform.entity.Category;
import uniform.entity.Comment;
import uniform.entity.Department;
import uniform.entity.Message;
import uniform.entity.User;

public class UserBO {

	private UserDAO userDAO = new UserDAO();
	
	public void saveOrUpdate(User user) {
		userDAO.saveOrUpdate(user);
	}
	
	public void delete(User user) {
		ArticleBO articleBO = new ArticleBO();
		for (Article article : articleBO.getAllByAuthor(user)) {
			article.setAuthor(null);
			articleBO.saveOrUpdate(article);
		}
		for (Article article : articleBO.getAllLikedByUser(user)) {
			articleBO.cancelToLikeArticleByThisUser(article.getId(), user);
		}
		CommentBO commentBO = new CommentBO();
		for (Comment comment : commentBO.getAllByAuthor(user)) {
			comment.setAuthor(null);
			comment.setWrittenByVisitor(true);
			comment.setVisitorName(user.getFullName());
			comment.setVisitorEmail(user.getEmail());
			commentBO.saveOrUpdate(comment);
		}
		MessageBO messageBO = new MessageBO();
		for (Message message : messageBO.getAllByUser(user)) {
			messageBO.delete(message);
		}
		dismissFromAllModeratingJobs(user);
		userDAO.delete(user);
	}
	
	public void dismissFromAllModeratingJobs(User moderator) {
		CategoryBO categoryBO = new CategoryBO();
		for (Category category : categoryBO.getByModerator(moderator)) {
			categoryBO.dismissModerator(category);
		}
	}
	
	public User getById(Long id) {
		return userDAO.getById(id);
	}
		
	public List<User> getAll() {
		return userDAO.getAll();
	}
	
	public List<User> getAllByDeparment(Department department) {
		return userDAO.getAllByDeparment(department);
	}
	
	public Boolean isThisEmailAlreadySaved(String email) {
		for (User user : getAll()) {
			if (user.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	public User getByEmail(String email) {
		return userDAO.getByEmail(email);
	}

	public List<User> getSystemAdmins() {
		return userDAO.getSystemAdmins();
	}
	
	public Long getCount() {
		return userDAO.getCount();
	}
	
}

package uniform.bo;

import java.util.List;

import uniform.dao.CommentDAO;
import uniform.entity.Article;
import uniform.entity.Comment;
import uniform.entity.Confirmation;
import uniform.entity.User;

public class CommentBO {

	private CommentDAO commentDAO = new CommentDAO();
	
	public void saveOrUpdate(Comment comment) {
		commentDAO.saveOrUpdate(comment);
	}
	
	public void delete(Comment comment) {
		commentDAO.delete(comment);
	}
	
	public Comment getById(Long id) {
		return commentDAO.getById(id);
	}
		
	public List<Comment> getAll() {
		return commentDAO.getAll();
	}

	public List<Comment> getAllByArticle(Article article) {
		return commentDAO.getAllByArticle(article);
	}
	
	public List<Comment> getAllJustByArticle(Article article) {
		return commentDAO.getAllJustByArticle(article);
	}
	
	public List<Comment> getAllByConfirmation(Confirmation confirmation) {
		return commentDAO.getAllByConfirmation(confirmation);
	}
	
	public List<Comment> getAllByModerator(Confirmation confirmation, User moderator) {
		return commentDAO.getAllByModerator(confirmation, moderator);
	}
	
	public List<Comment> getAllDeleted() {
		return commentDAO.getAllDeleted();
	}
	
	public List<Comment> getAllDeletedByModerator(User moderator) {
		return commentDAO.getAllDeletedByModerator(moderator);
	}

	public List<Comment> getAllByAuthor(User author) {
		return commentDAO.getAllByAuthor(author);
	}
	
}

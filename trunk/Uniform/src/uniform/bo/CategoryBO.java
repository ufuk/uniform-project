package uniform.bo;

import java.util.List;

import uniform.dao.CategoryDAO;
import uniform.entity.Article;
import uniform.entity.Category;
import uniform.entity.ManagementRole;
import uniform.entity.User;

public class CategoryBO {

	private CategoryDAO categoryDAO = new CategoryDAO();
	
	public void saveOrUpdate(Category category) {
		categoryDAO.saveOrUpdate(category);
	}
	
	public void delete(Category category) {
		ArticleBO articleBO = new ArticleBO();
		for (Article article : articleBO.getAllByCategory(category)) {
			article.setCategory(null);
			articleBO.saveOrUpdate(article);
		}
		dismissModerator(category);
		categoryDAO.delete(category);
	}
	
	public Category getById(Long id) {
		return categoryDAO.getById(id);
	}
	
	public List<Category> getAll() {
		return categoryDAO.getAll();
	}
	
	public List<Category> getByModerator(User moderator) {
		return categoryDAO.getByModerator(moderator);
	}
	
	public void dismissModerator(Category category) {
		User moderator = category.getModerator();
		category.setModerator(null);
		saveOrUpdate(category);
		if (moderator != null && getModeratingCount(moderator) == 0) {
			UserBO userBO = new UserBO();
			moderator.setManagementRole(ManagementRole.NONE);
			userBO.saveOrUpdate(moderator);
		}
	}
	
	public Long getModeratingCount(User moderator) {
		return categoryDAO.getModeratingCount(moderator);
	}
	
	public Boolean isThisTitleAlreadySaved(String title) {
		if (categoryDAO.getByTitle(title) != null) {
			return true;
		}
		return false;
	}
	
}

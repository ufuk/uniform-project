package uniform.view.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.CategoryBO;
import uniform.bo.UserBO;
import uniform.entity.Category;
import uniform.entity.ManagementRole;
import uniform.entity.User;

@ManagedBean
public class AdvanceAsModeratorBackingBean extends AbstractBackingBean {

	private User user;
	
	private Long categoryId;
	
	private List<Category> categoryList;
	
	public AdvanceAsModeratorBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("userId"));
		if (getUserBean().getCurrentUser().getId().equals(userId)) {
			navigateToErrorPage();
		} else {
			UserBO userBO = new UserBO();
			user = userBO.getById(userId);
		}
		if (user == null) {
			navigateToErrorPage();
		}
		CategoryBO categoryBO = new CategoryBO();
		categoryList = categoryBO.getAll();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String advanceAsModeratorAction() {
		CategoryBO categoryBO = new CategoryBO();
		Category category = categoryBO.getById(categoryId);
		category.setModerator(user);
		categoryBO.saveOrUpdate(category);
		if (!user.isModerator()) {
			UserBO userBO = new UserBO();
			user.setManagementRole(ManagementRole.MODERATOR);
			userBO.saveOrUpdate(user);
		}
		return "administerUsers?faces-redirect=true";
	}

}

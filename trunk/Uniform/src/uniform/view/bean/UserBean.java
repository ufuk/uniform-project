package uniform.view.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import uniform.bo.MessageBO;
import uniform.entity.Category;
import uniform.entity.ManagementRole;
import uniform.entity.User;

@ManagedBean
@SessionScoped
public class UserBean extends AbstractBackingBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User currentUser = null;
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public Long getNotReadedMessagesCount() {
		MessageBO messageBO = new MessageBO();
		return messageBO.getNotReadedMessagesCountByUser(currentUser);
	}
	
	public void loginAs(User user) {
		currentUser = user;
	}

	public Boolean isLoggedIn() {
		if (currentUser == null) {
			return false;
		}
		return true;
	}
	
	public Boolean isUserSystemAdmin() {
		if (!isLoggedIn()) {
			return false;
		} else if (currentUser.getManagementRole() == ManagementRole.SYSTEM_ADMIN) {
			return true;
		}
		return false;
	}
	
	public Boolean isUserModerator() {
		if (!isLoggedIn()) {
			return false;
		} else if (currentUser.getManagementRole() == ManagementRole.MODERATOR) {
			return true;
		}
		return false;
	}
	
	public void navigateToMainIfLoggedIn() {
		if (isLoggedIn()) {
			navigateToMainPage();
		}
	}
	
	public void navigateToMainIfNotLoggedIn() {
		if (!isLoggedIn()) {
			navigateToMainPage();
		}
	}
	
	public void navigateToMainIfNotSystemAdmin() {
		if (!isLoggedIn()
			|| currentUser.getManagementRole() != ManagementRole.SYSTEM_ADMIN) {
			navigateToMainPage();
		}
	}
	
	public void navigateToLoginIfNotLoggedIn() {
		if (!isLoggedIn()) {
			navigateToLogin();
		}
	}
	
	public void navigateToMainIfNotSystemAdminOrModerator() {
		if (!isLoggedIn()
			|| currentUser.getManagementRole() == ManagementRole.NONE) {
			navigateToMainPage();
		}
	}
	
	public void logoutAndNavigateToMain(ComponentSystemEvent cse) {
		currentUser = null;
		navigateToMainPage();
	}
	
	public Boolean isUserAuthorizedForCategory(Category category) {
		if (!isLoggedIn()) {
			return false;
		} else if (currentUser.isSystemAdmin()) {
			return true;
		} else if (category.getModerator() != null 
					&& category.getModerator().getId().equals(currentUser.getId())) {
			return true;
		}
		return false;
	}

}

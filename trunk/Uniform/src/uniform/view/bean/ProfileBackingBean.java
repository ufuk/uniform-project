package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.ArticleBO;
import uniform.bo.UserBO;
import uniform.entity.Article;
import uniform.entity.Confirmation;
import uniform.entity.Sex;
import uniform.entity.User;
import uniform.entity.UserRole;

@ManagedBean
public class ProfileBackingBean extends AbstractBackingBean {
	
	private User user;
	
	private List<Article> lastArticles = new ArrayList<Article>();
	
	private List<Article> likedArticles = new ArrayList<Article>();
	
	public ProfileBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("userId"));
		UserBO userBO = new UserBO();
		user = userBO.getById(userId);
		if (user == null) {
			navigateToErrorPage();
		}
		ArticleBO articleBO = new ArticleBO();
		lastArticles = articleBO.getAllByAuthor(user, Confirmation.CONFIRMED, false);
		likedArticles = articleBO.getAllLikedByUser(user);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Article> getLastArticles() {
		return lastArticles;
	}
	
	public List<Article> getLikedArticles() {
		return likedArticles;
	}

	public String getRole() {
		if (user.getUserRole() == UserRole.STUDENT) {
			return "Öðrenci";
		} else if (user.getUserRole() == UserRole.PROFESSOR) {
			return "Öðretim Üyesi";
		} else if (user.getUserRole() == UserRole.GRADUATED) {
			return "Mezun";
		}
		return "";
	}
	
	public String getSex() {
		if (user.getSex() == Sex.FEMALE) {
			return "Kadýn";
		} else if (user.getSex() == Sex.MALE) {
			return "Erkek";
		}
		return "-";
	}
	
	public Boolean isUserOwnerOfThisProfile() {
		if (!getUserBean().isLoggedIn()) {
			return false;
		} else if (getUserBean().getCurrentUser().getId().equals(user.getId())) {
			return true;
		}
		return false;
	}

}

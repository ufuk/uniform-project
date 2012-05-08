package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.CategoryBO;
import uniform.bo.UserBO;
import uniform.util.email.EmailSender;
import uniform.entity.ManagementRole;
import uniform.entity.User;

@ManagedBean
public class AdministerUsersBackingBean extends AbstractBackingBean {

	private List<User> userList = new ArrayList<User>();
	
	public AdministerUsersBackingBean() {
		UserBO userBO = new UserBO();
		userList = userBO.getAll();
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public Long getModeratingCount(User moderator) {
		CategoryBO categoryBO = new CategoryBO();
		return categoryBO.getModeratingCount(moderator);
	}
	
	public String advanceAsSystemAdminAction(User user) {
		UserBO userBO = new UserBO();
		userBO.dismissFromAllModeratingJobs(user);
		user.setManagementRole(ManagementRole.SYSTEM_ADMIN);
		userBO.saveOrUpdate(user);
		return "administerUsers?faces-redirect=true";
	}
	
	public String dismissAction(User user) {
		UserBO userBO = new UserBO();
		user.setManagementRole(ManagementRole.NONE);
		userBO.saveOrUpdate(user);
		return "administerUsers?faces-redirect=true";
	}
	
	public String activeUserAction(User user) {
		UserBO userBO = new UserBO();
		user.setActive(true);
		userBO.saveOrUpdate(user);
		
		// Sending informatin email about activation
		String subject = "Uniform: �yeli�iniz Aktifle�tirildi";
		String text = "Say�n " + user.getFullName() + ",\n\n�yeli�iniz aktifle�tirildi. " +
				"Kay�t olurken belirledi�iniz parolan�zla sisteme giri� yaparak sistemi " +
				"kullanmaya ba�layabilirsiniz.\n\n" +
				"--\nSistem Y�neticisi";
		Boolean result = EmailSender.send(user.getEmail(), subject, text);
		if (!result) {
			addFacesMessage("E-posta g�nderilemedi!");
			return null;
		}
		return "administerUsers?faces-redirect=true";
	}
	
	public String deactiveUserAction(User user) {
		UserBO userBO = new UserBO();
		user.setActive(false);
		userBO.saveOrUpdate(user);
		return "administerUsers?faces-redirect=true";
	}
	
	public String deleteUserAction(User user) {
		UserBO userBO = new UserBO();
		userBO.delete(user);
		return "administerUsers?faces-redirect=true";
	}
	
}

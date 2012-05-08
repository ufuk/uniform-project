package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.UserBO;
import uniform.entity.User;
import uniform.util.email.EmailSender;

@ManagedBean
public class LostPasswordBackingBean extends AbstractBackingBean {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String remindPasswordAction() {
		UserBO userBO = new UserBO();
		if (userBO.isThisEmailAlreadySaved(email)) {
			User user = userBO.getByEmail(email);
			String subject = "Uniform: Kullan�c� Parolan�z";
			String text = "Kullan�c� Parolan�z: " + user.getPassword() + "\n\n" +
					"Sisteme giri� yapt�ktan sonra mevcut parolan�z� de�i�tirmenizi �neririz.\n\n" +
					"--\nSistem Y�neticisi";
			Boolean result = EmailSender.send(email, subject, text);
			if (!result) {
				navigateToErrorPage();
			}
		} else {
			addFacesMessage("Girmi� oldu�unuz e-posta adresi sistemde kay�tl� de�il");
			return null;
		}
		
		return "login?faces-redirect=true";
	}

}

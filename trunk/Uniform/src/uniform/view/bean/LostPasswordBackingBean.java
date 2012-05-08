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
			String subject = "Uniform: Kullanýcý Parolanýz";
			String text = "Kullanýcý Parolanýz: " + user.getPassword() + "\n\n" +
					"Sisteme giriþ yaptýktan sonra mevcut parolanýzý deðiþtirmenizi öneririz.\n\n" +
					"--\nSistem Yöneticisi";
			Boolean result = EmailSender.send(email, subject, text);
			if (!result) {
				navigateToErrorPage();
			}
		} else {
			addFacesMessage("Girmiþ olduðunuz e-posta adresi sistemde kayýtlý deðil");
			return null;
		}
		
		return "login?faces-redirect=true";
	}

}

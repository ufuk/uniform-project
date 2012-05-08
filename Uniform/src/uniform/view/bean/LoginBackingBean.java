package uniform.view.bean;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uniform.bo.UserBO;
import uniform.entity.User;

@ManagedBean
public class LoginBackingBean extends AbstractBackingBean {

	private Boolean rememberMe;

	private String email;

	private String password;

	public void init() {
		try {
			Cookie cookie[] = ((HttpServletRequest) getFacesContext()
					.getExternalContext().getRequest()).getCookies();
	
			if (cookie != null && cookie.length > 0) {
				for (int i = 0; i < cookie.length; i++) {
					if (cookie[i].getName().equals("email")) {
						email = cookie[i].getValue();
					} else if (cookie[i].getName().equals("password")) {
						password = cookie[i].getValue();
					} else if (cookie[i].getName().equals("rememberMe")) {
						rememberMe = Boolean.valueOf(cookie[i].getValue());
					}
				}
			}
		} catch (Exception e) {
			
		}
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String loginAction() {
		UserBO userBO = new UserBO();
		User user = userBO.getByEmail(email);
		if (user == null) {
			addFacesMessage("Giriþ yapýlamadý, girmiþ olduðunuz "
					+ "e-posta adresi sistemde kayýtlý deðildir");
			return null;
		} else if (!user.getActive()) {
			addFacesMessage("Üyeliðiniz aktif deðil, lütfen önce üyeliðinizi aktifleþtiriniz");
			return null;
		} else if (user.getPassword().equals(password)) {
			getUserBean().loginAs(user);
			// remember (or not remember)
			rememberOrNot();
			// TODO: redirect to somewhere after login
			return "mainPage?faces-redirect=true";
		} else {
			addFacesMessage("Giriþ yapýlamadý, lütfen girmiþ olduðunuz "
					+ "kullanýcý bilgilerini kontrol ediniz");
			return null;
		}
	}

	private void rememberOrNot() {
		if (rememberMe) {
			Cookie emailCookie = new Cookie("email", email);
			Cookie passwordCookie = new Cookie("password", password);
			Cookie rememberMeCookie = new Cookie("rememberMe",
					rememberMe.toString());

			emailCookie.setMaxAge(86400);
			passwordCookie.setMaxAge(86400);
			rememberMeCookie.setMaxAge(86400);

			((HttpServletResponse) getFacesContext().getExternalContext()
					.getResponse()).addCookie(emailCookie);
			((HttpServletResponse) getFacesContext().getExternalContext()
					.getResponse()).addCookie(passwordCookie);
			((HttpServletResponse) getFacesContext().getExternalContext()
					.getResponse()).addCookie(rememberMeCookie);
		} else {
			Cookie cookie[] = ((HttpServletRequest) getFacesContext()
					.getExternalContext().getRequest()).getCookies();

			if (cookie != null && cookie.length > 0) {
				for (int i = 0; i < cookie.length; i++) {
					if (cookie[i].getName().equals("email")
							|| cookie[i].getName().equals("password")
							|| cookie[i].getName().equals("rememberMe")) {
						cookie[i].setMaxAge(0);
						((HttpServletResponse) getFacesContext()
								.getExternalContext().getResponse())
								.addCookie(cookie[i]);
					}
				}
			}
		}
	}

}

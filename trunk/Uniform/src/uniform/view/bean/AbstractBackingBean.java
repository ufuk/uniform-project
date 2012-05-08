package uniform.view.bean;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public abstract class AbstractBackingBean {
	
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public Flash getFlash() {
		return getFacesContext().getExternalContext().getFlash();
	}

	public Map<String,String> getRequestParameterMap() {
		return getFacesContext().getExternalContext().getRequestParameterMap();
	}
	
	public Map<String, Object> getRequestMap() {
		return getFacesContext().getExternalContext().getRequestMap();
	}
	
	public Map<String, Object> getSessionMap() {
		return getFacesContext().getExternalContext().getSessionMap();
	}
	
	public UserBean getUserBean() {
		return (UserBean) getSessionMap().get("userBean");
	}
	
	public void navigate(String outcome) {
		getFacesContext().getApplication().getNavigationHandler()
					.handleNavigation(getFacesContext(), null, outcome);
	}
	
	public void navigateToErrorPage() {
		navigate("errorPage?faces-redirect=true");
	}
	
	public void navigateToMainPage() {
		navigate("mainPage?faces-redirect=true");
	}
	
	public void navigateToLogin() {
		navigate("login?faces-redirect=true");
	}
	
	public void addFacesMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(message));
	}
	
}

package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import uniform.bo.DepartmentBO;
import uniform.bo.UserBO;
import uniform.entity.Department;
import uniform.entity.Sex;
import uniform.entity.User;
import uniform.entity.UserRole;

@ManagedBean
public class ModifyProfileBackingBean extends AbstractBackingBean {
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String password = "";
	
	private String passwordAgain = "";
	
	private Boolean passwordChanged = false;
	
	private String about;
	
	private Long departmentId;
	
	private List<Department> departmentList = new ArrayList<Department>();
	
	private String userRole;
	
	private String sex;
	
	public ModifyProfileBackingBean() {
		DepartmentBO departmentBO = new DepartmentBO();
		departmentList = departmentBO.getAll();
	}
	
	public void init() {
		if (getUserBean().isLoggedIn()) {
			User currentUser = getUserBean().getCurrentUser();
			name = currentUser.getName();
			surname = currentUser.getSurname();
			email = currentUser.getEmail();
			about = currentUser.getAbout();
			if (currentUser.getDepartment() != null) {
				departmentId = currentUser.getDepartment().getId();
			}
			userRole = currentUser.getUserRole().toString();
			sex = currentUser.getSex().toString();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public void validatePassword(FacesContext context, UIComponent toValidate, Object value)
					throws ValidatorException {
		password = (String) value;
	}
	
	public void validatePasswordAgain(FacesContext context, UIComponent toValidate, Object value)
					throws ValidatorException {
		passwordAgain = (String) value;
		if (!password.equals(value)) {
			FacesMessage message = new FacesMessage("Lütfen parola alanlarýna ayný deðerleri giriniz");
			throw new ValidatorException(message);
		} else if (!password.equals("") && !passwordAgain.equals("")) {
			passwordChanged = true;
		}
	}

	public String modifyAction() {
		UserBO userBO = new UserBO();
		User currentUser = getUserBean().getCurrentUser();
		if (currentUser.getEmail().equals(email)
			|| !userBO.isThisEmailAlreadySaved(email)) {
			currentUser.setName(name);
			currentUser.setSurname(surname);
			currentUser.setAbout(about);
			currentUser.setEmail(email);
			currentUser.setSex(Sex.valueOf(sex));
			currentUser.setUserRole(UserRole.valueOf(userRole));
			if (passwordChanged) {
				currentUser.setPassword(password);
			}
			if (departmentId != 0) {
				Department department = new Department();
				department.setId(departmentId);
				currentUser.setDepartment(department);
			}
			userBO.saveOrUpdate(currentUser);
			getUserBean().loginAs(currentUser);
		} else {
			addFacesMessage("Girmiþ olduðunuz e-posta adresi sisteme daha " +
							"önceden kaydedilmiþtir, lütfen baþka bir e-posta giriniz");
			return null;
		}		
		return "profile?faces-redirect=true&userId=" + currentUser.getId();
	}
	
}

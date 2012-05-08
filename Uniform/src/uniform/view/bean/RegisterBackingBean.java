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
import uniform.entity.ManagementRole;
import uniform.entity.Sex;
import uniform.entity.User;
import uniform.entity.UserRole;

@ManagedBean
public class RegisterBackingBean extends AbstractBackingBean {
	
	private String name;
	
	private String surname;
	
	private String sex;
	
	private String email;
	
	private String password = "";
	
	private String passwordAgain = "";
	
	private String userRole;
	
	private String about;
	
	private Long departmentId;
	
	private List<Department> departmentList = new ArrayList<Department>();
	
	public RegisterBackingBean() {
		DepartmentBO departmentBO = new DepartmentBO();
		departmentList = departmentBO.getAll();
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value)
								throws ValidatorException {
		password = (String) value;
	}
	
	public void validatePasswordAgain(FacesContext context, UIComponent toValidate, Object value)
								throws ValidatorException {
		if (!password.equals(value)) {
			FacesMessage message = new FacesMessage("Lütfen parola alanlarýna ayný deðerleri giriniz");
			throw new ValidatorException(message);
		}
	}
	
	public String registerAction() {
		UserBO userBO = new UserBO();
		if (!userBO.isThisEmailAlreadySaved(email)) {
			User user = new User();
			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
			user.setPassword(password);
			user.setSex(Sex.valueOf(sex));
			user.setUserRole(UserRole.valueOf(userRole));
			user.setAbout(about);
			if (departmentId != 0) {
				Department department = new Department();
				department.setId(departmentId);
				user.setDepartment(department);
			}
			// If there is 0 user, then the first user must be system admin and activated
			if (userBO.getCount() == 0) {
				user.setManagementRole(ManagementRole.SYSTEM_ADMIN);
				user.setActive(true);
			}
			userBO.saveOrUpdate(user);
		} else {
			addFacesMessage("Girmiþ olduðunuz e-posta adresi sisteme daha " +
							"önceden kaydedilmiþtir, lütfen baþka bir e-posta giriniz");
			return null;
		}
		return "mainPage?faces-redirect=true";
	}
	
}

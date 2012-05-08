package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.DepartmentBO;
import uniform.entity.Department;

@ManagedBean
public class ModifyDepartmentBackingBean extends AbstractBackingBean {
	
	private Department department;
	
	private String name;
	
	public ModifyDepartmentBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("departmentId"));
		DepartmentBO departmentBO = new DepartmentBO();
		department = departmentBO.getById(userId);
		if (department == null) {
			navigateToErrorPage();
		}
	}
	
	public void init() {
		name = department.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String modifyAction() {
		DepartmentBO departmentBO = new DepartmentBO();
		if (department.getName().equals(name)
			|| !departmentBO.isThisNameAlreadySaved(name)) {
			department.setName(name);
			departmentBO.saveOrUpdate(department);
		} else {
			addFacesMessage("Girmi� oldu�unuz b�l�m ad� sisteme daha �nceden " +
							"kaydedilmi�tir, l�tfen ba�ka bir b�l�m ad� giriniz");
			return null;
		}
		return "administerDepartments?faces-redirect=true";
	}
	
}

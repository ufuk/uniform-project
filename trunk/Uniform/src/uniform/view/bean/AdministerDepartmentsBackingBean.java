package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.DepartmentBO;
import uniform.entity.Department;

@ManagedBean
public class AdministerDepartmentsBackingBean extends AbstractBackingBean {
	
	private String name;

	private List<Department> departmentList = new ArrayList<Department>();
	
	public AdministerDepartmentsBackingBean() {
		DepartmentBO departmentBO = new DepartmentBO();
		departmentList = departmentBO.getAll();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public String addDepartmentAction() {
		DepartmentBO departmentBO = new DepartmentBO();
		if (!departmentBO.isThisNameAlreadySaved(name)) {
			Department department = new Department();
			department.setName(name);
			departmentBO.saveOrUpdate(department);
		} else {
			addFacesMessage("Girmiþ olduðunuz bölüm adý sisteme daha önceden " +
							"kaydedilmiþtir, lütfen baþka bir bölüm adý giriniz");
			return null;
		}
		return "administerDepartments?faces-redirect=true";
	}
	
	public String deleteAction(Department department) {
		DepartmentBO departmentBO = new DepartmentBO();
		departmentBO.delete(department);
		return "administerDepartments?faces-redirect=true";
	}

}

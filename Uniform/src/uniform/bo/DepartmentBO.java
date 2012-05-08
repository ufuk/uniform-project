package uniform.bo;

import java.util.List;

import uniform.dao.DepartmentDAO;
import uniform.entity.Department;
import uniform.entity.User;

public class DepartmentBO {

	private DepartmentDAO departmentDAO = new DepartmentDAO();
	
	public void saveOrUpdate(Department department) {
		departmentDAO.saveOrUpdate(department);
	}
	
	public void delete(Department department) {
		UserBO userBO = new UserBO();
		for (User user : userBO.getAllByDeparment(department)) {
			user.setDepartment(null);
			userBO.saveOrUpdate(user);
		}
		departmentDAO.delete(department);
	}
	
	public Department getById(Long id) {
		return departmentDAO.getById(id);
	}
		
	public List<Department> getAll() {
		return departmentDAO.getAll();
	}
	
	public Boolean isThisNameAlreadySaved(String name) {
		if (departmentDAO.getByName(name) != null) {
			return true;
		}
		return false;
	}
	
}

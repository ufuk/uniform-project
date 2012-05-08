package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.CategoryBO;
import uniform.entity.Category;

@ManagedBean
public class AdministerCategoriesBackingBean extends AbstractBackingBean {
	
	private String title;
	
	private List<Category> categoryList = new ArrayList<Category>();
	
	public AdministerCategoriesBackingBean() {
		CategoryBO categoryBO = new CategoryBO();
		categoryList = categoryBO.getAll();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public String addCategoryAction() {
		CategoryBO categoryBO = new CategoryBO();
		if (!categoryBO.isThisTitleAlreadySaved(title)) {
			Category category = new Category();
			category.setTitle(title);
			categoryBO.saveOrUpdate(category);
		} else {
			addFacesMessage("Girmiþ olduðunuz kategori baþlýðý sisteme daha önceden " +
							"kaydedilmiþtir, lütfen baþka bir kategori baþlýðý giriniz");
			return null;
		}
		return "administerCategories?faces-redirect=true";
	}
	
	public String deleteAction(Category category) {
		CategoryBO categoryBO = new CategoryBO();
		categoryBO.delete(category);
		return "administerCategories?faces-redirect=true";
	}
	
	public String dismissAction(Category category) {
		CategoryBO categoryBO = new CategoryBO();
		categoryBO.dismissModerator(category);
		return "administerCategories?faces-redirect=true";
	}

}

package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.CategoryBO;
import uniform.entity.Category;

@ManagedBean
public class ModifyCategoryBackingBean extends AbstractBackingBean {
	
	private Category category;
	
	private String title;
	
	public ModifyCategoryBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("categoryId"));
		CategoryBO categoryBO = new CategoryBO();
		category = categoryBO.getById(userId);
		if (category == null) {
			navigateToErrorPage();
		}
	}
	
	public void init() {
		title = category.getTitle();
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String modifyAction() {
		CategoryBO categoryBO = new CategoryBO();
		if (category.getTitle().equals(title)
			|| !categoryBO.isThisTitleAlreadySaved(title)) {
			category.setTitle(title);
			categoryBO.saveOrUpdate(category);
		} else {
			addFacesMessage("Girmiþ olduðunuz kategori baþlýðý sisteme daha önceden " +
							"kaydedilmiþtir, lütfen baþka bir kategori baþlýðý giriniz");
			return null;
		}
		return "administerCategories?faces-redirect=true";
	}
	
}

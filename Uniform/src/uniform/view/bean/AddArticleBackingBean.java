package uniform.view.bean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.ArticleBO;
import uniform.bo.CategoryBO;
import uniform.entity.Article;
import uniform.entity.Category;

@ManagedBean
public class AddArticleBackingBean extends AbstractBackingBean {
	
	private String title;
	
	private String content;
	
	private Long categoryId;
	
	private List<Category> categoryList;
	
	public AddArticleBackingBean() {
		CategoryBO categoryBO = new CategoryBO();
		categoryList = categoryBO.getAll();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String addArticleAction() {
		ArticleBO articleBO = new ArticleBO();
		Article article = new Article();
		article.setTitle(title);
		article.setContent(content);
		Category category = new Category();
		category.setId(categoryId);
		article.setCategory(category);
		article.setPublishedDate(new Date((new Date()).getTime()));
		article.setAuthor(getUserBean().getCurrentUser());
		Long newArticleId = articleBO.save(article);
		return "articleDetail?faces-redirect=true&articleId=" + newArticleId;
	}

}

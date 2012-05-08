package uniform.view.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.ArticleBO;
import uniform.bo.CategoryBO;
import uniform.entity.Article;
import uniform.entity.Category;

@ManagedBean
public class ModifyArticleBackingBean extends AbstractBackingBean {
	
	private Article article;
	
	private String title;
	
	private String content;
	
	private Long categoryId;
	
	private List<Category> categoryList;
	
	public ModifyArticleBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("articleId"));
		ArticleBO articleBO = new ArticleBO();
		article = articleBO.getById(userId, false);
		if (article == null
			|| !article.getAuthor().getId().equals(getUserBean().getCurrentUser().getId())) {
			navigateToErrorPage();
		}
		CategoryBO categoryBO = new CategoryBO();
		categoryList = categoryBO.getAll();
	}
	
	public void init() {
		title = article.getTitle();
		content = article.getContent();
		if (article.getCategory() != null) {
			categoryId = article.getCategory().getId();
		}
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public String modifyAction() {
		ArticleBO articleBO = new ArticleBO();
		article.setTitle(title);
		article.setContent(content);
		Category category = new Category();
		category.setId(categoryId);
		article.setCategory(category);
		articleBO.saveOrUpdate(article);
		return "articleDetail?faces-redirect=true&articleId=" + article.getId();
	}
	
}

package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.ArticleBO;
import uniform.bo.CategoryBO;
import uniform.entity.Article;
import uniform.entity.Category;
import uniform.util.paginator.Paginator;

@ManagedBean
public class ArticleListBackingBean extends AbstractBackingBean {

	private List<Article> articleList = new ArrayList<Article>();

	private List<Category> categoryList = new ArrayList<Category>();

	private Category category;
	
	private Paginator paginator;

	public ArticleListBackingBean() {
		ArticleBO articleBO = new ArticleBO();
		CategoryBO categoryBO = new CategoryBO();
		
		try {
			Long categoryId = Long.valueOf(getRequestParameterMap().get("categoryId"));
			category = categoryBO.getById(categoryId);
			if (category == null) {
				articleList = articleBO.getAll();
			} else {
				articleList = articleBO.getAllConfirmedByCategory(category);
			}
		} catch (Exception e) {
			articleList = articleBO.getAll();
		}
		
		categoryList = categoryBO.getAll();
		paginator = new Paginator(articleList);
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public Category getCategory() {
		return category;
	}

	public Paginator getPaginator() {
		return paginator;
	}

}

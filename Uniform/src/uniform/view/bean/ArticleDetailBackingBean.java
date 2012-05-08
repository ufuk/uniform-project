package uniform.view.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandLink;

import uniform.bo.ArticleBO;
import uniform.bo.CommentBO;
import uniform.entity.Article;
import uniform.entity.Comment;
import uniform.entity.Confirmation;

@ManagedBean
public class ArticleDetailBackingBean extends AbstractBackingBean {	
	
	private Article article;
	
	private String visitorName;
	
	private String visitorEmail;
	
	private String commentContent;
	
	private HtmlCommandLink likeCommandLink;
	
	private List<Comment> commentList = new ArrayList<Comment>();

	public ArticleDetailBackingBean() {
		Long articleId = Long.valueOf(getRequestParameterMap().get("articleId"));
		ArticleBO articleBO = new ArticleBO();
		article = articleBO.getById(articleId, false);
		if (article.getConfirmationStatus() != Confirmation.CONFIRMED
			&& !isUserAuthorOfThisArticle()
			&& !getUserBean().isUserAuthorizedForCategory(article.getCategory())) {
				navigateToErrorPage();
		}
		if (article == null) {
			navigateToErrorPage();
		}
		CommentBO commentBO = new CommentBO();
		commentList = commentBO.getAllByArticle(article);
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorEmail() {
		return visitorEmail;
	}

	public void setVisitorEmail(String visitorEmail) {
		this.visitorEmail = visitorEmail;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}	

	public HtmlCommandLink getLikeCommandLink() {
		return likeCommandLink;
	}

	public void setLikeCommandLink(HtmlCommandLink likeCommandLink) {
		this.likeCommandLink = likeCommandLink;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
	public int getArticleLikedUsersSize() {
		return article.getLikedUsers().size();
	}

	public String addCommentAction() {
		CommentBO commentBO = new CommentBO();
		Comment comment = new Comment();
		comment.setContent(commentContent);
		comment.setPublishedDate(new Date((new Date()).getTime()));
		comment.setArticle(article);
		if (!getUserBean().isLoggedIn()) {
			comment.setVisitorName(visitorName);
			comment.setVisitorEmail(visitorEmail);
			comment.setWrittenByVisitor(true);
		} else {
			comment.setAuthor(getUserBean().getCurrentUser());
		}
		commentBO.saveOrUpdate(comment);
		return "articleDetail?faces-redirect=true&articleId=" + article.getId();
	}
	
	public String deleteCommentAction(Comment comment) {
		CommentBO commentBO = new CommentBO();
		comment.setDeleted(true);
		commentBO.saveOrUpdate(comment);
		return "articleDetail?faces-redirect=true&articleId=" + article.getId();
	}
	
	public String deleteArticleAction() {
		ArticleBO articleBO = new ArticleBO();
		article.setDeleted(true);
		articleBO.saveOrUpdate(article);
		// TODO: redirect to somewhere after delete
		return "articleList?faces-redirect=true";
	}
	
	public String likeOrCancelAction() {
		ArticleBO articleBO = new ArticleBO();
		if (likeCommandLink.getValue().equals("Beðen")) {
			articleBO.likeArticleByThisUser(article.getId(), getUserBean().getCurrentUser());
		} else if (likeCommandLink.getValue().equals("Beðenmekten Vazgeç")) {
			articleBO.cancelToLikeArticleByThisUser(article.getId(), getUserBean().getCurrentUser());
		}
		return "articleDetail?faces-redirect=true&articleId=" + article.getId();
	}
	
	public void likePreRenderViewListener() {
		if (getUserBean().isLoggedIn()) {
			ArticleBO articleBO = new ArticleBO();
			if (articleBO.isThisArticleLikedByThisUserBefore(article.getId(), getUserBean().getCurrentUser())) {
				likeCommandLink.setValue("Beðenmekten Vazgeç");
			} else {
				likeCommandLink.setValue("Beðen");
			}
		}
	}
	
	public Boolean isUserAuthorOfThisArticle() {
		if (!getUserBean().isLoggedIn()
			|| article.getAuthor() == null) {
			return false;
		} else if (getUserBean().getCurrentUser().getId().equals(article.getAuthor().getId())) {
			return true;
		}
		return false;
	}
	
	public Boolean isUserAuthorOfThisComment(Comment comment) {
		if (!getUserBean().isLoggedIn()
			|| comment.getAuthor() == null) {
			return false;
		} else if (getUserBean().getCurrentUser().getId().equals(comment.getAuthor().getId())) {
			return true;
		}
		return false;
	}
	
}

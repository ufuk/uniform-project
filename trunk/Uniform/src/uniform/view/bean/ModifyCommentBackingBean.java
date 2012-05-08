package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.CommentBO;
import uniform.entity.Comment;

@ManagedBean
public class ModifyCommentBackingBean extends AbstractBackingBean {
	
	private Comment comment;
	
	private String content;
	
	public ModifyCommentBackingBean() {
		Long commentId = Long.valueOf(getRequestParameterMap().get("commentId"));
		CommentBO commentBO = new CommentBO();
		comment = commentBO.getById(commentId);
		if (comment == null
			|| !comment.getAuthor().getId().equals(getUserBean().getCurrentUser().getId())) {
			navigateToErrorPage();
		}
	}
	
	public void init() {
		content = comment.getContent();
	}
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String modifyAction() {
		CommentBO commentBO = new CommentBO();
		comment.setContent(content);
		commentBO.saveOrUpdate(comment);
		return "articleDetail?faces-redirect=true&articleId=" + comment.getArticle().getId();
	}
	
}

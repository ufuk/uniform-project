package uniform.view.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.CommentBO;
import uniform.bo.MessageBO;
import uniform.entity.Comment;
import uniform.entity.Confirmation;
import uniform.entity.Message;

@ManagedBean
public class ModerateCommentsBackingBean extends AbstractBackingBean {
	
	private List<Comment> commentList = new ArrayList<Comment>();
	
	private String confirmationStatus;
	
	public ModerateCommentsBackingBean() {
		confirmationStatus = getRequestParameterMap().get("confirmationStatus");
		
		CommentBO commentBO = new CommentBO();
		
		if (confirmationStatus == null || confirmationStatus.isEmpty()) {
			confirmationStatus = "CONFIRMED";
		} 
		if (confirmationStatus.equals("CONFIRMED")
			|| confirmationStatus.equals("NOT_CONFIRMED")
			|| confirmationStatus.equals("PENDING")) {
			if (getUserBean().getCurrentUser().isModerator()) {
				commentList = commentBO.getAllByModerator(Confirmation.valueOf(confirmationStatus),
														  getUserBean().getCurrentUser());
			} else {
				commentList = commentBO.getAllByConfirmation(Confirmation.valueOf(confirmationStatus));
			}
		} else if (confirmationStatus.equals("DELETED")) {
			if (getUserBean().getCurrentUser().isModerator()) {
				commentList = commentBO.getAllDeletedByModerator(getUserBean().getCurrentUser());
			} else {
				commentList = commentBO.getAllDeleted();
			}
		} else {
			navigate("moderateComments?faces-redirect=true&confirmationStatus=" + "CONFIRMED");
		}
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String getConfirmationStatus() {
		return confirmationStatus;
	}

	public void setConfirmationStatus(String confirmationStatus) {
		this.confirmationStatus = confirmationStatus;
	}
	
	public String confirmCommentAction(Comment comment) {
		CommentBO commentBO = new CommentBO();
		comment.setConfirmationStatus(Confirmation.CONFIRMED);
		comment.setDeleted(false);
		commentBO.saveOrUpdate(comment);
		
		// Send a information message about confirmation
		if (comment.getAuthor() != null) {
			MessageBO messageBO = new MessageBO();
			Message message = new Message();
			message.setTitle("Yorumunuz Yayýnlandý");
			message.setSender(getUserBean().getCurrentUser());
			message.setReceiver(comment.getAuthor());
			message.setSentDate(new Date((new Date().getTime())));
			message.setContent("'" + comment.getArticle().getTitle() +
					"' baþlýklý makaleye yapmýþ olduðunuz yorum yayýna alýnmýþtýr. Katkýlarýnýzdan dolayý teþekkür ederiz.");
			messageBO.saveOrUpdate(message);
		}
		
		return "moderateComments?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String rejectCommentAction(Comment comment) {
		CommentBO commentBO = new CommentBO();
		comment.setConfirmationStatus(Confirmation.NOT_CONFIRMED);
		commentBO.saveOrUpdate(comment);
		
		// Send a information message about rejection
		if (comment.getAuthor() != null) {
			MessageBO messageBO = new MessageBO();
			Message message = new Message();
			message.setTitle("Yorumunuz Reddedildi");
			message.setSender(getUserBean().getCurrentUser());
			message.setReceiver(comment.getAuthor());
			message.setSentDate(new Date((new Date().getTime())));
			message.setContent("'" + comment.getArticle().getTitle() +
					"' baþlýklý makaleye yapmýþ olduðunuz yorum yönetici tarafýndan uygun bulunmamýþtýr!");
			messageBO.saveOrUpdate(message);
		}
				
		return "moderateComments?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String cancelCommentConfirmationAction(Comment comment) {
		CommentBO commentBO = new CommentBO();
		comment.setConfirmationStatus(Confirmation.PENDING);
		commentBO.saveOrUpdate(comment);
		return "moderateComments?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String deleteCommentAction(Comment comment) {
		CommentBO commentBO = new CommentBO();
		comment.setDeleted(true);
		commentBO.saveOrUpdate(comment);
		return "moderateComments?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String deleteCommentPermanentlyAction(Comment comment) {
		CommentBO commentBO = new CommentBO();
		commentBO.delete(comment);
		return "moderateComments?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
}

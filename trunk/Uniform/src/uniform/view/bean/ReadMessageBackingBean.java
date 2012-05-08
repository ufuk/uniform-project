package uniform.view.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.MessageBO;
import uniform.bo.ReplyBO;
import uniform.entity.Message;
import uniform.entity.Reply;

@ManagedBean
public class ReadMessageBackingBean extends AbstractBackingBean {
	
	private Message message;
	
	private String replyContent;
	
	private List<Reply> replies = new ArrayList<Reply>();
	
	public ReadMessageBackingBean() {
		Long messageId = Long.valueOf(getRequestParameterMap().get("messageId"));
		MessageBO messageBO = new MessageBO();
		message = messageBO.getById(messageId);
		if (message == null) {
			navigateToErrorPage();
		}
		ReplyBO replyBO = new ReplyBO();
		replies = replyBO.getByMessage(message);
		if (message.getSender().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setReadedBySender(true);
			messageBO.saveOrUpdate(message);
		}
		if (message.getReceiver().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setReadedByReceiver(true);
			messageBO.saveOrUpdate(message);
		}
	}

	public Message getMessage() {
		return message;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	public List<Reply> getReplies() {
		return replies;
	}

	public String replyAction() {
		MessageBO messageBO = new MessageBO();
		message.setDeletedByReceiver(false);
		message.setDeletedBySender(false);
		message.setDeletedPermanentlyByReceiver(false);
		message.setDeletedPermanentlyBySender(false);
		
		if (message.getSender().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setReadedByReceiver(false);
		}
		if (message.getReceiver().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setReadedBySender(false);
		}
		
		ReplyBO replyBO = new ReplyBO();
		Reply reply = new Reply();
		reply.setMessage(message);
		reply.setWritter(getUserBean().getCurrentUser());
		reply.setContent(replyContent);
		reply.setSentDate(new Date((new Date().getTime())));
		reply.setId(replyBO.save(reply));
		
		message.getReplies().add(reply);
		messageBO.saveOrUpdate(message);
		
		return "readAndReplyMessage?faces-redirect=true&messageId=" + message.getId();
	}
	
	public String moveToArchiveAction() {
		MessageBO messageBO = new MessageBO();
		if (message.getSender().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setDeletedBySender(true);
		}
		if (message.getReceiver().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setDeletedByReceiver(true);
		}
		messageBO.saveOrUpdate(message);
		return "messages?faces-redirect=true";
	}
	
	public void navigateToMainIfNotReceiverOrSender() {
		if (!getUserBean().isLoggedIn()
			|| (!message.getReceiver().getId().equals(getUserBean().getCurrentUser().getId())
				&& !message.getSender().getId().equals(getUserBean().getCurrentUser().getId()))) {
			navigateToMainPage();
		}
	}
	
}

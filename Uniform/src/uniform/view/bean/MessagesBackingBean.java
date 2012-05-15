package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.MessageBO;
import uniform.entity.Message;

@ManagedBean
public class MessagesBackingBean extends AbstractBackingBean {
	
	private List<Message> messageList = new ArrayList<Message>();
	
	private String type;
	
	public MessagesBackingBean() {
		type = getRequestParameterMap().get("type");
		
		MessageBO messageBO = new MessageBO();
		
		if (type == null || type.isEmpty()) {
			type = "ACTIVE";
		}
		if (type.equals("ACTIVE")) {
			messageList = messageBO.getAllActiveByUser(getUserBean().getCurrentUser());
		} else if (type.equals("ARCHIVE")) {
			messageList = messageBO.getAllDeletedByUser(getUserBean().getCurrentUser());
		} else {
			navigate("messages?faces-redirect=true");
		} 
	}

	public List<Message> getMessageList() {
		return messageList;
	}
	
	public String getType() {
		return type;
	}

	public String moveToArchiveAction(Message message) {
		MessageBO messageBO = new MessageBO();
		if (message.getSender().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setDeletedBySender(true);
			message.setReadedBySender(true);
		}
		if (message.getReceiver().getId().equals(getUserBean().getCurrentUser().getId())) {
			message.setDeletedByReceiver(true);
			message.setReadedByReceiver(true);
		}
		messageBO.saveOrUpdate(message);
		return "messages?faces-redirect=true";
	}
	
	public String deleteAction(Message message) {
		MessageBO messageBO = new MessageBO();
		if (message.getSender().getId().equals(getUserBean().getCurrentUser().getId())) {
			if (message.getDeletedPermanentlyByReceiver()) {
				messageBO.delete(message);
				return "messages?faces-redirect=true&type=ARCHIVE";
			} else {
				message.setDeletedPermanentlyBySender(true);
				messageBO.saveOrUpdate(message);
			}
		}
		if (message.getReceiver().getId().equals(getUserBean().getCurrentUser().getId())) {
			if (message.getDeletedPermanentlyBySender()) {
				messageBO.delete(message);
			} else {
				message.setDeletedPermanentlyByReceiver(true);
				messageBO.saveOrUpdate(message);
			}
		}
		return "messages?faces-redirect=true&type=ARCHIVE";
	}
	
}

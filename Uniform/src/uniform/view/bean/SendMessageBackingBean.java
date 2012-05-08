package uniform.view.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import uniform.bo.MessageBO;
import uniform.entity.Message;
import uniform.entity.User;

@ManagedBean
public class SendMessageBackingBean extends AbstractBackingBean {
	
	private String title;
	
	private String content;

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

	public String sendMessageAction(User receiver) {
		MessageBO messageBO = new MessageBO();
		Message message = new Message();
		message.setSender(getUserBean().getCurrentUser());
		message.setReceiver(receiver);
		message.setTitle(title);
		message.setContent(content);
		message.setSentDate(new Date((new Date().getTime())));
		Long newMessageId = messageBO.save(message);
		return "readAndReplyMessage?faces-redirect=true&messageId=" + newMessageId;
	}
	
}

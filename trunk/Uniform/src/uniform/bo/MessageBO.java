package uniform.bo;

import java.util.List;

import uniform.dao.MessageDAO;
import uniform.entity.Message;
import uniform.entity.Reply;
import uniform.entity.User;

public class MessageBO {

	private MessageDAO messageDAO = new MessageDAO();
	
	public void saveOrUpdate(Message message) {
		messageDAO.saveOrUpdate(message);
	}
	
	public Long save(Message message) {
		return messageDAO.save(message);
	}
	
	public void delete(Message message) {
		ReplyBO replyBO = new ReplyBO();
		for (Reply reply : replyBO.getByMessage(message)) {
			replyBO.delete(reply);
		}
		messageDAO.delete(message);
	}
	
	public Message getById(Long id) {
		return messageDAO.getById(id);
	}
		
	public List<Message> getAll() {
		return messageDAO.getAll();
	}
	
	public List<Message> getAllActiveByUser(User user) {
		return messageDAO.getAllActiveByUser(user);
	}
	
	public List<Message> getAllByUser(User user) {
		return messageDAO.getAllByUser(user);
	}

	public List<Message> getAllDeletedByUser(User user) {
		return messageDAO.getAllDeletedByUser(user);
	}
	
	public Long getNotReadedMessagesCountByUser(User user) {
		return messageDAO.getNotReadedMessagesCountByUser(user);
	}
	
}

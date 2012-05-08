package uniform.bo;

import java.util.List;

import uniform.dao.ReplyDAO;
import uniform.entity.Message;
import uniform.entity.Reply;

public class ReplyBO {

	private ReplyDAO replyDAO = new ReplyDAO();
	
	public void saveOrUpdate(Reply reply) {
		replyDAO.saveOrUpdate(reply);
	}
	
	public Long save(Reply reply) {
		return replyDAO.save(reply);
	}
	
	public void delete(Reply reply) {
		replyDAO.delete(reply);
	}
	
	public Reply getById(Long id) {
		return replyDAO.getById(id);
	}
	
	public List<Reply> getByMessage(Message message) {
		return replyDAO.getByMessage(message);
	}
		
	public List<Reply> getAll() {
		return replyDAO.getAll();
	}
	
}

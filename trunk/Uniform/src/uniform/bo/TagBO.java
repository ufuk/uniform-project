package uniform.bo;

import java.util.List;

import uniform.dao.TagDAO;
import uniform.entity.Tag;

public class TagBO {

	private TagDAO tagDAO = new TagDAO();
	
	public void saveOrUpdate(Tag tag) {
		tagDAO.saveOrUpdate(tag);
	}
	
	public void delete(Tag tag) {
		tagDAO.delete(tag);
	}
	
	public Tag getById(Long id) {
		return tagDAO.getById(id);
	}
		
	public List<Tag> getAll() {
		return tagDAO.getAll();
	}
	
}

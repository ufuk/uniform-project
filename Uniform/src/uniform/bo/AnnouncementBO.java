package uniform.bo;

import java.util.List;

import uniform.dao.AnnouncementDAO;
import uniform.entity.Announcement;

public class AnnouncementBO {

	private AnnouncementDAO announcementDAO = new AnnouncementDAO();
	
	public void saveOrUpdate(Announcement announcement) {
		announcementDAO.saveOrUpdate(announcement);
	}
	
	public Long save(Announcement announcement) {
		return announcementDAO.save(announcement);
	}
	
	public void delete(Announcement announcement) {
		announcementDAO.delete(announcement);
	}
	
	public Announcement getById(Long id) {
		return announcementDAO.getById(id);
	}
		
	public List<Announcement> getAll() {
		return announcementDAO.getAll();
	}
	
	public List<Announcement> getAllAndPaginate(int firstResult, int maxResult) {
		return announcementDAO.getAllAndPaginate(firstResult, maxResult);
	}
	
}

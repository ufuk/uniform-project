package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.entity.Announcement;

@ManagedBean
public class AdministerAnnouncementsBackingBean extends AbstractBackingBean {

	private List<Announcement> announcementList = new ArrayList<Announcement>();
	
	public AdministerAnnouncementsBackingBean() {
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcementList = announcementBO.getAll();
	}
	
	public List<Announcement> getAnnouncementList() {
		return announcementList;
	}

	public void setAnnouncementList(List<Announcement> announcementList) {
		this.announcementList = announcementList;
	}
	
	public String deleteAction(Announcement announcement) {
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcementBO.delete(announcement);
		return "administerAnnouncements?faces-redirect=true";
	}

}

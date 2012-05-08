package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.entity.Announcement;

@ManagedBean
public class AnnouncementDetailBackingBean extends AbstractBackingBean {
	
	private Announcement announcement;

	public AnnouncementDetailBackingBean() {
		Long announcementId = Long.valueOf(getRequestParameterMap().get("announcementId"));
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcement = announcementBO.getById(announcementId);
		if (announcement == null) {
			navigateToErrorPage();
		}
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}
	
	public String deleteAction() {
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcementBO.delete(announcement);
		// TODO: redirect somewhere after delete
		return "announcementList?faces-redirect=true";
	}
	
}

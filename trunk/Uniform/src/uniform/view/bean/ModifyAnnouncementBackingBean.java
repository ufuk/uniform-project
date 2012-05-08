package uniform.view.bean;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.entity.Announcement;

@ManagedBean
public class ModifyAnnouncementBackingBean extends AbstractBackingBean {
	
	private Announcement announcement;
	
	private String title;
	
	private String content;
	
	public ModifyAnnouncementBackingBean() {
		Long userId = Long.valueOf(getRequestParameterMap().get("announcementId"));
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcement = announcementBO.getById(userId);
		if (announcement == null) {
			navigateToErrorPage();
		}
	}
	
	public void init() {
		title = announcement.getTitle();
		content = announcement.getContent();
	}
	
	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}
	
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

	public String modifyAction() {
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcement.setTitle(title);
		announcement.setContent(content);
		announcementBO.saveOrUpdate(announcement);
		return "announcementDetail?faces-redirect=true&announcementId=" + announcement.getId();
	}
	
}

package uniform.view.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.entity.Announcement;

@ManagedBean
public class AddAnnouncementBackingBean extends AbstractBackingBean {
	
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
	
	public String addAnnouncementAction() {
		AnnouncementBO announcementBO = new AnnouncementBO();
		Announcement announcement = new Announcement();
		announcement.setTitle(title);
		announcement.setContent(content);
		announcement.setPublishedDate(new Date((new Date()).getTime()));
		Long newAnnouncementId = announcementBO.save(announcement);
		return "announcementDetail?faces-redirect=true&announcementId=" + newAnnouncementId;
	}

}

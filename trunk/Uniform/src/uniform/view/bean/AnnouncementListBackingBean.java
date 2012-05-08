package uniform.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.AnnouncementBO;
import uniform.entity.Announcement;
import uniform.util.paginator.Paginator;

@ManagedBean
public class AnnouncementListBackingBean extends AbstractBackingBean {

	private List<Announcement> announcementList = new ArrayList<Announcement>();
	
	private Paginator paginator;
	
	public AnnouncementListBackingBean() {
		AnnouncementBO announcementBO = new AnnouncementBO();
		announcementList = announcementBO.getAll();
		paginator = new Paginator(announcementList);
	}

	public List<Announcement> getAnnouncementList() {
		return announcementList;
	}

	public void setAnnouncementList(List<Announcement> announcementList) {
		this.announcementList = announcementList;
	}

	public Paginator getPaginator() {
		return paginator;
	}
	
}

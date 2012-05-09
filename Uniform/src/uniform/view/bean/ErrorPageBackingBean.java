package uniform.view.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import uniform.bo.MessageBO;
import uniform.bo.UserBO;
import uniform.entity.Message;
import uniform.entity.User;

@ManagedBean
public class ErrorPageBackingBean extends AbstractBackingBean {

	private String report;

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}
	
	public String reportErrorAction() {
		UserBO userBO = new UserBO();
		for (User systemAdmin : userBO.getSystemAdmins()) {
			if (systemAdmin != null) {
				Message message = new Message();
				message.setSender(getUserBean().getCurrentUser());
				message.setReceiver(systemAdmin);
				message.setTitle("Hata Raporu");
				message.setContent("<b>Açýklama: </b>" + report);
				message.setSentDate(new Date((new Date().getTime())));
				MessageBO messageBO = new MessageBO();
				messageBO.saveOrUpdate(message);
			}
		}
		
		return "mainPage?face-redirect=true";
	}	
	
}

package uniform.view.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletRequest;

import uniform.bo.CategoryBO;
import uniform.bo.MessageBO;
import uniform.bo.UserBO;
import uniform.entity.Article;
import uniform.entity.Category;
import uniform.entity.Message;
import uniform.entity.User;

@ManagedBean
public class ComplainArticleBackingBean extends AbstractBackingBean {

	private String explanation;
	
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String complainArticleAction(Article article) {
		CategoryBO categoryBO = new CategoryBO();
		Category category = categoryBO.getById(article.getCategory().getId());
		if (category.getModerator() != null) {
			Message message = new Message();
			message.setSender(getUserBean().getCurrentUser());
			message.setReceiver(category.getModerator());
			message.setTitle("Þikayet Var: " + article.getTitle());
			
			HttpServletRequest servletRequest = 
					(HttpServletRequest) getFacesContext().getExternalContext().getRequest();
			String uri = servletRequest.getRequestURI() + "?articleId=" + article.getId();
			
			String link = "<a href=\"" + uri + "\">" + article.getTitle() + "</a><br /><br />";
			
			message.setContent(link + "<b>Açýklama: </b>" + explanation);
			message.setSentDate(new Date((new Date().getTime())));
			MessageBO messageBO = new MessageBO();
			messageBO.saveOrUpdate(message);
		}
		
		UserBO userBO = new UserBO();
		for (User systemAdmin : userBO.getSystemAdmins()) {
			if (systemAdmin != null) {
				Message message = new Message();
				message.setSender(getUserBean().getCurrentUser());
				message.setReceiver(systemAdmin);
				message.setTitle("Þikayet Var: " + article.getTitle());
				
				HttpServletRequest servletRequest = 
						(HttpServletRequest) getFacesContext().getExternalContext().getRequest();
				String uri = servletRequest.getRequestURI() + "?articleId=" + article.getId();
				
				String link = "<a href=\"" + uri + "\">" + article.getTitle() + "</a><br /><br />";
				
				message.setContent(link + "<b>Açýklama: </b>" + explanation);
				message.setSentDate(new Date((new Date().getTime())));
				MessageBO messageBO = new MessageBO();
				messageBO.saveOrUpdate(message);
			}
		}
		return "articleDetail?faces-redirect=true&articleId=" + article.getId();
	}

}

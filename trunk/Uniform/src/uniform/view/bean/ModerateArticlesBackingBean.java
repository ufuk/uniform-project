package uniform.view.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import uniform.bo.ArticleBO;
import uniform.bo.MessageBO;
import uniform.entity.Article;
import uniform.entity.Confirmation;
import uniform.entity.Message;

@ManagedBean
public class ModerateArticlesBackingBean extends AbstractBackingBean {
	
	private List<Article> articleList = new ArrayList<Article>();
	
	private String confirmationStatus;
	
	public ModerateArticlesBackingBean() {
		confirmationStatus = getRequestParameterMap().get("confirmationStatus");
		
		ArticleBO articleBO = new ArticleBO();
		
		if (confirmationStatus == null || confirmationStatus.isEmpty()) {
			confirmationStatus = "CONFIRMED";
		}
		if (confirmationStatus.equals("CONFIRMED")
			|| confirmationStatus.equals("NOT_CONFIRMED")
			|| confirmationStatus.equals("PENDING")) {
			if (getUserBean().getCurrentUser().isModerator()) {
				articleList = articleBO.getAllByModerator(Confirmation.valueOf(confirmationStatus),
														  getUserBean().getCurrentUser());
			} else {
				articleList = articleBO.getAllByConfirmation(Confirmation.valueOf(confirmationStatus));
			}
		} else if (confirmationStatus.equals("DELETED")) {
			if (getUserBean().getCurrentUser().isModerator()) {
				articleList = articleBO.getAllDeletedByModerator(getUserBean().getCurrentUser());
			} else {
				articleList = articleBO.getAllDeleted();
			}
		} else {
			navigate("moderateArticles?faces-redirect=true");
		}
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public String getConfirmationStatus() {
		return confirmationStatus;
	}

	public void setConfirmationStatus(String confirmationStatus) {
		this.confirmationStatus = confirmationStatus;
	}
	
	public String confirmArticleAction(Article article) {
		ArticleBO articleBO = new ArticleBO();
		article.setConfirmationStatus(Confirmation.CONFIRMED);
		article.setDeleted(false);
		articleBO.saveOrUpdate(article);
		
		// Send a information message about confirmation
		MessageBO messageBO = new MessageBO();
		Message message = new Message();
		message.setTitle("Makaleniz Yayýnlandý");
		message.setSender(getUserBean().getCurrentUser());
		message.setReceiver(article.getAuthor());
		message.setSentDate(new Date((new Date().getTime())));
		message.setContent("'" + article.getTitle() +
				"' baþlýklý makaleniz yayýna alýnmýþtýr. Katkýlarýnýzdan dolayý teþekkür ederiz.");
		messageBO.saveOrUpdate(message);
		
		return "moderateArticles?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String rejectArticleAction(Article article) {
		ArticleBO articleBO = new ArticleBO();
		article.setConfirmationStatus(Confirmation.NOT_CONFIRMED);
		articleBO.saveOrUpdate(article);
		return "moderateArticles?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String cancelArticleConfirmationAction(Article article) {
		ArticleBO articleBO = new ArticleBO();
		article.setConfirmationStatus(Confirmation.PENDING);
		articleBO.saveOrUpdate(article);
		return "moderateArticles?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String deleteArticleAction(Article article) {
		ArticleBO articleBO = new ArticleBO();
		article.setDeleted(true);
		articleBO.saveOrUpdate(article);
		return "moderateArticles?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
	public String deleteArticlePermanentlyAction(Article article) {
		ArticleBO articleBO = new ArticleBO();
		articleBO.delete(article);
		return "moderateArticles?faces-redirect=true&confirmationStatus=" + confirmationStatus;
	}
	
}

package uniform.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(length=2048)
	private String content;
	
	private Date publishedDate;
	
	@ManyToOne
	private User author;
	
	@ManyToOne
	private Article article;

	private String visitorName;
	
	private String visitorEmail;
	
	private Boolean writtenByVisitor = false;
	
	@Enumerated(EnumType.STRING)
	private Confirmation confirmationStatus = Confirmation.PENDING;
	
	private Boolean deleted = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorEmail() {
		return visitorEmail;
	}

	public void setVisitorEmail(String visitorEmail) {
		this.visitorEmail = visitorEmail;
	}

	public Confirmation getConfirmationStatus() {
		return confirmationStatus;
	}

	public void setConfirmationStatus(Confirmation confirmationStatus) {
		this.confirmationStatus = confirmationStatus;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getWrittenByVisitor() {
		return writtenByVisitor;
	}

	public void setWrittenByVisitor(Boolean writtenByVisitor) {
		this.writtenByVisitor = writtenByVisitor;
	}
	
}

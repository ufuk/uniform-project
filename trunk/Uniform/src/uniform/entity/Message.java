package uniform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	private String content;
	
	private Date sentDate;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User receiver;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Reply> replies = new ArrayList<Reply>();
	
	private Boolean readedByReceiver = false;
	
	private Boolean readedBySender = false;
	
	private Boolean deletedByReceiver = false;
	
	private Boolean deletedBySender = false;
	
	private Boolean deletedPermanentlyByReceiver = false;
	
	private Boolean deletedPermanentlyBySender = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public Boolean getReadedByReceiver() {
		return readedByReceiver;
	}

	public void setReadedByReceiver(Boolean readedByReceiver) {
		this.readedByReceiver = readedByReceiver;
	}

	public Boolean getReadedBySender() {
		return readedBySender;
	}

	public void setReadedBySender(Boolean readedBySender) {
		this.readedBySender = readedBySender;
	}

	public Boolean getDeletedByReceiver() {
		return deletedByReceiver;
	}

	public void setDeletedByReceiver(Boolean deletedByReceiver) {
		this.deletedByReceiver = deletedByReceiver;
	}

	public Boolean getDeletedBySender() {
		return deletedBySender;
	}

	public void setDeletedBySender(Boolean deletedBySender) {
		this.deletedBySender = deletedBySender;
	}

	public Boolean getDeletedPermanentlyByReceiver() {
		return deletedPermanentlyByReceiver;
	}

	public void setDeletedPermanentlyByReceiver(Boolean deletedPermanentlyByReceiver) {
		this.deletedPermanentlyByReceiver = deletedPermanentlyByReceiver;
	}

	public Boolean getDeletedPermanentlyBySender() {
		return deletedPermanentlyBySender;
	}

	public void setDeletedPermanentlyBySender(Boolean deletedPermanentlyBySender) {
		this.deletedPermanentlyBySender = deletedPermanentlyBySender;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	
}

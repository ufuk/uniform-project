package uniform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Article implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length=1024)
	private String title;
	
	// TODO: for mysql, change columnDefinition "CLOB" to "MEDIUMTEXT"
	@Column(columnDefinition="CLOB")
	private String content;
	
	private Date publishedDate;
	
	@ManyToOne
	private User author;
	
	@ManyToMany
	private List<User> likedUsers = new ArrayList<User>();
	
	@ManyToOne
	private Category category;
	
	private Long readingCount = 0L;
	
	/**
	 * rating = totalRating / ratingCount
	 */
	private Double rating = 0.0;
	
	private Long totalRating = 0L;
	
	private Long ratingCount = 0L;
	
	@Enumerated(EnumType.STRING)
	private Confirmation confirmationStatus = Confirmation.PENDING;
	
	private Boolean deleted = false;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getReadingCount() {
		return readingCount;
	}

	public void setReadingCount(Long readingCount) {
		this.readingCount = readingCount;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(Long totalRating) {
		this.totalRating = totalRating;
	}

	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
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

	public List<User> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<User> likedUsers) {
		this.likedUsers = likedUsers;
	}
	
}

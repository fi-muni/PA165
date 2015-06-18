package cz.fi.muni.pa165.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class ProductComment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional=false)
	@NotNull
	private User user;
	
	@ManyToOne(optional=false)
	@NotNull
	private Product product;
	
	@NotNull
	private String text;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Min(1l)
	@Max(5l)
	private Integer starsRating;

	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		product.addComment(this);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		user.addComment(this);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getStarsRating() {
		return starsRating;
	}

	public void setStarsRating(Integer starsRating) {
		this.starsRating = starsRating;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductComment other = (ProductComment) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
}

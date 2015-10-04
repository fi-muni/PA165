package cz.fi.muni.pa165.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import cz.fi.muni.pa165.validation.AllOrNothing;

@Entity
@AllOrNothing(members={"image", "imageMimeType"})
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	@Lob
	private byte[] image;

	private String imageMimeType;
	
	@NotNull
	@Column(nullable=false,unique=true)
	private String name;
	
	/*
	 * The day this item has been added to the eshop
	 */
	@Temporal(TemporalType.DATE)
	private java.util.Date addedDate;
	
	@ManyToMany
	private Set<Category> categories = new HashSet<Category>();


	@OneToOne
	@JoinTable(name="CURRENT_PRICE")
	private Price currentPrice;
	
	@OneToMany()
	@OrderBy("priceStart DESC")
	@JoinColumn(name="Product_FK")
	private List<Price> priceHistory = new ArrayList<Price>();
	
	@Enumerated
	private Color color;

	
	public void setId(Long id){
		this.id = id;
	}
	
	public void removeCategory(Category category)
	{
		this.categories.remove(category);
	}

	public Set<Category> getCategories() {
		return Collections.unmodifiableSet(categories);
	}
	
	public void addCategory(Category c) {
		categories.add(c);
		c.addProduct(this);
	}


	public enum Color{ BLACK, WHITE, RED}
	
	public java.util.Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(java.util.Date addedDate) {
		this.addedDate = addedDate;
	}
	public Product(Long productId) {
		this.id = productId;
	}
	public Product() {
	}
	public byte[] getImage() {
		return image;
	}
	

	public String getImageMimeType() {
		return imageMimeType;
	}



	public void setImageMimeType(String imageMimeType) {
		this.imageMimeType = imageMimeType;
	}



	public Price getCurrentPrice() {
		return currentPrice;
	}


	public void addHistoricalPrice(Price p){
		priceHistory.add(p);
	}
	
	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}


	public List<Price> getPriceHistory() {
		return Collections.unmodifiableList(priceHistory);
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}


	
	
	
}

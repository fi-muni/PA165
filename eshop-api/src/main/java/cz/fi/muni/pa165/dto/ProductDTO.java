package cz.fi.muni.pa165.dto;

import java.util.*;

public class ProductDTO
{
    private Long id;

    private byte[] image;

    private String imageMimeType;

    private String name;

    private String description;

    private Color color;

    private Date addedDate;

    private Set<CategoryDTO> categories = new HashSet<>();

    private List<PriceDTO> priceHistory = new ArrayList<>();
    
    private PriceDTO currentPrice;



    public byte[] getImage() {
        return image;
    }
    
    public Set<CategoryDTO> getCategories() {
        return categories;
    }
    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }
    
    public String getImageMimeType() {
        return imageMimeType;
    }


	public void setCurrentPrice(PriceDTO currentPrice) {
		this.currentPrice = currentPrice;		
	}

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }



    public PriceDTO getCurrentPrice() {
        return currentPrice;
    }



    public List<PriceDTO> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory( List<PriceDTO> priceHistory) {
        this.priceHistory=priceHistory;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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
        if (getClass() != obj.getClass())
            return false;
        ProductDTO other = (ProductDTO) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", imageMimeType='" + imageMimeType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", color=" + color +
                ", addedDate=" + addedDate +
                ", categories=" + categories +
                ", priceHistory=" + priceHistory +
                ", currentPrice=" + currentPrice +
                '}';
    }
}

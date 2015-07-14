package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

public class ProductDTO
{
    private Long id;

    private byte[] image;

    private String imageMimeType;

    private String name;

    private Set<CategoryDTO> categories = new HashSet<CategoryDTO>();

    private List<NewPriceDTO> priceHistory = new ArrayList<NewPriceDTO>();
    
    private NewPriceDTO currentPrice;

    private Color color;

    public enum Color{ BLACK, WHITE, RED}


    public byte[] getImage() {
        return image;
    }
    
    public Set<CategoryDTO> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public void addCategory(CategoryDTO c) {
        categories.add(c);
        c.addProduct(this);
    }

    public String getImageMimeType() {
        return imageMimeType;
    }



    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }



    public NewPriceDTO getCurrentPrice() {
        return currentPrice;
    }


    public void setCurrentPrice(NewPriceDTO currentPrice) {
        if (currentPrice != null)
        	priceHistory.add(currentPrice);

        this.currentPrice = currentPrice;
    }


    public List<NewPriceDTO> getPriceHistory() {
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
}

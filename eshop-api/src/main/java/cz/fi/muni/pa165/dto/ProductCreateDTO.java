package cz.fi.muni.pa165.dto;


import cz.fi.muni.pa165.enums.Currency;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductCreateDTO {
    private byte[] image;

    private String imageMimeType;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 3, max = 500)
    private String description;

    private Color color;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    private Currency currency;

    @NotNull
    private Long categoryId;


    public byte[] getImage() {
        return image;
    }


    public String getImageMimeType() {
        return imageMimeType;
    }


    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        ProductCreateDTO other = (ProductCreateDTO) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductCreateDTO{" +
                ", imageMimeType='" + imageMimeType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", color=" + color +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}

package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

public class ProductCreateDTO
{
    private byte[] image;

    private String imageMimeType;

    private String name;

    private PriceDTO currentPrice;

    private Color color;

    public enum Color{ BLACK, WHITE, RED}


    public byte[] getImage() {
        return image;
    }


    public String getImageMimeType() {
        return imageMimeType;
    }



    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }



    public PriceDTO getCurrentPrice() {
        return currentPrice;
    }


    public void setCurrentPrice(PriceDTO currentPrice) {
 
        this.currentPrice = currentPrice;
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
}

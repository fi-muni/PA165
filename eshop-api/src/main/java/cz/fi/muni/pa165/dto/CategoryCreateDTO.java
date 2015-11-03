package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryCreateDTO that = (CategoryCreateDTO) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CategoryCreateDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}

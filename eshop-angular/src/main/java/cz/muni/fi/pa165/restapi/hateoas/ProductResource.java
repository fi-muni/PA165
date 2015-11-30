package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.dto.PriceDTO;
import cz.fi.muni.pa165.dto.ProductDTO;
import cz.fi.muni.pa165.enums.Currency;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Product rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Relation(value = "product", collectionRelation = "products")
@JsonPropertyOrder({"id", "name", "description", "price", "currency"})
public class ProductResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;

    private String name;
    private String description;
    private Date addedDate;
    private Color color;
    private BigDecimal price;
    private Currency currency;
    private List<PriceDTO> priceHistory;
    private Set<CategoryDTO> categories;

    public ProductResource(ProductDTO dto) {
        dtoId = dto.getId();
        name = dto.getName();
        description = dto.getDescription();
        addedDate = dto.getAddedDate();
        color= dto.getColor();
        price = dto.getCurrentPrice().getValue();
        currency = dto.getCurrentPrice().getCurrency();
        priceHistory = dto.getPriceHistory();
        categories = dto.getCategories();
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public Color getColor() {
        return color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<PriceDTO> getPriceHistory() {
        return priceHistory;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }
}

package br.com.empresa.padaria.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.validations.NameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductDTO extends RepresentationModel<ProductDTO> {

	private UUID id;

	@NotNull(message = "The name field is mandatory.")
	@Size(min = 2, max = 50, message = "Minimum characters allowed are 2 and maximum are 50.")
	@NameConstraint(message = "The name field already exists (not allowed).")
	private String name;

	@NotNull(message = "The description field is mandatory.")
	@Size(min = 2, max = 100, message = "Minimum characters allowed are 2 and maximum are 100.")
	private String description;

	@NotNull(message = "The price field is mandatory.")
	@Min(value = 0, message = "Minimum allowed value is 0.")
	@Max(value = 9999, message = "Maximum value allowed is 9999.")
	private Double price;

	private String imgUrl;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	}

	public ProductDTO(UUID id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	public ProductDTO(Product entity) {
		
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
	}
	
	public ProductDTO(Product entity, List<Category> categories) {
		
		this(entity);
		categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ProductDTO that = (ProductDTO) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id);
	}
}

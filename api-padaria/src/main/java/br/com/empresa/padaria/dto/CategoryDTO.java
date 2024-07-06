package br.com.empresa.padaria.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.entities.Product;

public class CategoryDTO {

	private Long id;
	private String name;
	
	private Set<ProductDTO> products = new HashSet<>();
	
	public CategoryDTO() {
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		
		id = entity.getId();
		name = entity.getName();
	}
	
	public CategoryDTO(Category entity, Set<Product> products) {
		
		this(entity);
		products.forEach(x -> this.products.add(new ProductDTO(x)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProductDTO> getProducts() {
		return products;
	}
	
	
}

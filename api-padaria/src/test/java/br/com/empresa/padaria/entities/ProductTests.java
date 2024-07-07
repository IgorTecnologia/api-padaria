package br.com.empresa.padaria.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTests {

	@Test
	public void ProductShouldHaveCorrectStructure() {
		
		Product entity = new Product();
		
		Category category = new Category();
		
		category.setId(1L);
		category.setName("SmartPhone");
		
		entity.setId(1L);
		entity.setName("A70");
		entity.setPrice(1600.00);
		entity.setDescription("Dual chip");
		entity.setImgUrl("www.img.com");
		
		entity.getCategories().add(category);
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getName());
		Assertions.assertNotNull(entity.getPrice());
		Assertions.assertNotNull(entity.getDescription());
		Assertions.assertNotNull(entity.getImgUrl());
		
		Assertions.assertNotNull(entity.getCategories());
		
	}
}

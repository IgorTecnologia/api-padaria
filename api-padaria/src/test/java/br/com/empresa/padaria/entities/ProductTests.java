package br.com.empresa.padaria.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ProductTests {

	@Test
	public void ProductShouldHaveCorrectStructure() {
		
		Product entity = new Product();
		
		Category category = new Category();

		UUID id = UUID.randomUUID();

		UUID uuid = UUID.randomUUID();

		category.setId(id);
		category.setName("SmartPhone");
		
		entity.setId(uuid);
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

package br.com.empresa.padaria.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTests {

	@Test
	public void CategoryShouldHaveCorrectStructure() {
		
		Category entity = new Category();
		
		Product product = new Product();
		
		product.setId(1L);
		product.setName("Pc gamer");
		product.setPrice(6000.00);
		product.setDescription("Placa de vídeo de ultima geração");
		product.setImgUrl("www.img.com");
		
		entity.setId(1L);
		entity.setName("Computers");
		entity.getProducts().add(product);
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getName());
		Assertions.assertNotNull(entity.getProducts());
		
	}
	
}

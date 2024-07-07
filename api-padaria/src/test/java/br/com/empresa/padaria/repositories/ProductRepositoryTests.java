package br.com.empresa.padaria.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.empresa.padaria.dto.ProductDTO;
import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 4L;
		countTotalElements = 3L;
	}
	
	@Test
	public void findAllPagedShouldReturnAllProducts() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<Product> page = repository.findAll(pageable);
		
		Assertions.assertNotNull(page);
	}

	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {
		
		Optional<Product> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExisting() {
		
		Optional<Product> obj = repository.findById(nonExistingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		Product entity = Factory.createdProduct();
		
		repository.save(entity);
		
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(4L, entity.getId());
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void saveShouldUpdateObjectWhenIdExisting() {
		
		Optional<Product> obj = repository.findById(existingId);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + existingId));
		ProductDTO dto = Factory.createdProductDTO();
		
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(1L, entity.getId());
		Assertions.assertEquals(countTotalElements, repository.count());
	}
	
	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExisting() {
		
		repository.deleteById(existingId);
		
		Optional<Product> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
}

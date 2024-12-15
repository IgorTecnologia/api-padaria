package br.com.empresa.padaria.repositories;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception {

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

		Optional<Product> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		Optional<Product> optional = repository.findById(id);

		Assertions.assertNotNull(optional);
		Assertions.assertTrue(optional.isPresent());
	}

	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		Product entity = Factory.createdProduct();
		
		repository.save(entity);
		
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}

	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExisting() {

		Optional<Product> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		repository.deleteById(id);

		Assertions.assertEquals(countTotalElements -1, repository.count());
	}
}

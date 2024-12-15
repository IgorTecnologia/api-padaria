package br.com.empresa.padaria.repositories;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repository;

	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception{

		countTotalElements = 3L;
	}
	
	@Test
	public void findAllShouldReturnAllCategories() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<Category> page = repository.findAll(pageable);
		
		Assertions.assertNotNull(page);
		
	}

	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {

		Optional<Category> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		Optional<Category> optional = repository.findById(id);

		Assertions.assertNotNull(optional);
		Assertions.assertTrue(optional.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExisting() {

		UUID id = UUID.randomUUID();

		Optional<Category> obj = repository.findById(id);
		
		Assertions.assertTrue(obj.isEmpty());
	}
	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		Category entity = Factory.createdCategory();
		
		repository.save(entity);

		Assertions.assertNotNull(entity);
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void deleteByIdThrowDataIntegrityViolationExceptionWhenIdExisting() {

		Optional<Category> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			repository.deleteById(id);
			throw new DataIntegrityViolationException("Integrity Violation");
		});

	}
}

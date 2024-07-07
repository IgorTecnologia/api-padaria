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

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception{
		
		existingId = 1L;
		nonExistingId = 4L;
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
		
		Optional<Category> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExisting() {
		
		Optional<Category> obj = repository.findById(nonExistingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		Category entity = Factory.createdCategory();
		
		repository.save(entity);
		
		Assertions.assertEquals(4L, entity.getId());
		Assertions.assertEquals(countTotalElements + 1, entity.getId());
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void saveShouldUpdateObjecWhenIdExisting() {
		
		Optional<Category> obj = repository.findById(existingId);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + existingId));
		CategoryDTO dto = Factory.createdCategoryDTO();
		
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		
		repository.save(entity);
		
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(countTotalElements, repository.count());
	}
	
	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExisting() {
		
		repository.deleteById(existingId);
		
		Optional<Category> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
}

package br.com.empresa.padaria.services;

import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.repositories.CategoryRepository;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class CategoryServiceTests {

	@Autowired
	private CategoryServiceImpl service;
	
	@Autowired
	private CategoryRepository repository;

	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception{

		countTotalElements = 3L;
	}
	
	@Test
	public void findAllPagedShouldReturnAllCategories() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<CategoryDTO> page = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(page);
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {

		Optional<Category> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		CategoryDTO dto = service.findById(id);

		Assertions.assertNotNull(dto);
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdNonExisting() {

		UUID id = UUID.randomUUID();

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			CategoryDTO dto = service.findById(id);
			throw new ResourceNotFoundException("Id not found: " + id);
		});
	}
	
	@Test
	public void insertShouldSaveObjectWhenCorrectStructure() {
		
		CategoryDTO dto = Factory.createdCategoryDTO();
		
		dto = service.insert(dto);

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void updateShouldSaveObjectWhenIdExisting() {

		Optional<Category> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		CategoryDTO dto  = Factory.createdCategoryDTO();

		service.update(id, dto);

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(countTotalElements, repository.count());
	}
	
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdNonExisting() {

		UUID id = UUID.randomUUID();

		CategoryDTO dto  = Factory.createdCategoryDTO();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			 service.update(id, dto);
			 throw new ResourceNotFoundException("Id not found: " + id);
		});
	}

	@Test
	public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExistng() {

		UUID id = UUID.randomUUID();

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(id);
			throw new ResourceNotFoundException("Id not found: " + id);
		});
	}
}

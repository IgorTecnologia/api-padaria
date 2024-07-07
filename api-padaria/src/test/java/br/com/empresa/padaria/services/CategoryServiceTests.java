package br.com.empresa.padaria.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.repositories.CategoryRepository;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@SpringBootTest
@Transactional
public class CategoryServiceTests {

	@Autowired
	private CategoryService service;
	
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
	public void findAllPagedShouldReturnAllCategories() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<CategoryDTO> page = service.findAllPaged(pageable);
		
		Assertions.assertFalse(page.isEmpty());
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {
		
		CategoryDTO dto = service.findById(existingId);
		
		Assertions.assertNotNull(dto);
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdNonExisting() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			CategoryDTO dto = service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insertShouldSaveObjectWhenCorrectStructure() {
		
		CategoryDTO dto = Factory.createdCategoryDTO();
		
		dto = service.insert(dto);
		
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void updateShouldSaveObjectWhenIdExisting() {
		
		CategoryDTO dto  = Factory.createdCategoryDTO();
		
		dto = service.update(existingId, dto);
	}
	
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdNonExisting() {
		
		CategoryDTO dto  = Factory.createdCategoryDTO();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			 service.update(nonExistingId, dto);
		});
	}
	
	@Test
	public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExistng() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(nonExistingId);
		});
	}
}

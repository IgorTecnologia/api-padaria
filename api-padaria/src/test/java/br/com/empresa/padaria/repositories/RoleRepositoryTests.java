package br.com.empresa.padaria.repositories;

import java.util.Optional;
import java.util.UUID;

import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repository;

	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception{

		countTotalElements = 3L;
	}
	
	@Test
	public void findAllPagedShouldReturnAllRoles() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<Role> page = repository.findAll(pageable);
		
		Assertions.assertNotNull(page);
		Assertions.assertEquals(countTotalElements, repository.count());
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {

		Optional<Role> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		Optional<Role> optional = repository.findById(id);

		Assertions.assertNotNull(optional);
		Assertions.assertTrue(optional.isPresent());
	}
	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		Role entity = Factory.createdRole();
		
		repository.save(entity);

		Assertions.assertNotNull(entity);
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExisting() {

		Optional<Role> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			repository.deleteById(id);
			throw new DataIntegrityViolationException("Integrity Violation");
		});
	}
}

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

import br.com.empresa.padaria.entities.User;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;

	private Long countTotalElements;
	
	@BeforeEach
	void setUp() throws Exception{

		countTotalElements = 4L;
	}
	
	@Test
	public void findAllPagedShouldReturnAllUsers() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<User> page = repository.findAll(pageable);
		
		Assertions.assertNotNull(page);
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {

		Optional<User> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		Optional<User> optional = repository.findById(id);

		Assertions.assertNotNull(optional);
		Assertions.assertTrue(optional.isPresent());
	}

	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		User entity = Factory.createdUser();
		
		repository.save(entity);
		
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExisting() {

		Optional<User> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		repository.deleteById(id);

		Assertions.assertEquals(countTotalElements -1, repository.count());
	}
}

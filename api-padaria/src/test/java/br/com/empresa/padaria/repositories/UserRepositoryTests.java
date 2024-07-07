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

import br.com.empresa.padaria.dto.UserDTO;
import br.com.empresa.padaria.entities.User;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;
	
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
	public void findAllPagedShouldReturnAllUsers() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<User> page = repository.findAll(pageable);
		
		Assertions.assertNotNull(page);
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {
		
		Optional<User> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExisting() {
		
		Optional<User> obj = repository.findById(nonExistingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		User entity = Factory.createdUser();
		
		repository.save(entity);
		
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(4L, entity.getId());
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void saveShouldUpdateObjectWhenIdExisting() {
		
		Optional<User> obj = repository.findById(existingId);
		
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + existingId));
		
		UserDTO dto = Factory.createdUserDTO();
		
		//entity.setId(dto.getId());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		
		repository.save(entity);
		
		Assertions.assertTrue(obj.isPresent());
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(1L, entity.getId());
		Assertions.assertEquals(countTotalElements, repository.count());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExisting() {
		
		repository.deleteById(existingId);
		
		Optional<User> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
}

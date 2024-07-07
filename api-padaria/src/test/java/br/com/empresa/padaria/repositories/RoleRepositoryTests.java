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

import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.tests.Factory;

@DataJpaTest
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repository;
	
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
	public void findAllPagedShouldReturnAllRoles() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<Role> page = repository.findAll(pageable);
		
		Assertions.assertNotNull(page);
		Assertions.assertEquals(countTotalElements, repository.count());
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExisting() {
		
		Optional<Role> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptinalWhenIdNonExisting() {
		
		Optional<Role> obj = repository.findById(nonExistingId);
		
		Assertions.assertFalse(obj.isPresent());
	}
	
	@Test
	public void saveShouldInsertObjectWhenCorrectStructure() {
		
		Role entity = Factory.createdRole();
		
		repository.save(entity);
		
		Assertions.assertEquals(countTotalElements + 1, repository.count());
	}
	
	@Test
	public void saveShouldUpdateObjectWhenCorrectStructure() {
		
		RoleDTO dto = Factory.createdRoleDTO();
		Role entity = Factory.createdRole();
		
		entity.setId(dto.getId());
		entity.setAuthority(dto.getAuthority());
		
		repository.save(entity);
		
		Assertions.assertEquals(countTotalElements, repository.count());
		Assertions.assertEquals(1L, entity.getId());
	}
	
	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExisting() {
		
		repository.deleteById(existingId);
		
		Optional<Role> obj = repository.findById(existingId);
		
		Assertions.assertTrue(obj.isEmpty());
	}
}

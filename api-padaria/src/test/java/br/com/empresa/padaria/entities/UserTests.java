package br.com.empresa.padaria.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserTests {

	@Test
	public void UserShouldHaveCorrectStructure() {
		
		User entity = new User();
		
		Role role = new Role();

		UUID id = UUID.randomUUID();

		UUID uuid = UUID.randomUUID();

		role.setId(id);
		role.setAuthority("Manager");
		
		entity.setId(uuid);
		entity.setFirstName("Igor");
		entity.setLastName("Gonçalves");
		entity.setEmail("igor@gmail.com");
		entity.setPassword("123456");
		
		entity.getRoles().add(role);
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getFirstName());
		Assertions.assertNotNull(entity.getLastName());
		Assertions.assertNotNull(entity.getEmail());
		Assertions.assertNotNull(entity.getPassword());
		
		Assertions.assertNotNull(entity.getRoles());




	}
}

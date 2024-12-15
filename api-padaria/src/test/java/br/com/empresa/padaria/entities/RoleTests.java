package br.com.empresa.padaria.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RoleTests {

	@Test
	public void RoleShouldHaveCorrectStructure() {
		
		Role entity = new Role();

		UUID id = UUID.randomUUID();

		entity.setId(id);
		entity.setAuthority("Assistent");
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getAuthority());
		
	}
	
}

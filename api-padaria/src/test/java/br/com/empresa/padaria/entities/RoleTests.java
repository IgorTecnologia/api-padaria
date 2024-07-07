package br.com.empresa.padaria.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTests {

	@Test
	public void RoleShouldHaveCorrectStructure() {
		
		Role entity = new Role();
		
		entity.setId(1L);
		entity.setAuthority("Assistent");
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getAuthority());
		
	}
	
}

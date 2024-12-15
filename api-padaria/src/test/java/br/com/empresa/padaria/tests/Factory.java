package br.com.empresa.padaria.tests;

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.dto.ProductDTO;
import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.dto.UserDTO;
import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.entities.User;

import java.util.UUID;

public class Factory {

	public static Category createdCategory() {

		UUID id = UUID.randomUUID();

		Category entity = new Category(id, "Frios");
		return entity;
	}

	public static CategoryDTO createdCategoryDTO() {

		UUID id = UUID.randomUUID();

		CategoryDTO dto = new CategoryDTO(id, "Frios");
		return dto;

	}

	public static Product createdProduct() {

		UUID id = UUID.randomUUID();

		Product entity = new Product(id, "Mortadela", "Defumada", 6.0, "www.img.com");
		return entity;
	}

	public static ProductDTO createdProductDTO() {

		UUID id = UUID.randomUUID();

		ProductDTO dto = new ProductDTO(id, "Mortadela", "Defumada", 6.0, "www.img.com");
		
		return dto;
	}
	
	public static User createdUser() {

		UUID id = UUID.randomUUID();

		User entity = new User(id, "Pedro", "Silva", "pedro@gmail.com", "123456");
		
		return entity;
	}
	
	public static UserDTO createdUserDTO() {

		UUID id = UUID.randomUUID();

		UserDTO dto = new UserDTO(id, "Pedro", "Silva", "pedro@gmail.com", "123456");
		
		return dto;
	}

	public static UserDTO createdUserDtoToUpdate() {

		UUID id = UUID.randomUUID();

		UserDTO dto = new UserDTO(id, "Lucas", "Silva", "lucas@gmail.com", "123456");

		return dto;
	}
	
	public static Role createdRole() {

		UUID id = UUID.randomUUID();

		Role entity = new Role(id, "Manager");
		
		return entity;
	}
	
	public static RoleDTO createdRoleDTO() {

		UUID id = UUID.randomUUID();

		RoleDTO dto = new RoleDTO(id, "CEO");
		
		return dto;
	}

	public static RoleDTO createdRoleDtoToUpdate() {

		UUID id = UUID.randomUUID();

		RoleDTO dto = new RoleDTO(id, "Assistent");

		return dto;
	}
}

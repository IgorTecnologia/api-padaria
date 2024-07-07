package br.com.empresa.padaria.tests;

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.dto.ProductDTO;
import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.dto.UserDTO;
import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.entities.User;

public class Factory {

	public static Category createdCategory() {

		Category entity = new Category(null, "Frios");
		return entity;
	}

	public static CategoryDTO createdCategoryDTO() {

		CategoryDTO dto = new CategoryDTO(1L, "Frios");
		return dto;

	}

	public static Product createdProduct() {

		Product entity = new Product(null, "Mortadela", "Defumada", 6.0, "www.img.com");
		return entity;
	}

	public static ProductDTO createdProductDTO() {
		
		ProductDTO dto = new ProductDTO(1L, "Mortadela", "Defumada", 6.0, "www.img.com");
		
		return dto;
	}
	
	public static User createdUser() {
		
		User entity = new User(null, "Pedro", "Silva", "pedro@gmail.com", "123456");
		
		return entity;
	}
	
	public static UserDTO createdUserDTO() {
		
		UserDTO dto = new UserDTO(1L, "Pedro", "Silva", "pedro@gmail.com", "123456");
		
		return dto;
	}
	
	public static Role createdRole() {
		
		Role entity = new Role(null, "Manager");
		
		return entity;
	}
	
	public static RoleDTO createdRoleDTO() {
		
		RoleDTO dto = new RoleDTO(1L, "Manager");
		
		return dto;
	}
}

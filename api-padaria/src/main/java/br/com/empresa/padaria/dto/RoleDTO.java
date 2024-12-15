package br.com.empresa.padaria.dto;

import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.validations.AuthorityConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RoleDTO {

	private UUID id;

	@NotNull(message = "The authority field is mandatory.")
	@Size(min = 2, max = 30, message = "Minimum characters allowed are 2 e maximum are 30.")
	@AuthorityConstraint(message = "The authority already exists (not allowed).")
	private String authority;
	
	public RoleDTO() {
	}

	public RoleDTO(UUID id, String authority) {
		this.id = id;
		this.authority = authority;
	}

	public RoleDTO(Role entity) {
		
		id = entity.getId();
		authority = entity.getAuthority();
	}
}

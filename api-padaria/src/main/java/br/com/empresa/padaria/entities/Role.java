package br.com.empresa.padaria.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String authority;
	
	public Role() {
	}

	public Role(UUID id, String authority) {
		this.id = id;
		this.authority = authority;
	}
}

package br.com.empresa.padaria.resources;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.services.impl.RoleServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping(value = "/roles")
public class RoleResource {

	@Autowired
	private RoleServiceImpl service;
	
	@GetMapping
	public ResponseEntity<Page<RoleDTO>> findAllPaged(Pageable pageable){
		
		Page<RoleDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/authority/{authority}")
	public ResponseEntity<Page<RoleDTO>> queryMethod(@PathVariable String authority, Pageable pageable){
		
		Page<RoleDTO> page = service.queryMethod(authority, pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<RoleDTO> findById(@PathVariable UUID id){
		
		RoleDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<RoleDTO> insert(@RequestBody @Valid RoleDTO dto){
		
		dto = service.insert(dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<RoleDTO> update(@PathVariable UUID id, @RequestBody @Valid RoleDTO dto){
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		
		service.deleteById(id);
		return ResponseEntity.ok().body("Role deleted successfully.");
	}
}

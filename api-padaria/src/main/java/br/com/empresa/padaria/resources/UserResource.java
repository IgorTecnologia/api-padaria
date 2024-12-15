package br.com.empresa.padaria.resources;

import br.com.empresa.padaria.specification.SpecificationTemplate;
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

import br.com.empresa.padaria.dto.UserDTO;
import br.com.empresa.padaria.services.impl.UserServiceImpl;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserServiceImpl service;
	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllPaged(SpecificationTemplate.UserSpec spec, Pageable pageable){
		
		Page<UserDTO> page = service.findAllPaged(spec, pageable);
		if(!page.isEmpty()){
			for(UserDTO dto : page.toList()){
				dto.add(linkTo(methodOn(UserResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/firstName/{firstName}")
	public ResponseEntity<Page<UserDTO>> queryMethod(@PathVariable String firstName, Pageable pageable){
		
		Page<UserDTO> page = service.queryMethod(firstName, pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable UUID id){
		
		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserDTO dto){
		
		dto = service.insert(dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody @Valid UserDTO dto){
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		
		service.deleteById(id);
		return ResponseEntity.ok().body("User deleted successfully.");
	}
}

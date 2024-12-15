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

import br.com.empresa.padaria.dto.ProductDTO;
import br.com.empresa.padaria.services.impl.ProductServiceImpl;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductServiceImpl service;
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllPaged(Pageable pageable){
		
		Page<ProductDTO> page = service.findAllPaged(pageable);
		if(!page.isEmpty()){
			for(ProductDTO dto : page.toList()){
				dto.add(linkTo(methodOn(ProductResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Page<ProductDTO>> queryMethod(@PathVariable String name, Pageable pageable){
		
		Page<ProductDTO> page = service.queryMethod(name, pageable);
		return ResponseEntity.ok().body(page);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable UUID id){
		
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody @Valid ProductDTO dto){
		
		dto = service.insert(dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable UUID id, @RequestBody @Valid ProductDTO dto){
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		
		service.deleteById(id);
		return ResponseEntity.ok().body("Product deleted successfully.");
	}
}

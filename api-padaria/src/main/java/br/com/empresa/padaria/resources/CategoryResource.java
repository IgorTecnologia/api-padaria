package br.com.empresa.padaria.resources;

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

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAllPaged(Pageable pageable){
		
		Page<CategoryDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Page<CategoryDTO>> queryMethod(Pageable pageable, @PathVariable String name){
		
		Page<CategoryDTO> page = service.queryMethod(pageable, name);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
		
		dto = service.insert(dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto){
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

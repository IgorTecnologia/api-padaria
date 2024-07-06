package br.com.empresa.padaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.padaria.dto.CategoryDTO;
import br.com.empresa.padaria.dto.ProductDTO;
import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.repositories.CategoryRepository;
import br.com.empresa.padaria.repositories.ProductRepository;
import br.com.empresa.padaria.services.exceptions.DataBaseException;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {

		Page<Category> page = repository.findAll(pageable);
		return page.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> queryMethod(Pageable pageable, String name){
		
		Page<Category> page = repository.findAllByNameContainingIgnoreCase(name, pageable);
		return page.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {

		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new CategoryDTO(entity, entity.getProducts());
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new CategoryDTO(entity, entity.getProducts());
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {

			Optional<Category> obj = repository.findById(id);
			Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
			copyDtoToEntity(entity, dto);
			repository.save(entity);
			return new CategoryDTO(entity, entity.getProducts());
	}

	public void deleteById(Long id) {

		Optional<Category> category = repository.findById(id);
		try {
			if (category.isEmpty())
				throw new ResourceNotFoundException("Id not found: " + id);
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(Category entity, CategoryDTO dto) {

		entity.setName(dto.getName());

		entity.getProducts().clear();

		for (ProductDTO proDto : dto.getProducts()) {
			@SuppressWarnings("deprecation")
			Product product = productRepository.getOne(proDto.getId());
			entity.getProducts().add(product);
		}
	}
}

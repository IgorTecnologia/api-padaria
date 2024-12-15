package br.com.empresa.padaria.services.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.empresa.padaria.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> page = repository.findAll(pageable);
		return page.map(x -> new ProductDTO(x, x.getCategories()));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ProductDTO> queryMethod(String name, Pageable pageable){
		
		Page<Product> page = repository.findAllByNameContainingIgnoreCase(name, pageable);
		return page.map(x -> new ProductDTO(x, x.getCategories()));
	}
	
	@Transactional(readOnly = true)
	@Override
	public ProductDTO findById(UUID id) {

		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	@Override
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	@Override
	public ProductDTO update(UUID id, ProductDTO dto) {

		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ProductDTO(entity, entity.getCategories());

	}

	public void deleteById(UUID id) {

			Optional<Product> obj = repository.findById(id);
			if (obj.isEmpty()) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}

			repository.deleteById(id);
		}


	public void copyDtoToEntity(Product entity, ProductDTO dto) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());

		entity.getCategories().clear();

		if(dto.getCategories() != null && !dto.getCategories().isEmpty()){
			for(CategoryDTO catDto : dto.getCategories()){
				if(catDto.getId() != null){
					Optional<Category> obj = categoryRepository.findById(catDto.getId());
					Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + catDto.getId()));
					entity.getCategories().add(category);
					category.getProducts().add(entity);
				}else{

					Category newCategory = new Category();
					newCategory.setName(catDto.getName());
					entity.getCategories().add(newCategory);
					newCategory.getProducts().add(entity);
				}
			}
		}
	}
}



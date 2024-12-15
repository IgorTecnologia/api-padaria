package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryService {

    Page<CategoryDTO> findAllPaged(Pageable pageable);

    Page<CategoryDTO> queryMethod(String name, Pageable pageable);

    CategoryDTO findById(UUID id);

    CategoryDTO insert(CategoryDTO dto);

    CategoryDTO update(UUID id, CategoryDTO dto);

    void deleteById(UUID id);
}

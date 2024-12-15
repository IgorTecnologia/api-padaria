package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {


    Page<ProductDTO> findAllPaged(Pageable pageable);

    Page<ProductDTO> queryMethod(String name, Pageable pageable);

    ProductDTO findById(UUID id);

    ProductDTO insert(ProductDTO dto);

    ProductDTO update(UUID id, ProductDTO dto);

    void deleteById(UUID id);
}

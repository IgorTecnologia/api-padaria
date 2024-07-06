package br.com.empresa.padaria.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.padaria.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findAllByNameContainingIgnoreCase(@Param ("name") String name, Pageable pageable);
}

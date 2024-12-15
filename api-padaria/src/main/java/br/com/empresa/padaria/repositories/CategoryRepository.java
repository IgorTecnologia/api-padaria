package br.com.empresa.padaria.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.padaria.entities.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID>{
	
    Page<Category> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    boolean existsByName(String name);
}

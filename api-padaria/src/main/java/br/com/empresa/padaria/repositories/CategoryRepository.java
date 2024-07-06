package br.com.empresa.padaria.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empresa.padaria.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	public Page<Category> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}

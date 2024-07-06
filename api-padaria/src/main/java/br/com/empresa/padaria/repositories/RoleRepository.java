package br.com.empresa.padaria.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.padaria.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	public Page<Role> findAllByAuthorityContainingIgnoreCase(@Param ("authority") String authority, Pageable pageable);
}

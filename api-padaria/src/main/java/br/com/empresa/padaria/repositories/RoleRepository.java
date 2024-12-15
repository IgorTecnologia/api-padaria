package br.com.empresa.padaria.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.padaria.entities.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	Page<Role> findAllByAuthorityContainingIgnoreCase(@Param ("authority") String authority, Pageable pageable);

    boolean existsByAuthority(String authority);
}

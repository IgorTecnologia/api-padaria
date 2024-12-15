package br.com.empresa.padaria.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.padaria.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{

    Page<User> findAllByFirstNameContainingIgnoreCase(@Param ("firstName") String firstName, Pageable pageable);

    boolean existsByEmail(String email);
}

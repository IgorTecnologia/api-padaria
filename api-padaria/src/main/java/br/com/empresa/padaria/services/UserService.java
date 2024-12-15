package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.UserDTO;
import br.com.empresa.padaria.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAllPaged(SpecificationTemplate.UserSpec spec, Pageable pageable);

    Page<UserDTO> queryMethod(String firstName, Pageable pageable);

    UserDTO findById(UUID id);

    UserDTO insert(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    void deleteById(UUID id);
}

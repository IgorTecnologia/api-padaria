package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    Page<RoleDTO> findAllPaged(Pageable pageable);

    Page<RoleDTO> queryMethod(String authority, Pageable pageable);

    RoleDTO findById(UUID id);

    RoleDTO insert(RoleDTO dto);

    RoleDTO update(UUID id, RoleDTO dto);

    void deleteById(UUID id);
}

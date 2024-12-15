package br.com.empresa.padaria.services.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.empresa.padaria.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.repositories.RoleRepository;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

	@Transactional(readOnly = true)
	@Override
	public Page<RoleDTO> findAllPaged(Pageable pageable) {

		Page<Role> page = repository.findAll(pageable);
		return page.map(RoleDTO::new);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<RoleDTO> queryMethod(String authority, Pageable pageable){
		
		Page<Role> page = repository.findAllByAuthorityContainingIgnoreCase(authority, pageable);
		return page.map(RoleDTO::new);
	}
	
	@Transactional(readOnly = true)
	@Override
	public RoleDTO findById(UUID id) {

		Optional<Role> obj = repository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new RoleDTO(entity);
	}

	@Transactional
	@Override
	public RoleDTO insert(RoleDTO dto) {

		Role entity = new Role();
		entity.setAuthority(dto.getAuthority());
		repository.save(entity);
		return new RoleDTO(entity);
	}

	@Transactional
	@Override
	public RoleDTO update(UUID id, RoleDTO dto) {

		Optional<Role> obj = repository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		entity.setAuthority(dto.getAuthority());
		repository.save(entity);
		return new RoleDTO(entity);
	}

	@Override
	public void deleteById(UUID id) {

			Optional<Role> obj = repository.findById(id);
			if (obj.isEmpty()) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}

			repository.deleteById(id);
	}

}

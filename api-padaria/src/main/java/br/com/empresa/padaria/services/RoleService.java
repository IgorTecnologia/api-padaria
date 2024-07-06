package br.com.empresa.padaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.repositories.RoleRepository;
import br.com.empresa.padaria.services.exceptions.DataBaseException;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	@Transactional(readOnly = true)
	public Page<RoleDTO> findAllPaged(Pageable pageable) {

		Page<Role> page = repository.findAll(pageable);
		return page.map(x -> new RoleDTO(x));
	}

	@Transactional(readOnly = true)
	public Page<RoleDTO> queryMethod(String authority, Pageable pageable){
		
		Page<Role> page = repository.findAllByAuthorityContainingIgnoreCase(authority, pageable);
		return page.map(x -> new RoleDTO(x));
	}
	
	@Transactional(readOnly = true)
	public RoleDTO findById(Long id) {

		Optional<Role> obj = repository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new RoleDTO(entity);
	}

	@Transactional
	public RoleDTO insert(RoleDTO dto) {

		Role entity = new Role();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new RoleDTO(entity);
	}

	@Transactional
	public RoleDTO update(Long id, RoleDTO dto) {

		Optional<Role> obj = repository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		entity.setAuthority(dto.getAuthority());
		repository.save(entity);
		return new RoleDTO(entity);
	}

	public void deleteById(Long id) {
		try {
			Optional<Role> obj = repository.findById(id);

			if (obj.isEmpty()) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}

			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(Role entity, RoleDTO dto) {

		entity.setAuthority(dto.getAuthority());
	}
}

package br.com.empresa.padaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.padaria.dto.RoleDTO;
import br.com.empresa.padaria.dto.UserDTO;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.entities.User;
import br.com.empresa.padaria.repositories.RoleRepository;
import br.com.empresa.padaria.repositories.UserRepository;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable){
		
		Page<User> page = repository.findAll(pageable);
		return page.map(x -> new UserDTO(x, x.getRoles()));
	}
	
	@Transactional(readOnly = true)
	public Page<UserDTO> queryMethod(String firstName, Pageable pageable){
		
		Page<User> page = repository.findAllByFirstNameContainingIgnoreCase(firstName, pageable);
		return page.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new UserDTO(entity, entity.getRoles());
	}
	
	@Transactional
	public UserDTO insert(UserDTO dto) {
		
		User entity = new User();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new UserDTO(entity, entity.getRoles());
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new UserDTO(entity, entity.getRoles());
	}
	
	public void deleteById(Long id) {
		
		Optional<User> obj = repository.findById(id);
		repository.deleteById(id);
		if(obj.isEmpty()) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	
	public void copyDtoToEntity(User entity, UserDTO dto) {
		
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		
		entity.getRoles().clear();
		
		for(RoleDTO roleDto : dto.getRoles()) {
			@SuppressWarnings("deprecation")
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}
	}
}

package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.repositories.*;
import br.com.empresa.padaria.services.exceptions.*;
import br.com.empresa.padaria.services.impl.RoleServiceImpl;
import br.com.empresa.padaria.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class RoleServiceTests {

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        countTotalElements = 3L;
    }

    @Test
    public void findAllPagedShouldReturnAllRoles(){

        Pageable pageable = PageRequest.of(0, 12);

        Page<RoleDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
}

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
}

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotFound(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
}

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        RoleDTO dto = Factory.createdRoleDTO();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(countTotalElements +1, repository.count());
}

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDTO();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(countTotalElements, repository.count());
}

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        RoleDTO dto = Factory.createdRoleDTO();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
}

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
}
}

package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.*;
import br.com.empresa.padaria.repositories.*;
import br.com.empresa.padaria.services.exceptions.*;
import br.com.empresa.padaria.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

@SpringBootTest
@Transactional
public class RoleServiceTests {

    @Autowired
    private RoleService service;

    @Autowired
    private RoleRepository repository;

private Long existingId;
private Long nonExistingId;
private Long countTotalElements;

@BeforeEach
void setUp() throws Exception{

    existingId = 1L;
    nonExistingId = 4L;
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

    RoleDTO dto = service.findById(existingId);

    Assertions.assertNotNull(dto);
}

@Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotFound(){

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
        service.findById(nonExistingId);
    });
}

@Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

    RoleDTO dto = Factory.createdRoleDTO();

    dto = service.insert(dto);

    Assertions.assertEquals(countTotalElements +1, repository.count());
}

@Test
    public void updateShouldSaveObjectWhenIdExisting(){

    RoleDTO dto = Factory.createdRoleDTO();

    dto = service.update(existingId, dto);

    Assertions.assertEquals(countTotalElements, repository.count());
}

@Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

    RoleDTO dto = Factory.createdRoleDTO();

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
        service.update(nonExistingId, dto);

    });
}

@Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
        service.deleteById(nonExistingId);
    });
}
}

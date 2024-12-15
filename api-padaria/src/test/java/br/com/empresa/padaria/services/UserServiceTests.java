package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.User;
import br.com.empresa.padaria.repositories.*;
import br.com.empresa.padaria.services.exceptions.*;
import br.com.empresa.padaria.services.impl.UserServiceImpl;
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
public class UserServiceTests {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        countTotalElements = 4L;
    }

    @Test
    public void findAllPagedShouldReturnAllUsers(){

        Pageable pageable = PageRequest.of(0, 12);

        Page<UserDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        UserDTO dto = Factory.createdUserDTO();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDTO();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(countTotalElements, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        UserDTO dto = Factory.createdUserDTO();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " +id);
        });
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(countTotalElements -1, repository.count());
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

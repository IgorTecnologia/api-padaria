package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.Product;
import br.com.empresa.padaria.repositories.*;
import br.com.empresa.padaria.services.exceptions.*;
import br.com.empresa.padaria.services.impl.ProductServiceImpl;
import br.com.empresa.padaria.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Transactional
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductRepository repository;

    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        countTotalElements = 3L;
    }

    @Test
    public void findAllPagedShouldReturnAllProducts(){

        Pageable pageable = PageRequest.of(0, 12);

        Page<ProductDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ProductDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        ProductDTO dto = Factory.createdProductDTO();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(countTotalElements + 1, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ProductDTO dto = Factory.createdProductDTO();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(countTotalElements, repository.count());
    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        ProductDTO dto = Factory.createdProductDTO();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
             service.update(id, dto);
             throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();
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

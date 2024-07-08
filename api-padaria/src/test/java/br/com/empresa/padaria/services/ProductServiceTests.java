package br.com.empresa.padaria.services;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.repositories.*;
import br.com.empresa.padaria.services.exceptions.*;
import br.com.empresa.padaria.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

@Transactional
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long existingInd;
    private Long nonExistingId;
    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        existingInd = 1L;
        nonExistingId = 4L;
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

        ProductDTO dto = service.findById(existingInd);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(1L, dto.getId());
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdNonExisting(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        ProductDTO dto = Factory.createdProductDTO();

        dto = service.insert(dto);

        Assertions.assertEquals(countTotalElements + 1, repository.count());
        Assertions.assertEquals(4L, dto.getId());

    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        ProductDTO dto = Factory.createdProductDTO();

        dto = service.update(existingInd, dto);

        Assertions.assertEquals(countTotalElements, repository.count());
    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenIdNonExisting(){

        ProductDTO dto = Factory.createdProductDTO();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
             service.update(nonExistingId, dto);
        });
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

      service.deleteById(existingInd);

      Assertions.assertEquals(countTotalElements -1, repository.count());
    }

    @Test
    public void deleteByIdShouldReturnResourceNotFoundExceptionWhenIdNonExisting(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(nonExistingId);
        });
    }
}

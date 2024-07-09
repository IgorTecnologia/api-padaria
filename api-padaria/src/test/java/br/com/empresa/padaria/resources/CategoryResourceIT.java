package br.com.empresa.padaria.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.transaction.annotation.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CategoryResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void findAllPagedShouldReturnAllCategoriesSortByName() throws Exception{

        ResultActions result = mockMvc.perform(get("/categories?sort=name")
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").value(countTotalElements));
                    result.andExpect(jsonPath("$.content[0].name").value("Bebidas"));
                    result.andExpect(jsonPath("$.content[1].name").value("Doces"));
                    result.andExpect(jsonPath("$.content[2].name").value("Salgados"));
    }

    @Test
    public void findByIdShouldReturnObjectWhenExistingId() throws Exception{

        ResultActions result = mockMvc.perform(get("/categories/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/categories/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
                result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception{

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/categories")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception{

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);


        ResultActions result = mockMvc.perform(post("/categories", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception{

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/categories/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception{

        ResultActions result = mockMvc.perform(delete("/categories/{id}", existingId));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExistingId() throws Exception{

        ResultActions result = mockMvc.perform(delete("/categories/{id}", nonExistingId));

        result.andExpect(status().isNotFound());
    }
}

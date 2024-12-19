package br.com.empresa.padaria.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.Category;
import br.com.empresa.padaria.repositories.CategoryRepository;
import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import br.com.empresa.padaria.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void findAllPagedShouldReturnAllCategoriesSortByName() throws Exception{

        ResultActions result = mockMvc.perform(get("/categories?sort=name")
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.content[0].name").value("Bebidas"));
                    result.andExpect(jsonPath("$.content[1].name").value("Doces"));
                    result.andExpect(jsonPath("$.content[2].name").value("Salgados"));
    }

    @Test
    public void findByIdShouldReturnObjectWhenExistingId() throws Exception{

        Optional<Category> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/categories/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception{

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/categories/{id}", id)
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

        Optional<Category> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);


        ResultActions result = mockMvc.perform(put("/categories/{id}", id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception{

        UUID id = UUID.randomUUID();

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/categories/{id}", id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception{

        Optional<Category> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/categories/{id}", id));

        result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExistingId() throws Exception{

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/categories/{id}", id));

        result.andExpect(status().isNotFound());
    }
}

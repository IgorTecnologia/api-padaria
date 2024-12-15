package br.com.empresa.padaria.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.*;
import br.com.empresa.padaria.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existindId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception{

        existindId = 1L;
        nonExistingId = 4L;
    }

    @Test
    public void findAllPagedShouldReturnAllUsersSortByFirstName() throws Exception {

        ResultActions result = mockMvc.perform(get("/users?sort=firstName")
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.content").exists());
                result.andExpect(jsonPath("$.content[0].id").value(3L));
                result.andExpect(jsonPath("$.content[0].firstName").value("Igor"));
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/{id}", existindId)
                .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isOk());

            result.andExpect(jsonPath("$.id").exists());
            result.andExpect(jsonPath("$.firstName").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/{id}", nonExistingId));
            result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception{

        UserDTO dto = Factory.createdUserDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        UserDTO dto = Factory.createdUserDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

            ResultActions result = mockMvc.perform(put("/users/{id}", existindId)
                    .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON));
                                result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.id").exists());
                    result.andExpect(jsonPath("$.firstName").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        UserDTO dto = Factory.createdUserDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", nonExistingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

            Long id = 2L;
        ResultActions result = mockMvc.perform(delete("/users/{id}", id ));
                result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        Long id = 5L;

        ResultActions result = mockMvc.perform(delete("/users/{id}", id));
                result.andExpect(status().isNotFound());

    }
}


package br.com.empresa.padaria.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.tests.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws  Exception{

        existingId = 1L;
        nonExistingId = 4L;
    }

    @Test
    public void findAllPagedShouldReturnAllRolesSortByAuthority() throws Exception{


        ResultActions result = mockMvc.perform(get("/roles?sort=authority")
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].authority").exists());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        RoleDTO dto = Factory.createdRoleDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/roles")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        RoleDTO dto = Factory.createdRoleDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", existingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNonExistingId() throws Exception {

        RoleDTO dto = Factory.createdRoleDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", nonExistingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/roles/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/roles/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());

    }
}

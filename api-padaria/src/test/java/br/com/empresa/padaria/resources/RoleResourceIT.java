package br.com.empresa.padaria.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.padaria.dto.*;
import br.com.empresa.padaria.entities.Role;
import br.com.empresa.padaria.repositories.RoleRepository;
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
public class RoleResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository repository;

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

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/roles/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/roles/{id}", id)
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

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenNonExistingId() throws Exception {

        UUID id = UUID.randomUUID();

        RoleDTO dto = Factory.createdRoleDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/roles/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/roles/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());

    }
}

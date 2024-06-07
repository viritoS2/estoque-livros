package com.exemplo.estoque.livros.demo.controller.users;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class DeleteUserByIdControllerTests {
    @InjectMocks
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteUserById() throws Exception {
        when(userRepository.existsById(any())).thenReturn(true);
        mvc.perform(delete("/users/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Usuário deletado com sucesso")));
    }

    @Test
    public void testDeleteUserByIdUserNotFoundError() throws Exception {
        when(userRepository.existsById(any())).thenReturn(false);

        mvc.perform(delete("/users/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Não encontrado"))
                .andExpect(jsonPath("$.detail").value("Usuário não encontrado"))
                .andExpect(jsonPath("$.instance").value("/users/2"))
                .andExpect(jsonPath("$.Categoria").value("Users"));
    }

    @Test
    public void testDeleteUserByIdInternalError() throws Exception {
        when(userRepository.existsById(any())).thenThrow(new RuntimeException("Internal server error"));
        mvc.perform(delete("/users/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/users/1"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

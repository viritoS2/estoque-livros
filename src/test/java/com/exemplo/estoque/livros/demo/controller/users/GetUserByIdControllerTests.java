package com.exemplo.estoque.livros.demo.controller.users;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.handlers.user.UserNotFoundException;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class GetUserByIdControllerTests {

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
    public void testGeUserByIDSuccess() throws Exception {
        User user = new User(new DadosDeCadastroUser(90L, "Vitor", "vitor@gmail.com"));

        when(userRepository.findById(90L)).thenReturn(Optional.of(user));

        mvc.perform(get("/users/{id}", 90)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vitor"))
                .andExpect(jsonPath("$.email").value("vitor@gmail.com"));
    }

    @Test
    public void testGeUserByIDNotFoundError() throws Exception {

        when(userRepository.findById(1L)).thenThrow(new UserNotFoundException("Usuário não encontrado"));

        mvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Não encontrado"))
                .andExpect(jsonPath("$.detail").value("Usuário não encontrado"))
                .andExpect(jsonPath("$.instance").value("/users/1"))
                .andExpect(jsonPath("$.Categoria").value("Users"));
    }

    @Test
    public void testGeUserByIdInternalServerError() throws Exception {
        when(userRepository.findById(any())).thenThrow(new RuntimeException("Internal server error"));
        mvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.detail").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/users/1"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

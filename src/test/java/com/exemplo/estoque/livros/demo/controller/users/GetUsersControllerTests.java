package com.exemplo.estoque.livros.demo.controller.users;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class GetUsersControllerTests {

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
    public void testGetUsers() throws Exception {
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(new User(new DadosDeCadastroUser(90L, "Vitor", "vitor@gmail.com")));
        listOfUsers.add(new User(new DadosDeCadastroUser(40L, "Erlando", "erlando@gmail.com")));
        when(userRepository.findAll()).thenReturn(listOfUsers);
        mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Vitor"))
                .andExpect(jsonPath("$[0].email").value("vitor@gmail.com"))
                .andExpect(jsonPath("[1].name").value("Erlando"))
                .andExpect(jsonPath("[1].email").value("erlando@gmail.com"));
    }

    @Test
    public void testGetUserGenericError() throws Exception {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Internal server error"));
        mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/users"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

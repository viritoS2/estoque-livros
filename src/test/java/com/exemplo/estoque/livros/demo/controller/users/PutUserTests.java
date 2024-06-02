package com.exemplo.estoque.livros.demo.controller.users;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.handlers.user.UserNotFoundException;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class PutUserTests {

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);}

    @Test
    public void userUpdate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DadosDeCadastroUser dados = new DadosDeCadastroUser(1L, "John Doe", "johndoe@example.com");
        JsonNode node = mapper.valueToTree(dados);
        ObjectNode emailNode = mapper.createObjectNode();
        emailNode.set("email", node.get("email"));
        String json = mapper.writeValueAsString(emailNode);
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("email@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        mvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
        verify(userRepository, times(1)).save(user);
    }
    @Test
    public void userUpdateUserNotFoundError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DadosDeCadastroUser dados = new DadosDeCadastroUser(1L, "John Doe", "johndoe@example.com");
        JsonNode node = mapper.valueToTree(dados);
        ObjectNode emailNode = mapper.createObjectNode();
        emailNode.set("email", node.get("email"));
        String json = mapper.writeValueAsString(emailNode);
        when(userRepository.findById(1L)).thenThrow(new UserNotFoundException());
        mvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Não encontrado"))
                .andExpect(jsonPath("$.detail").value("Usuário não encontrado"))
                .andExpect(jsonPath("$.instance").value("/users/1"))
                .andExpect(jsonPath("$.Categoria").value("Users"));
    }

    @Test
    public void userUpdateGenericError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DadosDeCadastroUser dados = new DadosDeCadastroUser(1L, "John Doe", "johndoe@example.com");
        JsonNode node = mapper.valueToTree(dados);
        ObjectNode emailNode = mapper.createObjectNode();
        emailNode.set("email", node.get("email"));
        String json = mapper.writeValueAsString(emailNode);
        when(userRepository.findById(1L)).thenThrow(new RuntimeException());
        mvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.detail").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/users/1"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

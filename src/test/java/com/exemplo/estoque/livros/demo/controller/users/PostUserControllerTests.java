package com.exemplo.estoque.livros.demo.controller.users;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class PostUserControllerTests {

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
    public void testPostUserSuccess() throws Exception {
        DadosDeCadastroUser dados = new DadosDeCadastroUser(120L, "Gabriel", "gabriel@gmail.com");
        User user = new User(dados);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostUserInternalError() throws Exception {
        DadosDeCadastroUser dados = new DadosDeCadastroUser(120L, "Gabriel", "gabriel@gmail.com");
        User user = new User(dados);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.detail").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/users"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

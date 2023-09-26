package com.exemplo.estoque.livros.demo.controller.user;


import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.dto.Livro;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GetUserByIdControllerIT {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGeUserByID() {
        User user = new User(new DadosDeCadastroUser(90L, "Vitor", "vitor@gmail.com"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        ResponseEntity<String> responseEntity = userController.getUserById(90L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(user);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson );
        verify(userRepository).findById(90L);

    }

    @Test
    public void testGeUserByIDNotFoundError() throws RuntimeException{

        when(userRepository.findById(any())).thenThrow(new RuntimeException());

        ResponseEntity<String> responseEntity = userController.getUserById(70L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        String expectedJson = "Erro: java.lang.RuntimeException";
        String actualJson = responseEntity.getBody();

        assertEquals("Erro: java.lang.RuntimeException", actualJson );
        verify(userRepository).findById(70L);

    }
}
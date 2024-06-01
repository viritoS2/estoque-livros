package com.exemplo.estoque.livros.demo.controller.user;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
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


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GetUsersControllerIT {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetUsers() {
        List<User> listaDeUsuarios = new ArrayList<>();
        listaDeUsuarios.add(new User(new DadosDeCadastroUser(90L, "Vitor", "vitor@gmail.com")));
        listaDeUsuarios.add(new User(new DadosDeCadastroUser(40L, "Erlando", "erlando@gmail.com")));

        when(userRepository.findAll()).thenReturn(listaDeUsuarios);

        ResponseEntity<String> responseEntity = userController.getUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(listaDeUsuarios);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(userRepository).findAll();

    }

    @Test
    public void testGetUserGenericError() {
        when(userRepository.findAll()).thenThrow(new RuntimeException());
        ResponseEntity<String> responseEntity = userController.getUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}

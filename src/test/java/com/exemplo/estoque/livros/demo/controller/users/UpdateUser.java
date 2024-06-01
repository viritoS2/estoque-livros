package com.exemplo.estoque.livros.demo.controller.user;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.handlers.user.ResourceNotFoundException;
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
public class UpdateUser {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);}

    @Test
    public void userUpdate(){
        User oldUser = new User(new DadosDeCadastroUser(1L, "Vitor", "vitor@gmail.com"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
        DadosDeCadastroUser dados = new DadosDeCadastroUser(31231L, "", "vitora@gmail.com");

        ResponseEntity<String> response = userController.updateUser(1L, dados);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(new User(new DadosDeCadastroUser(
                                                                                            1L,
                                                                                            "Vitor",
                                                                                            "vitora@gmail.com")));
        String actualJson = response.getBody();

        assertEquals(expectedJson, actualJson);

        verify(userRepository).save(any(User.class));

    }
    @Test
    public void userUpdateUserNotFoundError(){
        when(userRepository.findById(any())).thenThrow(new ResourceNotFoundException("User not found"));
        ResponseEntity<String> response = userController.updateUser(1L, new DadosDeCadastroUser(1L, "Vitor", "vitor@gmail.com"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        String expectedJson = "Erro: com.exemplo.estoque.livros.demo.handlers.user.ResourceNotFoundException: User not found";
        String json = response.getBody();
        assertEquals(expectedJson, json);
        verify(userRepository).findById(any(Long.class));
    }

    @Test
    public void userUpdateGenericError(){
        when(userRepository.findById(any())).thenThrow(new RuntimeException("Ocurred an error"));
        ResponseEntity<String> response = userController.updateUser(1L,new DadosDeCadastroUser(1L, "Vitor", "vitor@gmail.com"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        String expectedJson = "Erro: com.exemplo.estoque.livros.demo.exceptions.GenericError: Ocurred an error";
        String json = response.getBody();
        assertEquals(expectedJson, json);
    }

}

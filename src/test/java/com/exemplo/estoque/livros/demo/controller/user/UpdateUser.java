package com.exemplo.estoque.livros.demo.controller.user;

import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.exceptions.GenericError;
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
import org.mockito.stubbing.Answer;
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
}

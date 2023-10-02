package com.exemplo.estoque.livros.demo.controller.user;


import com.exemplo.estoque.livros.demo.controller.UserController;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeleteUserByIdControllerIT {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteUserById(){
        when(userRepository.existsById(any())).thenReturn(true);

        ResponseEntity<String> responseEntity = userController.deleteUserById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Usuario deletado com sucesso";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);

        verify(userRepository).existsById(any());
        verify(userRepository).deleteById(any());
    }

    @Test
    public void testDeleteUserByIdUserNotFoundError(){
        when(userRepository.existsById(any())).thenReturn(false);

        ResponseEntity<String> responseEntity = userController.deleteUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        String expectedJson = "Esse usuário não está cadastrado";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(userRepository).existsById(any());
    }

    @Test
    public void testDeleteUserByIdGenericError(){
        when(userRepository.existsById(any())).thenThrow(new RuntimeException());


        ResponseEntity<String> responseEntity = userController.deleteUserById(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        String expectedJson = "Erro: java.lang.RuntimeException";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(userRepository).existsById(any());
    }

}

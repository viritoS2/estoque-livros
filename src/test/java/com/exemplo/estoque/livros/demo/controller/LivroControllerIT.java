package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.dto.Livro;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LivroControllerIT {

    @InjectMocks
    private LivroContoller livroController;

    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLivro() {
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(new DadosDeCasdastroLivro(1L, "Harry Potter", "J.K", 4L)));
        livros.add(new Livro(new DadosDeCasdastroLivro(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L)));

        when(livroRepository.findAll()).thenReturn(livros);

        ResponseEntity<String> responseEntity = livroController.getLivros();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(livros);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(livroRepository).findAll();

    }

    @Test
    public void testGetLivroByID() {
        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L);
        Livro livro = new Livro(dados);

        when(livroRepository.findById(any())).thenReturn(Optional.of(livro));

        ResponseEntity<String> responseEntity = livroController.getLivroByID(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(livro);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(livroRepository).findById(1L);

    }

    @Test
    public void testDeleteLivro() {
        Long id = 1L;
        when(livroRepository.existsById(id)).thenReturn(true);

        ResponseEntity<String> responseEntity = livroController.deleteLivroByID(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Deletado com sucesso";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);

        verify(livroRepository).existsById(any());
        verify(livroRepository).deleteById(any());
    }

    @Test
    public void testPostLivro() {

        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(1L, "Harry Potter", "J.K", 4L);
        Livro livro = new Livro(dados);

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        ResponseEntity<String> responseEntity = livroController.postLivro(dados);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Livro cadastradoLivro{id=1'nome=Harry Potter'autor=J.K'quantidade em estoque=4'}";
        String actualJson = responseEntity.getBody();
        assertEquals(expectedJson, actualJson);


        verify(livroRepository).save(any(Livro.class));
    }

}

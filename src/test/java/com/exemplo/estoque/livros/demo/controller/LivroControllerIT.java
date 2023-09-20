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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    }

    @Test
    public void testDeleteLivro() {
        Map<String, Long> corpoDaRequisicao = new HashMap<>();
        corpoDaRequisicao.put("id", 10L);

        ResponseEntity<String> responseEntity = livroController.deleteLivro(corpoDaRequisicao);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Livro removido";
        String actualJson = responseEntity.getBody();
        assertEquals(expectedJson, actualJson);

        verify(livroRepository).deleteById(10L);
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

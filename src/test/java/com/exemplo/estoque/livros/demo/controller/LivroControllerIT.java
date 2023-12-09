package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.BookRegistrationData;
import com.exemplo.estoque.livros.demo.dto.Book;
import com.exemplo.estoque.livros.demo.repository.BookRepository;
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
    private BookContoller livroController;

    @Mock
    private BookRepository livroRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBook() {
        List<Book> booksList = new ArrayList<>();
        booksList.add(new Book(new BookRegistrationData(1L, "Harry Potter", "J.K", 4L)));
        booksList.add(new Book(new BookRegistrationData(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L)));

        when(livroRepository.findAll()).thenReturn(booksList);

        ResponseEntity<String> responseEntity = livroController.getBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(booksList);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(livroRepository).findAll();

    }

    @Test
    public void testGetBookByID() {
        BookRegistrationData dados = new BookRegistrationData(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L);
        Book book = new Book(dados);

        when(livroRepository.findById(any())).thenReturn(Optional.of(book));

        ResponseEntity<String> responseEntity = livroController.getLivroByID(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(book);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(livroRepository).findById(1L);

    }

    @Test
    public void testDeleteBook() {
        Long id = 1L;
        when(livroRepository.existsById(id)).thenReturn(true);

        ResponseEntity<String> responseEntity = livroController.deleteLivroByID(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Successfully deleted";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);

        verify(livroRepository).existsById(any());
        verify(livroRepository).deleteById(any());
    }

    @Test
    public void testPostBook() {

        BookRegistrationData dados = new BookRegistrationData(1L, "Harry Potter", "J.K", 4L);
        Book book = new Book(dados);

        when(livroRepository.save(any(Book.class))).thenReturn(book);

        ResponseEntity<String> responseEntity = livroController.postLivro(dados);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Livro cadastradoLivro{id=1'nome=Harry Potter'author=J.K'amount em estoque=4'}";
        String actualJson = responseEntity.getBody();
        assertEquals(expectedJson, actualJson);


        verify(livroRepository).save(any(Book.class));
    }

}

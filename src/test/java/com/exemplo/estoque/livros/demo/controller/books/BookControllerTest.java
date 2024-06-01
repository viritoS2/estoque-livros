package com.exemplo.estoque.livros.demo.controller.books;

import com.exemplo.estoque.livros.demo.controller.BookController;
import com.exemplo.estoque.livros.demo.dto.Book;
import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.repository.BookRepository;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BookController livroController;

    @MockBean
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLivro() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(new DadosDeCasdastroLivro(1L, "Harry Potter", "J.K", 4L)));
        books.add(new Book(new DadosDeCasdastroLivro(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L)));

        when(bookRepository.findAll()).thenReturn(books);

        ResponseEntity<String> responseEntity = livroController.getBookList();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(books);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(bookRepository).findAll();

    }

    @Test
    public void testGetBookById() throws Exception {
        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(2L, "Harry Potter e a Ordem da Fenix", "J.K", 10L);
        Book book = new Book(dados);

        when(bookRepository.existsById(2L)).thenReturn(true);
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/livros/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("Harry Potter e a Ordem da Fenix"))
                .andExpect(jsonPath("$.autor").value("J.K"))
                .andExpect(jsonPath("$.quantidade").value(10));
        verify(bookRepository).findById(2L);

    }

    @Test
    public void testDeleteLivro() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(true);

        ResponseEntity<String> responseEntity = livroController.deleteBookById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Deletado com sucesso";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);

        verify(bookRepository).existsById(any());
        verify(bookRepository).deleteById(any());
    }

    @Test
    public void testPostLivro() {

        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(1L, "Harry Potter", "J.K", 4L);
        Book book = new Book(dados);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        ResponseEntity<String> responseEntity = livroController.postLivro(dados);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "Livro cadastradoLivro{id=1'nome=Harry Potter'autor=J.K'quantidade em estoque=4'}";
        String actualJson = responseEntity.getBody();
        assertEquals(expectedJson, actualJson);


        verify(bookRepository).save(any(Book.class));
    }

}

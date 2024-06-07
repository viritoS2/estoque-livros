package com.exemplo.estoque.livros.demo.controller.books;

import com.exemplo.estoque.livros.demo.controller.BookController;
import com.exemplo.estoque.livros.demo.dto.Book;
import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class GetBookByIdTests {

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
    public void testGetBookByIdSuccess() throws Exception {
        Book book = new Book(new DadosDeCasdastroLivro(2L, "Harry Potter e a Ordem da Fenix", "J.K", 10L));
        when(bookRepository.existsById(2L)).thenReturn(true);
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/books/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Harry Potter e a Ordem da Fenix"))
                .andExpect(jsonPath("$.autor").value("J.K"))
                .andExpect(jsonPath("$.quantidade").value(10));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        when(bookRepository.existsById(3L)).thenReturn(false);
        mockMvc.perform(get("/books/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Livro não encontrado"))
                .andExpect(jsonPath("$.detail").value("Esse livro não está cadastrado"))
                .andExpect(jsonPath("$.instance").value("/books/2"))
                .andExpect(jsonPath("$.Categoria").value("Books"));
    }
    @Test
    public void testGetBookByIdInternalError() throws Exception {
        Book book = new Book(new DadosDeCasdastroLivro(2L, "Harry Potter e a Ordem da Fenix", "J.K", 10L));
        when(bookRepository.existsById(2L)).thenReturn(true);
        when(bookRepository.findById(2L)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/books/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/books/2"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

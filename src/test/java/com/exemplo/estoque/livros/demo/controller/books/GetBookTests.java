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
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTests {

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
    public void testGetLivroSuccess() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(new DadosDeCasdastroLivro(2L, "Harry Potter", "J.K", 4L)));
        books.add(new Book(new DadosDeCasdastroLivro(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L)));
        when(bookRepository.findAll()).thenReturn(books);
        mockMvc.perform(get("/books"))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[0].autor").value("J.K"))
                .andExpect(jsonPath("$[0].quantidade").value(4L))
                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Harry Potter e a Ordem da Fenix"))
                .andExpect(jsonPath("$[1].autor").value("J.K"))
                .andExpect(jsonPath("$[1].quantidade").value(10));
        verify(bookRepository).findAll();

    }

    @Test
    public void testGetBookFail() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(new DadosDeCasdastroLivro(2L, "Harry Potter", "J.K", 4L)));
        books.add(new Book(new DadosDeCasdastroLivro(1L, "Harry Potter e a Ordem da Fenix", "J.K", 10L)));
        when(bookRepository.findAll()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/books"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/books"))
                .andExpect(jsonPath("$.Categoria").value("Application"));

        verify(bookRepository).findAll();

    }

}

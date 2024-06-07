package com.exemplo.estoque.livros.demo.controller.books;

import com.exemplo.estoque.livros.demo.controller.BookController;
import com.exemplo.estoque.livros.demo.dto.Book;
import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class PostBookTests {

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
    public void testPostBookSuccess() throws Exception {
        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(null, "Harry Potter e a Ordem da Fenix", "J.K", 1L);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo("Livro cadastrado com sucesso")));
    }

    @Test
    public void testPostBookNameInvalidParameter() throws Exception {
        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(null, null, "J.K", 1L);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Nome não pode ser nulo"));
    }

    @Test
    public void testPostBookAuthorInvalidParameter() throws Exception {
        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(null, "Harry Potter", null, 1L);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Autor não pode ser nulo"));
    }

    @Test
    public void testPostBookQuantitynvalidParameter() throws Exception {
        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(null, "Harry Potter", "J.K", null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Quantidade não pode ser nula"));
    }

    @Test
    public void testPostBookInternalError() throws Exception {

        DadosDeCasdastroLivro dados = new DadosDeCasdastroLivro(null, "Harry Potter", "J.K", 10L);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);

        when(bookRepository.save(any(Book.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal server error"))
                .andExpect(jsonPath("$.instance").value("/books"))
                .andExpect(jsonPath("$.Categoria").value("Application"));
    }
}

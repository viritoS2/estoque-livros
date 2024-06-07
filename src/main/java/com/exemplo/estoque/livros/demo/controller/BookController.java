package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.Book;
import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.handlers.generic.InvalidParameters;
import com.exemplo.estoque.livros.demo.handlers.book.BookNotFound;
import com.exemplo.estoque.livros.demo.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "Book Controller", description = "Book of Controller")
@RestController()
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookRepository repository;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<String> getBookList(){
        List<Book> books = repository.findAll();
        String json = gson.toJson(books);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getBookById(@PathVariable(name = "id", required = true) Long id){
        try{
            if(!(repository.existsById(id))){
                throw new BookNotFound("Esse livro não está cadastrado");
            }
            Optional<Book> book = repository.findById(id);
            String json = gson.toJson(book.orElse(null));
            return ResponseEntity.ok(json);
        } catch (BookNotFound e ){
            throw new BookNotFound(e.getMessage());
        } catch (Exception e){
            throw new RuntimeException("Internal server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable(name = "id", required = true) Long id){
        try{
            if (!(repository.existsById(id))){ throw new BookNotFound("Esse livro não está cadastrado");}

        } catch (Exception e){
           throw new BookNotFound(e.getMessage());
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }

    @PostMapping()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> postBook(@RequestBody DadosDeCasdastroLivro dados) {
        try {
            Book book = new Book(dados);
            repository.save(book);
            return new ResponseEntity<>("Livro cadastrado com sucesso", HttpStatus.CREATED);
        } catch (InvalidParameters e) {
            throw new InvalidParameters(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
}}

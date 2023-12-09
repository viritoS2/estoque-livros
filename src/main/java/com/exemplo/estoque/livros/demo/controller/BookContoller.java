package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.BookRegistrationData;
import com.exemplo.estoque.livros.demo.dto.Book;
import com.exemplo.estoque.livros.demo.repository.BookRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "Livro Controller", description = "Controller of Book")
@RestController()
@RequestMapping("/livros")
@Transactional
public class BookContoller {

    @Autowired
    private BookRepository repository;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public BookContoller(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<String> getLivros(){
        List<Book> books = repository.findAll();
        String json = gson.toJson(books);

        return ResponseEntity.ok(json);
    }

    @GetMapping()
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getLivroByID(@RequestParam(name = "id", required = true) Long id){
        try{
            Optional<Book> bookOptional = repository.findById(id);
            Book book = bookOptional.orElse(null);
            if( book == null){throw new Exception("Book not found");}
            String json = gson.toJson(book);

            return ResponseEntity.ok(json);
        } catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e);
        }
    }

    @DeleteMapping()
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLivroByID(@RequestParam(name = "id", required = true) Long id){
        try{
            if (repository.existsById(id)){
                repository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book is not registered");
            }
        } catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e);
        }
    }

    @PostMapping()
    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public ResponseEntity<String> postLivro(@RequestBody BookRegistrationData dados){
        try {
            var livro = new Book(dados);
            repository.save(livro);
            return ResponseEntity.ok("Livro cadastrado" + livro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to register the book");
        }
    }
}

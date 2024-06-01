package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.dto.Livro;
import com.exemplo.estoque.livros.demo.handlers.generic.InvalidParameters;
import com.exemplo.estoque.livros.demo.handlers.book.BookNotFound;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
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


@Tag(name = "Livro Controller", description = "Controller do Livro")
@RestController()
@RequestMapping("/livros")
public class LivroContoller {

    private static final Logger log = LoggerFactory.getLogger(LivroContoller.class);
    @Autowired
    private  LivroRepository repository;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public LivroContoller(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<String> getLivros(){
        List<Livro> livros = repository.findAll();
        String json = gson.toJson(livros);
        return ResponseEntity.ok(json);
    }

    @GetMapping()
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getLivroByID(@PathVariable(name = "id", required = true) Long id){
        try{
            if(!(repository.existsById(id))){
                throw new BookNotFound("Esse livro está não cadastrado");
            }
            Optional<Livro> livro = repository.findById(id);
            var livroS = (Livro) livro.orElse(null);
            String json = gson.toJson(livroS);

            return ResponseEntity.ok(json);

        } catch (BookNotFound e ){
            throw new BookNotFound(e.getMessage());
        } catch (Exception e){
           throw new RuntimeException("Internal server error");
        }
    }

    @DeleteMapping()
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLivroByID(@RequestParam(name = "id", required = true) Long id){
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
    public ResponseEntity<String> postLivro(@RequestBody DadosDeCasdastroLivro dados) {
        try{
            Livro livro = new Livro(dados);
            repository.save(livro);
        } catch (InvalidParameters e){
           throw new InvalidParameters(e.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return new ResponseEntity<String>("Livro cadastrado", HttpStatus.OK);

    }
}

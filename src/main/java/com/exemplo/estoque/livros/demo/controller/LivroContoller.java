package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.dto.Livro;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
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


@Tag(name = "Livro Controller", description = "Controller do Livro")
@RestController()
@RequestMapping("/livros")
@Transactional
public class LivroContoller {

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
    public ResponseEntity<String> getLivroByID(@RequestParam(name = "id", required = true) Long id){
        try{
            Optional<Livro> livroOptional = repository.findById(id);
            Livro livro = livroOptional.orElse(null);
            if( livro == null){throw new Exception("Livro não encontrado");}
            String json = gson.toJson(livro);

            return ResponseEntity.ok(json);
        } catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e);
        }
    }

    @DeleteMapping()
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLivroByID(@RequestParam(name = "id", required = true) Long id){
        try{
            if (repository.existsById(id)){
                repository.deleteById(id);
                return ResponseEntity.ok("Deletado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse livro não está cadastrado");
            }
        } catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @PostMapping()
    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public ResponseEntity<String> postLivro(@RequestBody DadosDeCasdastroLivro dados){
        try {
            var livro = new Livro(dados);
            repository.save(livro);
            return ResponseEntity.ok("Livro cadastrado" + livro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu algum erro ao tentar cadastrar o livro");
        }
    }
}

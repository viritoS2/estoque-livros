package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.dto.Livro;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("/")
@Transactional
public class LivroContoller {

    @Autowired
    private LivroRepository repository;

    public LivroContoller(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<String> getLivros(){
        List<Livro> livros = repository.findAll();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(livros);

        return ResponseEntity.ok(json);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLivro(@RequestBody Map<String, Long> corpoDaRequisicao){
        Long id = corpoDaRequisicao.get("id");
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Livro removido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu algum erro ao tentar deletar");
        }
    }

    @RequestMapping("/cadastro/livro")
    @PostMapping
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

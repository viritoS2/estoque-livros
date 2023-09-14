package com.exemplo.estoque.livros.demo.controller;


import com.exemplo.estoque.livros.demo.dto.DadosDeCasdastroLivro;
import com.exemplo.estoque.livros.demo.dto.Livro;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller("/")
@Transactional
public class LivroContoller {

    @Autowired
    private LivroRepository repository;


    @GetMapping
    @ResponseBody
    public List<Livro> livro(){
        var listaDeLivros = repository.findAll();
        return listaDeLivros;
    }

    @DeleteMapping
    public ResponseEntity<String> deletaLivro(@RequestBody Map<String, Long> corpoDaRequisicao){
        Long id = corpoDaRequisicao.get("id");
        var livro = repository.getReferenceById(id);
        repository.deleteById(id);

        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Livro deletado"+ livro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu algum erro ao tentar deletar");
        }
    }


    @RequestMapping("/cadastro/livro")
    @PostMapping
    public ResponseEntity<String> cadastraLivro(@RequestBody DadosDeCasdastroLivro dados){
        try {
            var livro = new Livro(dados);
            repository.save(livro);
            return ResponseEntity.ok("Livro cadastrado" + livro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu algum erro ao tentar cadastrar o livro");
        }
    }

}

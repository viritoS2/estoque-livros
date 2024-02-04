package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroSales;
import com.exemplo.estoque.livros.demo.dto.Sales;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
import com.exemplo.estoque.livros.demo.repository.SalesRepository;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sales controller", description = "Controller of Sales")
@RequestMapping("/sales")
@RestController
public class SalesController {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private LivroRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    public SalesController(SalesRepository salesRepository, LivroRepository bookRepository, UserRepository userRepository){
        this.salesRepository = salesRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

     @GetMapping
     public ResponseEntity<String> getSales(){
           try{
               List<Sales> listOfSales = salesRepository.findAll();
               return ResponseEntity.ok(gson.toJson(listOfSales));
           } catch (Exception e){
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado");
           }

     }

     @PostMapping
     @RequestMapping(method =  RequestMethod.POST)
     public ResponseEntity<String> postSales(@RequestBody DadosDeCadastroSales dados) {
         try {
             if (userRepository.existsById(dados.user_id()) && bookRepository.existsById(dados.book_id())) {
                 Sales newSales = new Sales(dados);

                 salesRepository.save(newSales);
                 return ResponseEntity.ok("venda feita com sucesso");
             } else {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu algum erro ao tentar fazer a venda");
             }
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado");
         }
     }


}

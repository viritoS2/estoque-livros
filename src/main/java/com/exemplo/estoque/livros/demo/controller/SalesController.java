package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroSales;
import com.exemplo.estoque.livros.demo.dto.Sales;
import com.exemplo.estoque.livros.demo.repository.SalesRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Tag(name = "Sales controller", description = "Controller of Sales")
@RequestMapping("/sales")
@RestController
@Transactional
public class SalesController {
    @Autowired
    private SalesRepository salesRepository;

     private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SalesController(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }


    @GetMapping
     public ResponseEntity<String> getSales(){
             //Fix this bug
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            UUID a = UUID.fromString("9c99acad-57ef-4408-99da-1d095ef59502");
            Optional<Sales> uuid =salesRepository.findById(a);
            System.out.println(uuid);

            List<Sales> listOfSales = salesRepository.findAll();

        return ResponseEntity.ok(gson.toJson(listOfSales));
     }

     @PostMapping
     public ResponseEntity<String> postSales(){
             System.out.println("Estou batendo aqui");
             Sales newSales = new Sales(new DadosDeCadastroSales(1L, 1L, 12L, 10L));
             System.out.println(newSales.getUUID());
             salesRepository.save(newSales);
         return ResponseEntity.ok().body(newSales + "");
     }
     @GetMapping("/certo")
     public ResponseEntity<String> certo(){
         System.out.println("ESTOU BATENDO AQUI");
         return ResponseEntity.ok().body("ESTOU CERTO");
     }


}

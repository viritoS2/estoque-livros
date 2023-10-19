package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.Sales;
import com.exemplo.estoque.livros.demo.repository.SalesRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Sales controller", description = "Controller of Sales")
@RequestMapping("/sales")
@RestController
@Transactional
public class SalesController {
    @Autowired
    private SalesRepository salesRepository;

     private Gson gson = new GsonBuilder().setPrettyPrinting().create();


     @GetMapping
     public ResponseEntity<String> getSales(){
         //Fix this bug
         List<Sales> listOfSales = salesRepository.findAll();
         return ResponseEntity.ok(gson.toJson(listOfSales));
     }


}

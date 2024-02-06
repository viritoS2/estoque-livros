package com.exemplo.estoque.livros.demo.dto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record DadosDeCadastroSales(Long book_id, Long user_id,Long quantity, Long total_cost){}

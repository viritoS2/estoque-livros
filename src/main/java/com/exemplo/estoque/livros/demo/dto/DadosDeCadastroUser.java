package com.exemplo.estoque.livros.demo.dto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record DadosDeCadastroUser(Long id, String nome, String email) {}

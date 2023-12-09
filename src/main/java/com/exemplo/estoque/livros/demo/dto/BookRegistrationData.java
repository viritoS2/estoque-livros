package com.exemplo.estoque.livros.demo.dto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record BookRegistrationData(Long id, String name, String author, Long amount) {}

package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dto.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {}

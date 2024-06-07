package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}

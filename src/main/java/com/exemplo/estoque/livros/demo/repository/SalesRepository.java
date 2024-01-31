package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dto.Sales;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SalesRepository extends JpaRepository<Sales, Long> {}

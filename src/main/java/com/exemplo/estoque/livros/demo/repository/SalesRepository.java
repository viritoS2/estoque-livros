package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dto.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, UUID> {
}

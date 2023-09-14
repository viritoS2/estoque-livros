package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}

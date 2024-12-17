package com.exemplo.estoque.livros.demo.dao;

import com.exemplo.estoque.livros.demo.dto.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();
    User findById(Long id);
    Boolean existsById(Long id);
    void deleteUserById(Long id);
    User save(User user);

}

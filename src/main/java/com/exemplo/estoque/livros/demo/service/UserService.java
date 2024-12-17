package com.exemplo.estoque.livros.demo.service;

import com.exemplo.estoque.livros.demo.dao.UserDAO;
import com.exemplo.estoque.livros.demo.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public final UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public User getUserById(Long id){
        return userDAO.findById(id);
    }

    public User save(User user){
        return  userDAO.save(user);
    }

    public void deleteUserById(Long id){
        userDAO.deleteUserById(id);
    }

    public Boolean existsById(Long id){
        return userDAO.existsById(id);
    }

}

package com.exemplo.estoque.livros.demo.handlers.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){}
    public UserNotFoundException(String message){
        super(message);
    }
}

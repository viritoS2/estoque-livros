package com.exemplo.estoque.livros.demo.handlers;

public class NotFoundBook extends RuntimeException{

    public NotFoundBook(){}
    public NotFoundBook(String message){
        super(message);
    }
}

package com.exemplo.estoque.livros.demo.handlers.book;

public class BookNotFound extends RuntimeException{

    public BookNotFound(){}
    public BookNotFound(String message){
        super(message);
    }
}

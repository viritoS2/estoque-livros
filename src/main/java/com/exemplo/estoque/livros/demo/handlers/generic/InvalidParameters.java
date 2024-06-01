package com.exemplo.estoque.livros.demo.handlers;

public class InvalidParameters extends RuntimeException{

    public InvalidParameters(){
        super("Algum parametro está errado");
    }
    public InvalidParameters(String message){
        super(message);
    }
}

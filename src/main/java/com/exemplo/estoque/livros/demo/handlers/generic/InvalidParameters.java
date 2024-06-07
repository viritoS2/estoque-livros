package com.exemplo.estoque.livros.demo.handlers.generic;

public class InvalidParameters extends RuntimeException{

    public InvalidParameters(){
        super("Algum parametro está errado");
    }
    public InvalidParameters(String message){
        super(message);
    }
}

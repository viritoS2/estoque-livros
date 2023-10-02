package com.exemplo.estoque.livros.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericError extends RuntimeException{

    public GenericError(String message){
        super(message);
    }
}


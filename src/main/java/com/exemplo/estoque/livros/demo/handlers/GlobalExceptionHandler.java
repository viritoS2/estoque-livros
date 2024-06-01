package com.exemplo.estoque.livros.demo.handlers;

import com.exemplo.estoque.livros.demo.handlers.book.BookNotFound;
import com.exemplo.estoque.livros.demo.handlers.generic.InvalidParameters;
import com.exemplo.estoque.livros.demo.handlers.user.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(BookNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ProblemDetail handlerBookNotFound(BookNotFound ex)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        problemDetail.setTitle("Livro não encontrado");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("Categoria", "Books");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handlerResourceNotFound(ResourceNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        problemDetail.setTitle("Não encontrado");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("Categoria", "Usuário");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({InvalidParameters.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handlerInvalidParameter(InvalidParameters ex){

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
            problemDetail.setTitle("Parametro invalido");
            problemDetail.setDetail(ex.getMessage());
            problemDetail.setProperty("Categoria", "Livros");
            problemDetail.setProperty("TimeStamp", Instant.now());
            return problemDetail;

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ProblemDetail handleDefaultException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        problemDetail.setTitle("Internal server error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("Categoria", "Application");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }
}

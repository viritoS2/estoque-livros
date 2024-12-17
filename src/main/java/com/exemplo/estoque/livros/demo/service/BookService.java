package com.exemplo.estoque.livros.demo.service;


import com.exemplo.estoque.livros.demo.dao.BookDAO;
import com.exemplo.estoque.livros.demo.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    public final BookDAO bookDAO;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookService(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks(){
        return bookDAO.getAllBooks();
    }

    public Book getBookById(Long id){
        logger.error("ESTOU FAZENDO ALGO AAAAAA {}", id);
        return bookDAO.getBookById(id);
    }

    public Book save(Book book){
        return bookDAO.save(book);
    }

    public void deleteBookById(Long id){
        bookDAO.deleteBookById(id);
    }

    public Boolean existsById(Long id) {
        return bookDAO.existsById(id);
    }
}

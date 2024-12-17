package com.exemplo.estoque.livros.demo.dao;

import com.exemplo.estoque.livros.demo.dto.Book;

import java.util.List;

public interface BookDAO {

    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book save(Book book);
    void deleteBookById(Long id);
    Boolean existsById(Long id);
}

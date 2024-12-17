package com.exemplo.estoque.livros.demo.dao;

import com.exemplo.estoque.livros.demo.dto.Book;

import java.util.List;

public interface BookDAO {

    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(Long id);
    Boolean findById(Long id);
    Boolean existsById(Long id);
}

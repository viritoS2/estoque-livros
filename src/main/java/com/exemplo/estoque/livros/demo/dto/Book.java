package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;

@Entity
@Table(name="livros")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;

    private Long amount =0L;

    public Book(BookRegistrationData data) {
        this.id = data.id();
        this.name = data.name();
        this.author = data.author();
        this.amount = data.amount();
    }

    public Book(){};

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + this.id + '\'' +
                "nome=" + this.name + '\'' +
                "author=" + this.author + '\'' +
                "amount em estoque=" + this.amount + '\'' +
                '}';
    }
}

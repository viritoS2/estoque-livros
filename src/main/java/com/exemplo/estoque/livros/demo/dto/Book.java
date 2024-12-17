package com.exemplo.estoque.livros.demo.dto;

import com.exemplo.estoque.livros.demo.handlers.generic.InvalidParameters;


public class Book {

    private Long id;
    private String name;
    private String autor;
    private Long quantidade=0L;

    public Book(DadosDeCasdastroLivro dados) {
        if (dados.nome() == null) {
            throw new InvalidParameters("Nome não pode ser nulo");
        }
        if (dados.autor() == null) {
            throw new InvalidParameters("Autor não pode ser nulo");
        }
        if (dados.quantidade() == null) {
            throw new InvalidParameters("Quantidade não pode ser nula");
        }
        this.name = dados.nome();
        this.autor = dados.autor();
        this.quantidade = dados.quantidade();
    }

    public Book(){};

    public Book(Long id, String nome, String autor, long quantidade) {
        this.id = id;
        this.name = nome;
        this.autor = autor;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + this.id + '\'' +
                "nome=" + this.name + '\'' +
                "autor=" + this.autor + '\'' +
                "quantidade em estoque=" + this.quantidade + '\'' +
                '}';
    }
}

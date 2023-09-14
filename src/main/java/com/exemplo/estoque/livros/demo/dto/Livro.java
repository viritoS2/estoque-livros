package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;

@Entity
@Table(name="livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String autor;

    private Long quantidade=0L;

    public Livro(DadosDeCasdastroLivro dados) {
        this.id = dados.id();
        this.nome = dados.nome();
        this.autor = dados.autor();
        this.quantidade = dados.quantidade();
    }

    public Livro(){};

    @Override
    public String toString() {
        return "Livro{ " +
                "id=" + this.id + '\'' +
                "nome=" + this.nome + '\'' +
                "autor=" + this.autor + '\'' +
                "quantidade em estoque=" + this.quantidade + '\'' +
                '}';
    }
}

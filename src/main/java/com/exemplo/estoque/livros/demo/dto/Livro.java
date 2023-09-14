package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;

@Entity
@Table(name="livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String autor;

    public Livro(DadosDeCasdastroLivro dados) {
        this.id = dados.id();
        this.nome = dados.nome();
        this.autor = dados.autor();
    }

    public Livro(){};

    @Override
    public String toString() {
        return "Livro{ " +
                "id=" + this.id + '\'' +
                "nome=" + this.nome + '\'' +
                "autor=" + this.autor + '\'' +
                '}';
    }
}

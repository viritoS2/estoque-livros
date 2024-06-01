package com.exemplo.estoque.livros.demo.dto;

import com.exemplo.estoque.livros.demo.handlers.generic.InvalidParameters;
import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String autor;

    private Long quantidade=0L;

    public Livro(DadosDeCasdastroLivro dados) {
        if (dados.nome() == null) {
            throw new InvalidParameters("Nome não pode ser nulo");
        }
        if (dados.autor() == null) {
            throw new InvalidParameters("Autor não pode ser nulo");
        }
        if (dados.quantidade() == null) {
            throw new InvalidParameters("Quantidade não pode ser nula");
        }
        this.id = dados.id();
        this.nome = dados.nome();
        this.autor = dados.autor();
        this.quantidade = dados.quantidade();
    }

    public Livro(){};

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + this.id + '\'' +
                "nome=" + this.nome + '\'' +
                "autor=" + this.autor + '\'' +
                "quantidade em estoque=" + this.quantidade + '\'' +
                '}';
    }
}

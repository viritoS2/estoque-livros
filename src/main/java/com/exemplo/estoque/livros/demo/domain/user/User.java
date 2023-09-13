package com.exemplo.estoque.livros.demo.domain.user;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;


    public User (DadosDeCadastroUser dados){
        this.id = dados.id();
        this.nome = dados.nome();
        this.email = dados.email();
    }

    public User(){}

    @Override
    public String toString() {
       return  "Usuario{ " +
                "id=" + this.id + '\'' +
                "nome=" + this.nome + '\'' +
                "email=" + this.email + '\'' +
                '}';
    }
}

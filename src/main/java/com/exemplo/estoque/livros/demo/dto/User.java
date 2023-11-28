package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nome;
    @Column(name = "email")
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

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;


    public User (DadosDeCadastroUser dados){
        this.id = dados.id();
        this.name = dados.name();
        this.email = dados.email();
    }

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
       return  "Usuario{ " +
                "id=" + this.id + '\'' +
                "nome=" + this.name + '\'' +
                "email=" + this.email + '\'' +
                '}';
    }
}

package com.exemplo.estoque.livros.demo.dto;

public class User {

    private Long id;
    private String name;
    private String email;

    public User (DadosDeCadastroUser dados){
        this.id = dados.id();
        this.name = dados.name();
        this.email = dados.email();
    }

    public User(){};
    public User (String email){
        this.email = email;
    }

    public User(long generatedId, String name, String email) {
        this.id = generatedId;
        this.name = name;
        this.email = email;
    }

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

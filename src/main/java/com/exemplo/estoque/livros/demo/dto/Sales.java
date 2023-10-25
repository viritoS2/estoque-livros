package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;


import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;
    @Column(name = "book_id")
    private Long book_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "sales_date")
    @Temporal(TemporalType.DATE)
    private Date sales_date;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_cost")
    private Long total_cost;


    public Sales(DadosDeCadastroSales dados){
        this.book_id = dados.book_id();
        this.user_id =dados.user_id();
        this.sales_date = new Date();
        this.quantity = dados.quantity();
        this.total_cost = dados.total_cost();
    }
    public Sales(){};

    public UUID getUUID() {
        return this.uuid;
    }
}

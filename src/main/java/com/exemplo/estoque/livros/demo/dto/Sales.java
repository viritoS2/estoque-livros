package com.exemplo.estoque.livros.demo.dto;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "book_id")
    private Long book_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "sales_date")
    private Long sales_date;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_cost")
    private Long total_cost;

    private Date  now = new Date();

    public Sales(DadosDeCadastroSales dados){
        this.id = dados.id();
        this.book_id = dados.book_id();
        this.user_id =dados.user_id();
        this.sales_date = now.getTime();
        this.quantity = dados.quantity();
        this.total_cost = dados.total_cost();
    }
}

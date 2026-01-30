package com.hyunha.stock.stock.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "theme_members")
@Entity
public class ThemeMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_code")
    private Theme theme;

    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "market"),
            @JoinColumn(name = "stock_code")
    })
    private Stock stock;

}

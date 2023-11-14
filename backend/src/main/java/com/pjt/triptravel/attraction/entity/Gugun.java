package com.pjt.triptravel.attraction.entity;

import javax.persistence.*;

@Entity
public class Gugun {

    @Id
    @GeneratedValue
    private Long id;

    private Long code;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sido sido;
}

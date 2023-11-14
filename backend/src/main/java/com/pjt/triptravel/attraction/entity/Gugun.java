package com.pjt.triptravel.attraction.entity;

import javax.persistence.*;

import lombok.Getter;

@Entity
@Getter
public class Gugun {

    @Id
    @GeneratedValue
    @Column(name = "gugun_id")
    private Long gugunId;

    @Column(name = "gugun_code")
    private Long code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_code")
    private Sido sido;
}

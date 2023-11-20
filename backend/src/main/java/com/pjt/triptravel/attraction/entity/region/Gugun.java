package com.pjt.triptravel.attraction.entity.region;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Gugun {

    @Id
    @GeneratedValue
    @Column(name = "gugun_id")
    private Long gugunId;

    @Column(name = "gugun_code")
    private Long code;

    @Column(name = "gugun_name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sido_code")
    private Sido sido;
}

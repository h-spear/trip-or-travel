package com.pjt.triptravel.attraction.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Sido {

    @Id
    @GeneratedValue
    @Column(name = "sido_code")
    private Long code;

    @Column(name = "sido_name")
    private String name;
}

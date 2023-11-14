package com.pjt.triptravel.attraction.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Sido {

    @Id
    @GeneratedValue
    private Long id;
    private Long code;
    private String sidoName;
}

package com.pjt.triptravel.attraction.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class ContentType {

    @Id
    @Column(name = "content_type_id")
    private Long id;
    private String name;
}

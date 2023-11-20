package com.pjt.triptravel.attraction.entity.region;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Sido {

    @Id
    @GeneratedValue
    @Column(name = "sido_code")
    private Long code;

    @Column(name = "sido_name")
    private String name;

    @OneToMany(mappedBy = "sido")
    private List<Gugun> guguns;
}

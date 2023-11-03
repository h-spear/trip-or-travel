package com.pjt.triptravel.member.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String sido;
    private String gugun;
}

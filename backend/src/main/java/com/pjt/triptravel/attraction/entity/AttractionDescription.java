package com.pjt.triptravel.attraction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class AttractionDescription {

	@Id
	@Column(name = "content_id")
	private Long id;

	private String homepage;
	private String overview;
}

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

	@Column(length = 100)
	private String homepage;

	@Column(length = 10000)
	private String overview;
}

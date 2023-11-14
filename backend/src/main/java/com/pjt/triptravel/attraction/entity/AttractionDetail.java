package com.pjt.triptravel.attraction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class AttractionDetail {

	@Id
	@Column(name = "content_id")
	private Long id;

	private String cat1;
	private String cat2;
	private String cat3;

	private String createdTime;
	private String modifiedTime;

	@Column(name = "booktour")
	private String bookTour;
}

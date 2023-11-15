package com.pjt.triptravel.attraction.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class AttractionDetail {

	@Id
	@Column(name = "content_id")
	private Long id;

	@Column(length = 3)
	private String cat1;

	@Column(length = 5)
	private String cat2;

	@Column(length = 9)
	private String cat3;

	@Column(length = 14)
	private String createdTime;

	@Column(length = 14)
	private String modifiedTime;

	@Column(name = "booktour", length = 5)
	private String bookTour;
}

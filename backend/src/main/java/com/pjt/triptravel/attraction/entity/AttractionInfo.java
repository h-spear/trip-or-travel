package com.pjt.triptravel.attraction.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.pjt.triptravel.attraction.entity.region.Gugun;
import com.pjt.triptravel.attraction.entity.region.Sido;

import lombok.Getter;

@Entity
@Getter
public class AttractionInfo {

	@Id
	@Column(name = "content_id")
	private Long id;

	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	@Column(name = "first_image")
	private String imageUrl;
	@Column(name = "first_image2")
	private String imageUrl2;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sido_code")
	private Sido sido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gugun_code")
	private Gugun gugun;

	@Column(precision = 20, scale = 17)
	private BigDecimal latitude;

	@Column(precision = 20, scale = 17)
	private BigDecimal longitude;

	private String mlevel;

	@Column(name = "readcount")
	private int readCount;
	private int likeCount;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "content_id")
	private AttractionDetail detail;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "content_id")
	private AttractionDescription description;
}

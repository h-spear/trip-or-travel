package com.pjt.triptravel.attraction.entity;

import com.pjt.triptravel.attraction.entity.region.Gugun;
import com.pjt.triptravel.attraction.entity.region.Sido;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
public class AttractionInfo {

	@Id
	@Column(name = "content_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_type_id")
	private ContentType contentType;

	@Column(length = 100)
	private String title;

	@Column(length = 100)
	private String addr1;

	@Column(length = 50)
	private String addr2;

	@Column(length = 50)
	private String zipcode;

	@Column(length = 50)
	private String tel;

	@Column(name = "first_image", length = 200)
	private String imageUrl;
	@Column(name = "first_image2", length = 200)
	private String imageUrl2;

	private Long sidoCode;
	private Long gugunCode;

	@Column(precision = 20, scale = 17)
	private BigDecimal latitude;

	@Column(precision = 20, scale = 17)
	private BigDecimal longitude;

	@Column(length = 2)
	private String mlevel;

	@Column(name = "readcount")
	private int readCount;

	@ColumnDefault("0")
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

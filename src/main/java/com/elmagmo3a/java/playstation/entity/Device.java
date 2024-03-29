package com.elmagmo3a.java.playstation.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Status {
		ACTIVE, SUSPENDED, FIXING
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Float price;
	private Float multiPrice;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = DeviceType.class)
	private DeviceType type;

	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne(fetch = FetchType.EAGER)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User createdBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User lastUpdatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	@Builder.Default
	private Boolean archived = false;

}

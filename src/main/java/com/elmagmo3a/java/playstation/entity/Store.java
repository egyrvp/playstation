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
public class Store implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Status {
		ACTIVE, SUSPENDED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String mobile;
	private String address;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Status status = Status.ACTIVE;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private User lastUpdateBy;

	@ManyToOne(fetch = FetchType.LAZY)
	private User owner;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	@Builder.Default
	private Boolean archived = false;

}

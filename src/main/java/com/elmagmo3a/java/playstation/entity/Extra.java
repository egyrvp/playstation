package com.elmagmo3a.java.playstation.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Extra implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Type {
		ADMIN, SHIFTER
	}

	public enum Status {
		AVAILABLE, NON_AVAILABLE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Float price;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String createdBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	private String lastUpdateBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;
	
	@Builder.Default
	private Boolean archived = false;

}

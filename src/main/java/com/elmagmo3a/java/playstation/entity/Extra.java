package com.elmagmo3a.java.playstation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User createdBy;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonBackReference(value = "reservations")
	@ToString.Exclude
	private List<Reservation> reservations;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonBackReference(value = "archives")
	@ToString.Exclude
	private List<Archive> archives;
	
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

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Status {
		ACTIVE, FINISHED, WAITING, CANCELED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String clientName;
	private Date startDate;
	private Date endDate;
	private Float price;
	private Float paid;
	private Float actualPaid;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User createdBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Device.class)
	private Device device;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "reservationExtras", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "extra_id", referencedColumnName = "id"))
	private List<Extra> extras;

	@Builder.Default
	private Boolean archived = false;

}

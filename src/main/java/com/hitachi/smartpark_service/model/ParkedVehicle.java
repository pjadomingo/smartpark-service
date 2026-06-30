package com.hitachi.smartpark_service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

@Entity
public class ParkedVehicle {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parkedvehicle_seq")
    @SequenceGenerator(name = "parkedvehicle_seq", sequenceName = "parkedvehicle_seq", allocationSize = 1)
    private Long id;
	
	@Column
	private String licensePlate;
	
	@Column
	private String parkingLotId;
	
	@Column
	private boolean isParked;
	
	@Column
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date checkInDate = new Date();
	
	@PrePersist
	protected void onCreate() {
	    checkInDate = new Date(); // set default when first persisted
	}
	
	@PreUpdate
	protected void onUpdate() {
	    checkInDate = new Date(); // refresh whenever updated
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(String parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	public boolean isParked() {
		return isParked;
	}

	public void setParked(boolean isParked) {
		this.isParked = isParked;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}	
	
	
}

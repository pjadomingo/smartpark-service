package com.hitachi.smartpark_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class ParkingLot {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parkinglot_seq")
    @SequenceGenerator(name = "parkinglot_seq", sequenceName = "parkinglot_seq", allocationSize = 1)
	private Long id;

	@Column(length = 50, nullable = false, unique=true)
	private String lotId;
	
	@Column(length = 20)
	private String location;
	@Column
	private int capacity;
	@Column
	private int occupiedSpaces;
	@Column
	private double costPerMinute;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getOccupiedSpaces() {
		return occupiedSpaces;
	}
	public void setOccupiedSpaces(int occupiedSpaces) {
		this.occupiedSpaces = occupiedSpaces;
	}
	public double getCostPerMinute() {
		return costPerMinute;
	}
	public void setCostPerMinute(double costPerMinute) {
		this.costPerMinute = costPerMinute;
	}
	public boolean isFull() {
		return occupiedSpaces >= capacity;
	}
	
	
}

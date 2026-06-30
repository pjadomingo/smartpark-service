package com.hitachi.smartpark_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SmartParkDto {

	@NotBlank(message = "licensePlate is required field")
	@Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate must contain only letters, numbers, and dashes")
	private String licensePlate;

	@NotBlank(message = "lotId is required field")
	@Size(max = 50, message = "Lot ID must not exceed 50 characters")
	private String lotId;

	// Constructors
	public SmartParkDto() {
	}

	public SmartParkDto(String licensePlate, String lotId) {
		this.licensePlate = licensePlate;
		this.lotId = lotId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
}

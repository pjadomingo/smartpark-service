package com.hitachi.smartpark_service.dto;

import com.hitachi.smartpark_service.enums.VehicleType;
import com.hitachi.smartpark_service.model.Vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class VehicleDto {

    private Long id;
    
    @NotBlank(message = "licensePlate is required field")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate must contain only letters, numbers, and dashes")
    private String licensePlate;
    
    @NotBlank(message = "ownerName is required field")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name must contain only letters and spaces")
    private String ownerName;
    
    @NotNull(message = "Type is required field")
    @Pattern(regexp = "Car|Motorcycle|Truck", message = "Type must be Car or Motorcycle or Truck")
    private String type;

    // Constructors
    public VehicleDto() {}

    public VehicleDto(Long id, String licensePlate, String ownerName, String type) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.type = type;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static VehicleDto fromEntity(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return new VehicleDto(
            vehicle.getId(),
            vehicle.getLicensePlate(),
            vehicle.getOwnerName(),
            vehicle.getType()!= null ? vehicle.getType().name() : null
        );
    }

    public Vehicle toEntity() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(this.licensePlate);
        vehicle.setOwnerName(this.ownerName);
        vehicle.setType(this.type != null ? VehicleType.valueOf(this.type) : null);
        
        return vehicle;
    }
}

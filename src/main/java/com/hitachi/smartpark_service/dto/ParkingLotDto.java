package com.hitachi.smartpark_service.dto;

import com.hitachi.smartpark_service.model.ParkingLot;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ParkingLotDto {

    private Long id;
    
    @NotBlank(message = "lotId is required field")
    @Size(max = 50, message = "Lot ID must not exceed 50 characters")
    private String lotId;
    
    @NotBlank(message = "location is required field")
    private String location;
    
    @NotNull(message = "capacity is required field")
    @Min(value = 1, message = "capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "occupiedSpaces is required field")
    @Min(value = 0, message = "occupiedSpaces cannot be negative")
    private Integer occupiedSpaces;

    @NotNull(message = "costPerMinute is required field")
    @Positive(message = "costPerMinute must be greater than 0")
    private Double costPerMinute;


    // Constructors
    public ParkingLotDto() {}

    public ParkingLotDto(Long id, String lotId, String location, int capacity, int occupiedSpaces, double costPerMinute) {
        this.id = id;
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = occupiedSpaces;
        this.costPerMinute = costPerMinute;
    }

    // Getters and Setters
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

 // Mapping: Entity → DTO
    public static ParkingLotDto fromEntity(ParkingLot parkingLot) {
        if (parkingLot == null) {
            return null;
        }
        ParkingLotDto dto = new ParkingLotDto();
        dto.setId(parkingLot.getId());
        dto.setLotId(parkingLot.getLotId());
        dto.setLocation(parkingLot.getLocation());
        dto.setCapacity(parkingLot.getCapacity());
        dto.setOccupiedSpaces(parkingLot.getOccupiedSpaces());
        dto.setCostPerMinute(parkingLot.getCostPerMinute());
        return dto;
    }

    // Mapping: DTO → Entity
    public ParkingLot toEntity() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(this.getId()); // optional: usually null for new inserts
        parkingLot.setLotId(this.getLotId());
        parkingLot.setLocation(this.getLocation());
        parkingLot.setCapacity(this.getCapacity());
        parkingLot.setOccupiedSpaces(this.getOccupiedSpaces());
        parkingLot.setCostPerMinute(this.getCostPerMinute());
        return parkingLot;
    }

}


package com.hitachi.smartpark_service.dto;

import java.util.Date;

import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;

public class ParkedVehicleDto {

    private Long id;
    private String licensePlate;
    private String parkingLotId;
    private boolean isParked;
    private double parkingCost; // computed field

    // Constructors
    public ParkedVehicleDto() {}

    public ParkedVehicleDto(Long id, String licensePlate, String parkingLotId, boolean isParked, double parkingCost) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.parkingLotId = parkingLotId;
        this.isParked = isParked;
        this.parkingCost = parkingCost;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getParkingLotId() { return parkingLotId; }
    public void setParkingLotId(String parkingLotId) { this.parkingLotId = parkingLotId; }

    public boolean isParked() { return isParked; }
    public void setParked(boolean parked) { isParked = parked; }

    public double getParkingCost() { return parkingCost; }
    public void setParkingCost(double parkingCost) { this.parkingCost = parkingCost; }

    // Mapping: Entity → DTO with cost calculation
    public static ParkedVehicleDto fromEntity(ParkedVehicle parkedVehicle, ParkingLot parkingLot) {
        if (parkedVehicle == null || parkingLot == null) return null;

        // Calculate minutes difference
        long diffMillis = new Date().getTime() - parkedVehicle.getCheckInDate().getTime();
        long diffMinutes = diffMillis / (1000 * 60);

        double cost = diffMinutes * parkingLot.getCostPerMinute();

        return new ParkedVehicleDto(
            parkedVehicle.getId(),
            parkedVehicle.getLicensePlate(),
            parkedVehicle.getParkingLotId(),
            parkedVehicle.isParked(),
            cost
        );
    }
}


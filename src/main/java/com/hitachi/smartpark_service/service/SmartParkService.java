package com.hitachi.smartpark_service.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.smartpark_service.dto.ParkedVehicleDto;
import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;

@Service
public class SmartParkService {

	@Autowired
	public ParkingLotService parkingLotService;

	@Autowired
	public ParkedVehicleService parkedVehicleService;

	@Autowired
	public VehicleService vehicleService;

	
	public Map<Long, ParkedVehicle> getCheckedInVehicles() {
		
		return parkedVehicleService.getCheckedInVehicles();
	}
	
	public void removeCheckInVehicles(Long id) {
		 parkedVehicleService.getCheckedInVehicles().remove(id);
	}
	
	public ParkedVehicle checkInVehicle(String licensePlate, String parkingLotId) {
		try {
			// Find vehicle
			Vehicle vehicle = vehicleService.getByLicensePlate(licensePlate);
			if (vehicle == null) {
				System.out.println("Vehicle with license plate " + licensePlate + " not found.");
				return null;
			}

			// Find parking lot
			ParkingLot parkingLot = parkingLotService.findByParkingLotId(parkingLotId);
			if (parkingLot == null) {
				System.out.println("Parking lot with ID " + parkingLotId + " not found.");
				return null;
			}
			
			//check vehicle if already parked
			if(!parkedVehicleService.isVehicleParked(licensePlate, parkingLotId)) {
				// Update occupied spaces
				if (parkingLotService.occupySpace(parkingLotId)) {
					// Check-in parked vehicle
					return parkedVehicleService.checkIn(parkingLot, vehicle);
				}

			}
		} catch (Exception e) {
			System.out.println("Error during check-in: " + e.getMessage());
		}

		return null;
	}

	public ParkedVehicleDto checkOutVehicle(String licensePlate, String parkingLotId) {
		try {
			// Find vehicle
			Vehicle vehicle = vehicleService.getByLicensePlate(licensePlate);
			if (vehicle == null) {
				System.out.println("Vehicle with license plate " + licensePlate + " not found.");
				return null;
			}

			// Find parking lot
			ParkingLot parkingLot = parkingLotService.findByParkingLotId(parkingLotId);
			if (parkingLot == null) {
				System.out.println("Parking lot with ID " + parkingLotId + " not found.");
				return null;
			}
			if(parkedVehicleService.isVehicleParked(licensePlate, parkingLotId)) {
				// Update occupied spaces
				if (parkingLotService.unOccupySpace(parkingLotId)) {

					// Check-in parked vehicle
					return parkedVehicleService.checkOut(parkingLot, vehicle);
				}
			}

		} catch (Exception e) {
			System.out.println("Error during check-in: " + e.getMessage());
		}

		return null;
	}
}

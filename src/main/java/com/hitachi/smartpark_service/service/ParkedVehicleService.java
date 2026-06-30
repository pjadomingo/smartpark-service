package com.hitachi.smartpark_service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.smartpark_service.dto.ParkedVehicleDto;
import com.hitachi.smartpark_service.mapper.ParkedVehicleMapper;
import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.repository.ParkedVehicleRepository;

@Service
public class ParkedVehicleService {

	@Autowired
	public ParkedVehicleRepository repository;

	// Cache of currently checked-in vehicles
	private final Map<Long, ParkedVehicle> checkedInVehicles = new ConcurrentHashMap<>();

	public Map<Long, ParkedVehicle> getCheckedInVehicles() {
		return checkedInVehicles;
	}

	public List<ParkedVehicle> getParkedVehiclesByLot(String parkingLotId) {
		try {
			return repository.findByParkingLotIdAndIsParked(parkingLotId, true);
		} catch (Exception e) {
			System.out.println("Error retrieving parked vehicles: " + e.getMessage());
		}
		
		return List.of();
	}

	public boolean isVehicleParked(String licensePlate, String parkingLotId) {

		try {
			return repository.existsByLicensePlateAndParkingLotIdAndIsParked(licensePlate, parkingLotId, true);
		} catch (Exception e) {

			System.out.println("Error retrieving parked vehicles: " + e.getMessage());
		}

		return false;
	}

	public ParkedVehicle checkIn(ParkingLot parkingLot, Vehicle vehicle) {
		try {
			boolean isCheckIn = true;
			// Check if record already exists
			Optional<ParkedVehicle> existing = repository.findByLicensePlateAndIsParked(vehicle.getLicensePlate(),
					isCheckIn);

			ParkedVehicle parkedVehicle;
			if (!existing.isPresent()) {
				// Create new record
				parkedVehicle = ParkedVehicleMapper.mapToParkedVehicle(parkingLot, vehicle, isCheckIn);
				repository.save(parkedVehicle);
				
				checkedInVehicles.put(parkedVehicle.getId(), parkedVehicle);
				return parkedVehicle;
			}

		} catch (Exception e) {
			System.out.println("Error: SS" + e.getStackTrace());
		}

		return null;
	}

	public ParkedVehicleDto checkOut(ParkingLot parkingLot, Vehicle vehicle) {
		try {
			boolean isCheckIn = true;
			// Check if record already exists
			Optional<ParkedVehicle> existing = repository.findByLicensePlateAndIsParked(vehicle.getLicensePlate(),
					isCheckIn);

			ParkedVehicle parkedVehicle;
			if (existing.isPresent()) {
				parkedVehicle = existing.get();
				parkedVehicle.setParked(!isCheckIn);

				repository.save(parkedVehicle);

				checkedInVehicles.remove(parkedVehicle.getId());
				
				return ParkedVehicleDto.fromEntity(parkedVehicle, parkingLot);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		}

		return null;
	}

}

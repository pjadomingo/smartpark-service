package com.hitachi.smartpark_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.smartpark_service.dto.VehicleDto;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.repository.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    public VehicleRepository repository;

    public VehicleDto create(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleDto.toEntity();
        
        try {
        	if(!repository.existsByLicensePlate(vehicle.getLicensePlate())) {
                vehicle = repository.save(vehicle);
                
                vehicleDto = VehicleDto.fromEntity(vehicle);
                vehicleDto.setSuccessStatusTransaction("Registered Successfully.");
        	}else {
        		vehicleDto.setFailedStatusTransaction("Registered Failed, duplicate license plate.");
        	}
        	
            return vehicleDto;
        	
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public Vehicle getByLicensePlate(String licensePlate) {
        try {
            return repository.findByLicensePlate(licensePlate)
                             .orElse(null); // return null if not found
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}

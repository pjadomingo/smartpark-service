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

    public Vehicle create(VehicleDto accountDto) {
        Vehicle vehicle = accountDto.toEntity();
        try {
            vehicle = repository.save(vehicle);
            return vehicle;
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

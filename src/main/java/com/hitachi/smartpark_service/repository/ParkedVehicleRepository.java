package com.hitachi.smartpark_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hitachi.smartpark_service.model.ParkedVehicle;


@Repository
public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, Long> {
    
	Optional<ParkedVehicle> findByLicensePlateAndParkingLotIdAndIsParked(String licensePlate, String parkingLotId, boolean isParked);
	Optional<ParkedVehicle> findByLicensePlateAndIsParked(String licensePlate, boolean isParked);
	boolean existsByLicensePlateAndParkingLotIdAndIsParked(String licensePlate, String parkingLotId, boolean isParked);
	List<ParkedVehicle> findByParkingLotIdAndIsParked(String parkingLotId, boolean isParked);
}



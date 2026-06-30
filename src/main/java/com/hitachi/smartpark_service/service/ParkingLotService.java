package com.hitachi.smartpark_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.smartpark_service.dto.ParkingLotDto;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.repository.ParkingLotRepository;

@Service
public class ParkingLotService {

	@Autowired
	public ParkingLotRepository repository;

	public ParkingLot create(ParkingLotDto accountDto) {
		ParkingLot vehicle = accountDto.toEntity();

		try {

			vehicle = repository.save(vehicle);

			return vehicle;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return null;
	}
	
    public ParkingLot findByLotId(String lotId) {
        try {
            return repository.findByLotId(lotId)
                             .orElse(null); 
        } catch (Exception e) {
            System.out.println("Error finding ParkingLot: " + e.getMessage());
        }
        
        return null;
    }
	
	public boolean occupySpace(String lotId) {
	    try {
	        Optional<ParkingLot> optionalLot = repository.findByLotId(lotId);

	        if (optionalLot.isPresent()) {
	            ParkingLot pl = optionalLot.get();
	            
	            if(!pl.isFull()) {
		            pl.setOccupiedSpaces(pl.getOccupiedSpaces() + 1); // increment by 1
		             repository.save(pl); // persist the change
		             
		             return true;
	            }
	            
	        }

	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    

        return false;
	}
	
	public boolean unOccupySpace(String lotId) {
	    try {
	        Optional<ParkingLot> optionalLot = repository.findByLotId(lotId);

	        if (optionalLot.isPresent()) {
	            ParkingLot pl = optionalLot.get();
	            
	            if(pl.getOccupiedSpaces() > 0) {
		            pl.setOccupiedSpaces(pl.getOccupiedSpaces() - 1); // decrement by 1
		            repository.save(pl); // persist the change
	            
		            return true;
	            }
	            
	        }

	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    

        return false;
	}

}

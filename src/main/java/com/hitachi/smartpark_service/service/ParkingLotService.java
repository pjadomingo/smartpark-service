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

	public ParkingLotDto create(ParkingLotDto parkinglotDto) {
		ParkingLot parkinglot = parkinglotDto.toEntity();

		try {

			if(!repository.existsByLotId(parkinglot.getLotId())) {
				parkinglot = repository.save(parkinglot);
				parkinglotDto = ParkingLotDto.fromEntity(parkinglot);
				
				parkinglotDto.setSuccessStatusTransaction("Registered Successfully.");
			}else {
				parkinglotDto.setFailedStatusTransaction("Registered Failed, duplicate lot id.");
			}
			

			return parkinglotDto;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return null;
	}
	
    public ParkingLotDto findByLotId(String lotId) {
        try {
        	ParkingLot pl = repository.findByLotId(lotId)
                             .orElse(null); 

    		ParkingLotDto dto;
        	if(pl == null) {
        		dto = new ParkingLotDto(null, lotId, "", 0, 0, 0);
        		dto.setFailedStatusTransaction("Not Found Parking Lot.");
        		
        	}else {
        		 dto = ParkingLotDto.fromEntity(pl);
        		 dto.setSuccessStatusTransaction("Found Parking lot.");
        	}
        	
        	return dto;
        
        } catch (Exception e) {
            System.out.println("Error finding ParkingLot: " + e.getMessage());
        }
        
        return null;
    }
    
    public ParkingLot findByParkingLotId(String lotId) {
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

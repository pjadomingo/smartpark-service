package com.hitachi.smartpark_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.smartpark_service.dto.ParkedVehicleDto;
import com.hitachi.smartpark_service.dto.SmartParkDto;
import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.service.SmartParkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/smartpark")
public class SmartParkController {

	public SmartParkService service;
	
	public SmartParkController(SmartParkService service) {
		this.service = service;
	}
	

	@PostMapping("/checkIn/{lotId}/{licensePlate}")
	public ResponseEntity<?> checkIn(@PathVariable String lotId, @PathVariable String licensePlate) {
		try {
			ParkedVehicle response = service.checkInVehicle(licensePlate, lotId);

			if(response != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong.");
	}
	
	@PostMapping("/checkOut/{lotId}/{licensePlate}")
	public ResponseEntity<?> checkOut(@PathVariable String lotId, @PathVariable String licensePlate) {
		try {
			ParkedVehicleDto response = service.checkOutVehicle(licensePlate, lotId);

			if(response != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong.");
	}
}

package com.hitachi.smartpark_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.smartpark_service.dto.VehicleDto;
import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.service.ParkedVehicleService;
import com.hitachi.smartpark_service.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

	public VehicleService service;
	
	public ParkedVehicleService parkedVehicleService;

	public VehicleController(VehicleService service, ParkedVehicleService parkedVehicleService) {
		this.service = service;
		this.parkedVehicleService = parkedVehicleService;
	}

	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody VehicleDto body) {
		try {
			VehicleDto response = service.create(body);
			if(response != null) {
				return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong");
	}
	
	@GetMapping("/parked/{lotId}")
	public ResponseEntity<?> get(@PathVariable String lotId) {
		try {
			List<ParkedVehicle> response = parkedVehicleService.getParkedVehiclesByLot(lotId);
			if(response != null) {
				 return ResponseEntity.status(!response.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(response);
			}			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong");
	}
}

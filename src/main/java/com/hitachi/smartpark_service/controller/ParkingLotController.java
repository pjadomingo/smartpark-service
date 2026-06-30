package com.hitachi.smartpark_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.smartpark_service.dto.ParkingLotDto;
import com.hitachi.smartpark_service.dto.VehicleDto;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.service.ParkingLotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/parkinglot")
public class ParkingLotController {

	public ParkingLotService service;

	public ParkingLotController(ParkingLotService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody ParkingLotDto body) {
		try {
			ParkingLot response = service.create(body);

			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong");
	}

	@GetMapping("/{lotId}")
	public ResponseEntity<?> get(@PathVariable String lotId) {
		try {
			ParkingLot response = service.findByLotId(lotId);

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong");
	}
}

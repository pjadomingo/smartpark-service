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
import com.hitachi.smartpark_service.model.ParkingLot;
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
			ParkingLotDto response = service.create(body);

			if(response != null) {
				return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong");
	}

	@GetMapping("/{lotId}")
	public ResponseEntity<?> get(@PathVariable String lotId) {
		try {
			ParkingLotDto response = service.findByLotId(lotId);
			if(response != null) {
				 return ResponseEntity.status(response.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(response);
			}			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Something went wrong");
	}
}

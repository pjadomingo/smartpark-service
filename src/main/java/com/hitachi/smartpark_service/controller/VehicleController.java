package com.hitachi.smartpark_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.smartpark_service.dto.VehicleDto;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

	public VehicleService service;

	public VehicleController(VehicleService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody VehicleDto body) {
		try {
			Vehicle response = service.create(body);
			if(response != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ResponseEntity.badRequest().body("Bad Request");
	}
}

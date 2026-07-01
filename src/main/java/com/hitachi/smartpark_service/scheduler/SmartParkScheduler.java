package com.hitachi.smartpark_service.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.service.SmartParkService;

@Service
public class SmartParkScheduler {

    @Autowired
    public SmartParkService service;

    @Scheduled(fixedRate = 60000) // every minute
    public void autoCheckOutVehicles() {
        Date now = new Date();

        for (ParkedVehicle pv : service.getCheckedInVehicles().values()) {
            long diffMillis = now.getTime() - pv.getCheckInDate().getTime();
            long diffMinutes = diffMillis / (1000 * 60);

            if (diffMinutes > 15) {
            	
            	service.checkOutVehicle(pv.getLicensePlate(), pv.getParkingLotId());

            	//remove in the cache
            	service.removeCheckInVehicles(pv.getId());

                System.out.println("Auto checked-out vehicle: " + pv.getLicensePlate());
            }
        }
    }
}


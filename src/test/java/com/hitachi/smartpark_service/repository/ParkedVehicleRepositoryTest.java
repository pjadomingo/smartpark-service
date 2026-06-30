package com.hitachi.smartpark_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hitachi.smartpark_service.model.ParkedVehicle;

@DataJpaTest
class ParkedVehicleRepositoryTest {

    @Autowired
    private ParkedVehicleRepository repository;

    private ParkedVehicle createParkedVehicle(String licensePlate, String lotId, boolean isParked) {
        ParkedVehicle pv = new ParkedVehicle();
        pv.setLicensePlate(licensePlate);
        pv.setParkingLotId(lotId);
        pv.setParked(isParked);
        pv.setCheckInDate(new Date());
        return repository.save(pv);
    }

    @Test
    void testFindByLicensePlateAndParkingLotIdAndIsParked() {
        createParkedVehicle("ABC-123", "LOT-A1", true);

        Optional<ParkedVehicle> result =
                repository.findByLicensePlateAndParkingLotIdAndIsParked("ABC-123", "LOT-A1", true);

        assertThat(result).isPresent();
        assertThat(result.get().getLicensePlate()).isEqualTo("ABC-123");
    }

    @Test
    void testFindByLicensePlateAndIsParked() {
        createParkedVehicle("XYZ-999", "LOT-B2", true);

        Optional<ParkedVehicle> result =
                repository.findByLicensePlateAndIsParked("XYZ-999", true);

        assertThat(result).isPresent();
        assertThat(result.get().getParkingLotId()).isEqualTo("LOT-B2");
    }

    @Test
    void testExistsByLicensePlateAndParkingLotIdAndIsParked() {
        createParkedVehicle("CAR-456", "LOT-C3", true);

        boolean exists = repository.existsByLicensePlateAndParkingLotIdAndIsParked("CAR-456", "LOT-C3", true);

        assertThat(exists).isTrue();
    }

    @Test
    void testFindByParkingLotIdAndIsParked() {
        createParkedVehicle("TEST-111", "LOT-D4", true);
        createParkedVehicle("TEST-222", "LOT-D4", true);
        createParkedVehicle("TEST-333", "LOT-D4", false);

        List<ParkedVehicle> parkedVehicles = repository.findByParkingLotIdAndIsParked("LOT-D4", true);

        assertThat(parkedVehicles).hasSize(2);
        assertThat(parkedVehicles).extracting("licensePlate")
                .containsExactlyInAnyOrder("TEST-111", "TEST-222");
    }
}

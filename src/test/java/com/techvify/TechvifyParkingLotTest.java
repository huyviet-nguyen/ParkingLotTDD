package com.techvify;

import com.github.javafaker.Faker;
import com.techvify.entity.IParkingLot;
import com.techvify.entity.impl.TechvifyParkingLot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

//    Behaviors:
//    + Given full parking lot, when n cars try to park, expect N time of Exception
//    + Given random parking lot with at least 2 empty slots, when 1 car parks, nearer slots will be assigned (smaller index)
//    + Given not full parking lot, when car A ( A parked already) tries to park, then expect an Exception
//    + Given not empty parking lot, when car B (B didn't park) tries to leave, then expect an Exception
//    + Given not empty parking lot, when car A(already parked) tries to leave after n hours, then calculate ticket bill (0 < n <= 2 ? 10 : 10*(n-2) + 10)

class TechvifyParkingLotTest {

    IParkingLot parkingLot;
    int initSize = 0;

    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        initSize = ThreadLocalRandom.current().nextInt(1, 50);
        parkingLot = TechvifyParkingLot.create(initSize);
    }

    private String getRandomNumberPlate() {
        return faker.dragonBall().character() + randomInt(0, 999);
    }


    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    @Test
    void givenFullParkingLot_whenCarAPark_thenExpectAnException() {
        for (int i = 0; i < initSize; i++) {
            parkingLot.park(getRandomNumberPlate());
        }

        Exception expected = assertThrows(RuntimeException.class, () -> {
            parkingLot.park(getRandomNumberPlate());
        });
        assertNotNull(expected);
        parkingLot.status();
    }

    @Test
    void givenParkingLotWith2EmptySlot_whenCarAPark_nearerSlotBeAssigned() {
        int randomSlot1 = randomInt(1, initSize);
        int randomSlot2 = randomInt(1, initSize);
        while (randomSlot2 == randomSlot1) {
            randomSlot2 = randomInt(1, initSize);
        }

        for (int i = 0; i < initSize; i++) {
            if (i == randomSlot1 - 1) {
                parkingLot.park("RANDOM1");
            } else if (i == randomSlot2 - 1) {
                parkingLot.park("RANDOM2");
            } else {
                parkingLot.park(getRandomNumberPlate());
            }
        }

        parkingLot.leave("RANDOM1", randomInt(1, 99));
        parkingLot.leave("RANDOM2", randomInt(1, 99));

        assertEquals(parkingLot.park("RANDOM"), Math.min(randomSlot1, randomSlot2));
        parkingLot.status();
    }

    @Test
    void givenRandomParkingLot_whenCarAParksTwice_thenExpectException() {
        parkingLot.park("Car-A");

        Exception expected = assertThrows(RuntimeException.class, () -> {
            parkingLot.park("Car-A");
        });

        assertNotNull(expected);
        parkingLot.status();
    }

    @Test
    void givenRandomParkingLot_whenCarANotParkSTryToExit_thenExpectException() {
        parkingLot.park("Car-A");
        parkingLot.leave("Car-A", randomInt(1, 99));

        Exception expected = assertThrows(RuntimeException.class, () -> {
            parkingLot.leave("Car-A", randomInt(1, 99));
        });

        assertNotNull(expected);
        parkingLot.status();
    }

    @Test
    void givenRandomParkingLot_whenCarAExitBefore2Hour_thenExpectCorrectBillingTicket() {
        int hours = 1;
        int expectedBill = 10;
        parkingLot.park("Car-A");

        assertEquals(expectedBill, parkingLot.calculateBill(hours));
        parkingLot.status();
    }

    @Test
    void givenRandomParkingLot_whenCarAExitExceed2Hour_thenExpectCorrectBillingTicket() {
        int hours = 3;
        int expectedBill = 20;
        parkingLot.park("Car-A");

        assertEquals(expectedBill, parkingLot.calculateBill(hours));
        parkingLot.status();
    }

    @Test
    void givenRandomParkingLot_whenCarAExitAfter2Hour_thenExpectCorrectBillingTicket() {
        int hours = 2;
        int expectedBill = 10;
        parkingLot.park("Car-A");

        assertEquals(expectedBill, parkingLot.calculateBill(hours));
        parkingLot.status();
    }

}
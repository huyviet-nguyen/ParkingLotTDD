package com.techvify.entity;

import static com.techvify.common.Logger.log;

public interface IParkingLot {

    int park(String numberPlate);

    void carExit(String numberPlate);

    int getSlotNumber(String numberPlate);

    int calculateBill(int hour);

    void status();

    default void leave(String numberplate, int hour) {
        int slotNumber = getSlotNumber(numberplate);
        carExit(numberplate);
        log("Registration Number " + numberplate + " from Slot " + slotNumber + " has left with Charge " + calculateBill(hour) + "$");
    }

}

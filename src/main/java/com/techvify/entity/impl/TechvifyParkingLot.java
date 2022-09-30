package com.techvify.entity.impl;

import com.techvify.entity.IParkingLot;
import org.apache.commons.lang3.StringUtils;

import static com.techvify.common.Logger.log;

public class TechvifyParkingLot implements IParkingLot {

    public static IParkingLot INSTANCE = null;

    private static final String EMPTY_SLOT = "--";

    private final int initialSize;

    private int availSlotsNumber;

    private volatile String[][] data;


    private TechvifyParkingLot(int initialSize) {
        this.initialSize = initialSize;
        this.availSlotsNumber = initialSize;
        data = new String[2][initialSize];
        for (int i = 0; i < initialSize; i++) {
            data[0][i] = String.valueOf(i);
            data[1][i] = EMPTY_SLOT;
        }
    }

    /**
     * Reset the singleton instance
     * @param initialSize
     * @return
     */
    public static IParkingLot create(int initialSize) {
        log("Created parking lot with " + initialSize + " slots");
        INSTANCE = new TechvifyParkingLot(initialSize);
        return INSTANCE;
    }


    /**
     * get the slot number of car parking in
     * Worst case : O(initialSize)
     *
     * @param numberPlate
     * @return position, or -1 if no car found
     */
    @Override
    public int getSlotNumber(String numberPlate) {
        int position = 0;
        for (int i = 0; i < initialSize; i++) {
            if (StringUtils.equals(data[1][i], numberPlate)) {
                position = i + 1;
                break;
            }
        }
        return position;
    }

    /**
     * Worst case O(2*initialSize)
     *
     * @param numberPlate
     * @return
     */
    @Override
    public int park(String numberPlate) {

        if (availSlotsNumber == 0) {
            throw new RuntimeException("Sorry, parking lot is full");
        }

        if (getSlotNumber(numberPlate) != 0) {
            throw new RuntimeException("This car is already inside");
        }

        int slotPosition = 0;

        for (int i = 0; i < data[0].length; i++) {
            if (StringUtils.equals(data[1][i], EMPTY_SLOT)) {
                data[1][i] = numberPlate;
                slotPosition = i + 1;
                break;
            }
        }

        availSlotsNumber--;
        log("Allocated slot number :" + slotPosition);
        return slotPosition;
    }


    @Override
    public void carExit(String numberPlate) {
        int index = getSlotNumber(numberPlate) - 1;

        if (index != -1) {
            data[1][index] = EMPTY_SLOT;
            availSlotsNumber++;
        } else {
            throw new RuntimeException("Registration Number " + numberPlate + " not found");
        }
    }

    @Override
    public int calculateBill( int hour) {
        return hour <= 2 ? 10 : (hour - 2) * 10 + 10;
    }

    @Override
    public void status() {
        log("Slot No. | Registration No.");
        String separator = ".        ";
        int slotTaken = 0;

        for (int i = 1; i <= initialSize; i++) {
            if (!StringUtils.equals(data[1][i - 1], EMPTY_SLOT)) {
                log(i + separator + data[1][i - 1]);
                slotTaken++;
            }
        }
        log("Allocated slot number: " + slotTaken);
    }


}

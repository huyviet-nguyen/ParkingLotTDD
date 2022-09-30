package com.techvify.strategy.command;

import com.techvify.entity.impl.TechvifyParkingLot;
import com.techvify.strategy.CommandStrategy;

public class Park implements CommandStrategy {

    @Override
    public void executeWithException(String[] args) {
        String numberPlate = args[0];
        TechvifyParkingLot.INSTANCE.park(numberPlate);
    }
}

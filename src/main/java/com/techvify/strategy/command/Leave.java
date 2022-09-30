package com.techvify.strategy.command;

import com.techvify.entity.impl.TechvifyParkingLot;
import com.techvify.strategy.CommandStrategy;

public class Leave implements CommandStrategy {
    @Override
    public void executeWithException(String[] args) {
        String numberPlate = args[0];
        int hours = Integer.parseInt(args[1]);
        TechvifyParkingLot.INSTANCE.leave(numberPlate, hours);
    }
}

package com.techvify.strategy.command;

import com.techvify.entity.impl.TechvifyParkingLot;
import com.techvify.strategy.CommandStrategy;

public class Create implements CommandStrategy {

    @Override
    public void executeWithException(String[] args) {
        int initalSize = Integer.parseInt(args[0]);
        TechvifyParkingLot.create(initalSize);
    }
}

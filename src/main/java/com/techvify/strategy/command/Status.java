package com.techvify.strategy.command;

import com.techvify.entity.impl.TechvifyParkingLot;
import com.techvify.strategy.CommandStrategy;

public class Status implements CommandStrategy {

    @Override
    public void executeWithException(String[] args) {
        TechvifyParkingLot.INSTANCE.status();
    }
}

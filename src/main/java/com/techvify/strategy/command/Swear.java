package com.techvify.strategy.command;

import com.techvify.strategy.CommandStrategy;

public class Swear implements CommandStrategy {
    @Override
    public void executeWithException(String[] args) {
        System.out.println("HOLy MOTHER FUCKING SHIT");
    }
}

package com.techvify;

import com.techvify.strategy.CommandRegistry;
import com.techvify.strategy.CommandStrategy;
import com.techvify.strategy.StringCommandStrategyFacade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        StringCommandStrategyFacade commandStrategy = new StringCommandStrategyFacade();
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\huynv\\Desktop\\gRPC\\ParkingLotTDD\\src\\main\\java\\com\\techvify\\command.txt"));
            String line = reader.readLine();
            while (line != null) {
                commandStrategy.executeStrategy(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

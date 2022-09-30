package com.techvify.strategy;

import static com.techvify.common.Logger.log;

public interface CommandStrategy {

    void executeWithException(String[] args);

    default void execute(String[] args) {
        try {
            executeWithException(args);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }
}
package com.techvify.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


public class StringCommandStrategyFacade {
    public void executeStrategy(String fullCommand) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        CommandRegistry<CommandStrategy> commandRegistry = new CommandRegistry<>(CommandStrategy.class, "com.techvify.strategy.command");
        if (StringUtils.isEmpty(fullCommand)) {
            return;
        }

        String[] fullCommandSeparated = fullCommand.split(" ");
        String command = fullCommandSeparated[0];
        String[] argsList = Arrays.copyOfRange(fullCommandSeparated, 1, fullCommandSeparated.length);

        CommandStrategy strategy = commandRegistry.getStrategy(command);
        strategy.execute(argsList);
    }

}

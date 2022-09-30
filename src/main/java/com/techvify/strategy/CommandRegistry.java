package com.techvify.strategy;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class CommandRegistry<STRATEGY_INTERFACE> {
    private final Class<STRATEGY_INTERFACE> type;
    private final HashMap<String, Class<?>> commandMap = new HashMap<>();

    public HashMap<String, Class<?>> getCommandMap() {
        return commandMap;
    }

    public CommandRegistry(Class<STRATEGY_INTERFACE> type, String packageName) {
        this.type = type;
        scanForImplementation(packageName);
    }

    private void scanForImplementation(String packageName) {
        Reflections reflections = new Reflections(packageName);

        Set<Class<? extends STRATEGY_INTERFACE>> allClasses = reflections.getSubTypesOf(type);
        allClasses.forEach(entry -> {
            commandMap.put(entry.getCanonicalName().replace(packageName + ".", "").toLowerCase(Locale.ROOT), entry);
        });
    }

    public STRATEGY_INTERFACE getStrategy(String command) throws InstantiationException, IllegalAccessException {
        if (this.getCommandMap().containsKey(command)) {
            return (STRATEGY_INTERFACE) this.getCommandMap().get(command).newInstance();
        }
        return null;
    }
}

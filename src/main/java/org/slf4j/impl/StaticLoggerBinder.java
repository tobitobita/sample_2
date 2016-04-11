package org.slf4j.impl;

import dsk.sample.slf4j.DskLoggerFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public static final StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    private static final String LOGGER_FACTORY_CLASS_NAME = DskLoggerFactory.class.getName();

    public static String REQUESTED_API_VERSION = "1.7.0"; // !final

    private final DskLoggerFactory loggerFactory;

    private StaticLoggerBinder() {
        this.loggerFactory = new DskLoggerFactory();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return LOGGER_FACTORY_CLASS_NAME;
    }
}

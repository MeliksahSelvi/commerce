package com.commerce.order.service.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.appender.MemoryApender;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public abstract class LoggerTest<T> {

    protected final MemoryApender memoryApender;

    public LoggerTest(Class<T> loggerClass) {
        Logger logger = (Logger) LoggerFactory.getLogger(loggerClass);
        memoryApender = new MemoryApender();
        memoryApender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryApender);
        memoryApender.start();
    }

    protected void destroy() {
        memoryApender.reset();
        memoryApender.stop();
    }
}

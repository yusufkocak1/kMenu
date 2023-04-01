package com.kocak.logserver.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.logging.LogLevel;

import java.util.HashMap;
import java.util.Map;

public class MyLogger {
    private static final Map<String, Logger> LOGGERS_BY_TOPIC = new HashMap<>();

    static {
        LOGGERS_BY_TOPIC.put("k-menu", LogManager.getLogger("k-menu"));
        LOGGERS_BY_TOPIC.put("k-stock", LogManager.getLogger("k-stock"));
    }

    public static void log(LogLevel logLevel, String logSource, String logText, String topic) {
       Logger logger = LOGGERS_BY_TOPIC.get(topic);

        switch (logLevel) {
            case TRACE -> logger.trace("[{}] {}", logSource, logText);
            case DEBUG -> logger.debug("[{}] {}", logSource, logText);
            case INFO -> logger.info("[{}] {}", logSource, logText);
            case WARN -> logger.warn("[{}] {}", logSource, logText);
            case ERROR -> logger.error("[{}] {}", logSource, logText);
            default -> logger.error("Invalid log level");
        }
    }
}
package com.kocak.logserver.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;

public  class Header {
    private LogLevel logLevel;
    private String logSource;

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogSource() {
        return logSource;
    }

    public void setLogSource(String logSource) {
        this.logSource = logSource;
    }
}
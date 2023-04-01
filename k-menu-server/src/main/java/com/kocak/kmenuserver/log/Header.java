package com.kocak.kmenuserver.log;

import org.springframework.boot.logging.LogLevel;

public  class Header {
    private LogLevel logLevel;
    private String logSource;

    public Header(LogLevel logLevel, String logSource) {
        this.logLevel = logLevel;
        this.logSource = logSource;
    }

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
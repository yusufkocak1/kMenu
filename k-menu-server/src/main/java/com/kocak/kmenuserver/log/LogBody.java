package com.kocak.kmenuserver.log;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Builder
@Getter
@ToString
public class LogBody {

    /**
     * Sequence number
     */
    private long sequenceNumber;

    /**
     * Class that issued logging call
     */
    private String sourceClassName;

    /**
     * Method that issued logging call
     */
    private String sourceMethodName;

    /**
     * Non-localized raw message text
     */
    private String message;

    /**
     * Thread ID for thread that issued logging call.
     */
    private int threadID;
    /**
     * stack trace
     */
    private String stackTrace;
    public static LogBodyBuilder builder(String message) {
        return new LogBodyBuilder().message(message);
    }

    @Override
    public String toString() {
        return "{\"LogBody\":{"
                + "                        \"sequenceNumber\":\"" + sequenceNumber + "\""
                + ",                         \"sourceClassName\":\"" + sourceClassName + "\""
                + ",                         \"sourceMethodName\":\"" + sourceMethodName + "\""
                + ",                         \"message\":\"" + message + "\""
                + ",                         \"threadID\":\"" + threadID + "\""
                + ",                         \"stackTrace\":\"" + stackTrace + "\""
                + "}}";
    }
}

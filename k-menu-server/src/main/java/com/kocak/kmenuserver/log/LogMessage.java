package com.kocak.kmenuserver.log;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LogMessage {
    Header header;
    LogBody log;

    public LogMessage(Header header, LogBody log) {
        this.header = header;
        this.log = log;
    }
}

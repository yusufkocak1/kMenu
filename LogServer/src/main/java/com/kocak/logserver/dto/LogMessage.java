package com.kocak.logserver.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LogMessage {
    Header header;
    LogBody log;
}

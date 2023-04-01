package com.kocak.logserver.service;

import com.kocak.logserver.dto.LogMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogConsumer {

    @KafkaListener(topics = {"k-menu", "k-stock"})
    public void receive(ConsumerRecord<?, LogMessage> consumerRecord) {
        LogMessage logMessage = consumerRecord.value();
        LogLevel logLevel = logMessage.getHeader().getLogLevel();
        String topic= consumerRecord.topic();
        String logSource = logMessage.getHeader().getLogSource();
        String logText = logMessage.getLog().toString();
        MyLogger.log(logLevel, logSource, logText, topic);

    }
}
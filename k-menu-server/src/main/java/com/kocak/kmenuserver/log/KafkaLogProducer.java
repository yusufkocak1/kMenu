package com.kocak.kmenuserver.log;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaLogProducer {

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;

    public KafkaLogProducer(KafkaTemplate<String, LogMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(LogMessage logMessage) {
        kafkaTemplate.send("K-Menu", logMessage);
    }
}

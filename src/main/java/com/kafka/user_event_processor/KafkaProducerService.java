package com.kafka.user_event_processor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, String message) {
        kafkaTemplate.send(topicName, key, message);
    }

    public void sendMessageWithHeaders(String key, String message) {

        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("user-id", key.getBytes()));
        ProducerRecord<String, String> record =
                new ProducerRecord <>("user-events-headers", null, key, message, headers);

        kafkaTemplate.send(record);
    }

    public void sendMultipleMessagesWithHeaders(int eventsCount) {
        for(Integer j=1;j<4; j++) {
            for(int i = 1; i <= eventsCount; i++) {
                String message = "Event "+ i;
                sendMessageWithHeaders(j.toString(), message);
            }
        }
    }

    public void sendMultipleMessages(int eventsCount, String uuid) {
        for(int i = 1; i <= eventsCount; i++) {
            String message = "Event "+ i;
            sendMessage(uuid, message);
        }
    }
}

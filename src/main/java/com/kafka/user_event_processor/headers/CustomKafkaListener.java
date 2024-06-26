package com.kafka.user_event_processor.headers;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomKafkaListener {

    private final Logger logger = LoggerFactory.getLogger(CustomKafkaListener.class);

    @KafkaListener(topics = "user-events-headers", groupId = "my-consumer-group")
    public void listen(ConsumerRecord<String, String> record) {
        StringBuilder headersStr = new StringBuilder();
        record.headers().forEach(header -> headersStr.append(header.key()).append("=").append(new String(header.value())).append(", "));
        // Remove the trailing ", " from headersStr
        String headers = headersStr.length() > 2 ? headersStr.substring(0, headersStr.length() - 2) : headersStr.toString();

        logger.info("Consumed message: Key = {}, Value = {}, Headers = {}", record.key(), record.value(), headers);
    }

}
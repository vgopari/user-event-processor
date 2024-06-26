package com.kafka.user_event_processor.producers;

import com.kafka.user_event_processor.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProducerOne {

    private Logger logger = LoggerFactory.getLogger(ProducerOne.class);

    private final KafkaProducerService kafkaProducerService;

    String uuid;

    @Autowired
    public ProducerOne(KafkaProducerService kafkaProducerService) {
        this.uuid = "1";
        logger.info(String.format("Producer One: %s", this.uuid));
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/producer-one")
    public void produceOne() {
        kafkaProducerService.sendMultipleMessages(4, this.uuid);
    }
}

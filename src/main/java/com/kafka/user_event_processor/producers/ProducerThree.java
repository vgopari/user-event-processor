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
public class ProducerThree {

    private Logger logger = LoggerFactory.getLogger(ProducerThree.class);

    private final KafkaProducerService kafkaProducerService;

    String uuid;

    @Autowired
    public ProducerThree(KafkaProducerService kafkaProducerService) {
        this.uuid = "3";
        logger.info(String.format("Producer Three: %s", this.uuid));
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/producer-three")
    public void produceThree() {
        kafkaProducerService.sendMultipleMessages(5, this.uuid);
    }
}

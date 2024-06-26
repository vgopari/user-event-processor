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
public class ProducerTwo {

    private Logger logger = LoggerFactory.getLogger(ProducerTwo.class);

    private final KafkaProducerService kafkaProducerService;

    String uuid;

    @Autowired
    public ProducerTwo(KafkaProducerService kafkaProducerService) {
        this.uuid = "2";
        logger.info(String.format("Producer Two: %s", this.uuid));
        this.kafkaProducerService = kafkaProducerService;
    }


    @GetMapping("/producer-two")
    public void produceTwo() {
        kafkaProducerService.sendMultipleMessages(3, this.uuid);
    }
}

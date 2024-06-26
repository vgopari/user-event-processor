package com.kafka.user_event_processor.producers;

import com.kafka.user_event_processor.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ProducerHeaders {

    private Logger logger = LoggerFactory.getLogger(ProducerHeaders.class);

    private final KafkaProducerService kafkaProducerService;

    String uuid;

    @Autowired
    public ProducerHeaders(KafkaProducerService kafkaProducerService) {
        this.uuid = "1";
        logger.info(String.format("Producer One: %s", this.uuid));
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/producer-headers")
    public void producerWithHeaders() {

        kafkaProducerService.sendMultipleMessagesWithHeaders(new Random().nextInt(6));
    }
}

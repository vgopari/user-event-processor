package com.kafka.user_event_processor.streams;

import com.kafka.user_event_processor.config.KafkaStreamsConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.stereotype.Component;

@Component
public class UserEventsStreams {

    private final Logger logger = LoggerFactory.getLogger(UserEventsStreams.class);

    private final KafkaStreamsConfiguration kStreamsConfig;

    @Autowired
    public UserEventsStreams(@Qualifier("defaultKafkaStreamsConfig")  KafkaStreamsConfiguration kStreamsConfig) {
        this.kStreamsConfig = kStreamsConfig;
    }


    @Autowired
    public void userEventStreamBuilder(StreamsBuilder builder) {
        logger.info("In UserEventsBuilder");
        KStream<String, String> stream = builder.stream("user-events");

        // Group by key (user ID)
        KTable<String, String> groupedStream = stream.groupByKey().reduce((aggValue, newValue) -> aggValue + "," + newValue);

        // Process the grouped messages (e.g., printing them)
        groupedStream.toStream().foreach((key, value) -> logger.info(String.format("User ID: %s Events: %s \n" , key, value)));

        try (KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), kStreamsConfig.asProperties())) {
            kafkaStreams.cleanUp();
            kafkaStreams.start();
            // Add shutdown hook to handle shutdown gracefully
            Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        }
    }
}

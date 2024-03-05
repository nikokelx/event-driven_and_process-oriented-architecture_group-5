package ch.unisg.consumer;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer
{
    public static void main( String[] args ) throws IOException
    {
        KafkaConsumer<String, String> kafkaConsumer;
        try (InputStream props = Resources.getResource("consumer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            kafkaConsumer = new KafkaConsumer<>(properties);
        }

        kafkaConsumer.subscribe(Arrays.asList("global_events", "project_events"));

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                switch (record.topic()) {
                    case "project_events":
                        System.out.println("Received project_events message - key: " + record.key() + " value: " + record.value() + " partition: " + record.partition());
                        break;
                    case "global_events":
                        System.out.println("Received global_events message - key: " + record.key() + " value: " + record.value() + " partition: " + record.partition());
                        break;
                    default:
                        throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
                }
            }
        }
    }
}

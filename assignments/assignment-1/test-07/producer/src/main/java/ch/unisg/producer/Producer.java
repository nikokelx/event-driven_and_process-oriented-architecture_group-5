package ch.unisg.producer;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Producer
{
    private static final String[] global_events = {"project_build", "project_start", "project_end"};
    public static void main( String[] args ) throws IOException
    {
        KafkaProducer<String, String> kafkaProducer;
        try (InputStream props = Resources.getResource("producer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            kafkaProducer = new KafkaProducer<>(properties);
        }

        System.out.println("Amount of partitions " + kafkaProducer.partitionsFor("project_events").size());

        int noOfMessages = 1000000000;

        long startTime = System.nanoTime();
        try {
            for (int i = 0; i < noOfMessages; i++) {
                kafkaProducer.send(new ProducerRecord<String, String>(
                        "project_events", // topic
                        "project_id_" + i, //key
                        "some_value_" + System.nanoTime()) //value
                );

                if (i % 100 == 0) {
                    System.out.println("Sent message number " + i);
                }
            }

            long endTime = System.nanoTime();
            long durationInNanos = endTime - startTime;
            System.out.println("Time taken to send " + noOfMessages + " messages: " + (durationInNanos / 1000000) + " ms.");

        } catch (Throwable throwable) {
            System.out.println(throwable.getStackTrace());
        } finally {
            kafkaProducer.close();
        }
    }
}

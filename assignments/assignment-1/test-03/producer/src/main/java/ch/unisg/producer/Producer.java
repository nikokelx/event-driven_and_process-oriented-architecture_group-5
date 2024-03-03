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

        try {
            for (int i = 0; i < 100000; i++) {
                kafkaProducer.send(new ProducerRecord<String, String>(
                        "project_events", // topic
                        "project_id_" + i, //key
                        "some_value_" + System.nanoTime()) //value
                );

                if (i % 100 == 0) {
                    String event = global_events[(int) (Math.random() * global_events.length)] + "_" + System.nanoTime();

                    kafkaProducer.send(new ProducerRecord<String, String>(
                            "global_events", // topic
                            "project_id_" + i, // key
                            event) // value
                    );
                    kafkaProducer.flush();
                    System.out.println("Sent message number " + i);
                }
            }
        } catch (Throwable throwable) {
            System.out.println(throwable.getStackTrace());
        } finally {
            kafkaProducer.close();
        }
    }
}

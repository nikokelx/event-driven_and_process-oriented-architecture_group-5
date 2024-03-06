package ch.unisg.producer;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Producer
{
    public static void main( String[] args ) throws IOException
    {
        KafkaProducer<String, String> kafkaProducer;
        try (InputStream props = Resources.getResource("producer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            kafkaProducer = new KafkaProducer<>(properties);
        }

        try {
            for (int i = 0; i < 10000000; i++) {
                kafkaProducer.send(new ProducerRecord<String, String>(
                        "project_events", // topic
                        "project_id_" + i, //key
                        "some_value_" + System.nanoTime()) //value
                );

                System.out.println(i);
            }
        } catch (Throwable throwable) {
            System.out.println(throwable.getStackTrace());
        } finally {
            kafkaProducer.close();
        }
    }
}

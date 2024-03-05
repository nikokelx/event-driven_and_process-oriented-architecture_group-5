package ch.unisg.consumer;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
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
        kafkaConsumer.subscribe(Arrays.asList("project_events"));

        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
                System.out.println("Polled records: " + records.count());

                for (ConsumerRecord<String, String> record : records) {
                    switch (record.topic()) {
                        case "project_events":
                            for (TopicPartition par : records.partitions())
                            {
                                // Get the end offset for the partition
                                long endOffset = kafkaConsumer.endOffsets(Collections.singletonList(par)).get(par);

                                /// Get the committed offset
                                Map<TopicPartition, OffsetAndMetadata> committedOffsets = kafkaConsumer.committed(Collections.singleton(par));
                                OffsetAndMetadata committedMetadata = committedOffsets.get(par);

                                // Check if the committed offset is available
                                long committedOffset = -1;
                                if (committedMetadata != null) {
                                    committedOffset = committedMetadata.offset();

                                    // Calculate the difference
                                    long consumerLag = endOffset - committedOffset;
                                    System.out.println("Partition " + par.partition() +
                                            ": Last Offset Stored by Broker = " + endOffset +
                                            ", Last Committed Offset = " + committedOffset +
                                            ", Current Lag = " + consumerLag
                                    );
                                }
                            }

                            System.out.println("----------------------");
                            System.out.println("Received project_events - Offset = " + record.offset() +  " key: " + record.key() + " partition: " + record.partition());
                            System.out.println("-----------------------------------------------------------------------------");
                            break;
                        default:
                            throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
                    }
                }

                System.out.println("################# Polled records processed ##################");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            kafkaConsumer.close();
        }
    }
}
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
    private static long lastOffset = -1;
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
                long currentOffset = -1;
                System.out.println("Records: " + records.count());

                TopicPartition partition = new TopicPartition("project_events", 0);

                Map<TopicPartition, Long> endOffsets = kafkaConsumer.endOffsets(Arrays.asList(partition));
                Long latestOffset = endOffsets.get(partition);

                for (TopicPartition par : records.partitions()) {
                    // Get the end offset for the partition
                    long endOffset = kafkaConsumer.endOffsets(Collections.singletonList(par)).get(par);

                    /// Get the committed offset
                    Map<TopicPartition, OffsetAndMetadata> committedOffsets = kafkaConsumer.committed(Collections.singleton(par));
                    OffsetAndMetadata committedMetadata = committedOffsets.get(par);

                    // Check if the committed offset is available
                    long committedOffset = -1;
                    if (committedMetadata != null) {
                        committedOffset = committedMetadata.offset();
                        // Print the committed offset
                        System.out.println("Offset committed by the consumer group: " + committedOffset);
                    } else {
                        System.out.println("No offset committed by the consumer group for partition: " + par);
                    }

                    // Calculate the difference
                    long difference = endOffset - committedOffset;

                    System.out.println("Partition " + partition.partition() +
                            ": Last Offset Stored by Broker = " + endOffset +
                            ", Last Committed Offset = " + committedOffset +
                            ", Difference = " + difference);
                }

                for (ConsumerRecord<String, String> record : records) {
                    currentOffset = record.offset();

                    // Introduce lag by adding a delay
                    // Thread.sleep(1500);

                    switch (record.topic()) {
                        case "project_events":
                            System.out.println("Received project_events message - Offset = " + record.offset() +  " key: " + record.key() + " value: " + record.value());
                            long lag = latestOffset - currentOffset;
                            System.out.println("-------------------------------------------------------------");
                            System.out.println("Current Lag: " + lag);
                            System.out.println("-------------------------------------------------------------");
                            break;
                        default:
                            throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            kafkaConsumer.close();
        }
    }
}

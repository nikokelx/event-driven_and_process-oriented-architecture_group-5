package ch.unisg.consumer;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;

public class Consumer
{
    // 6. enable.auto.commit = false with custom committing every 10 records or faster if polled records < 5:
    // private static Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
    public static void main( String[] args ) throws IOException
    {
        KafkaConsumer<String, String> kafkaConsumer;
        try (InputStream props = Resources.getResource("consumer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            kafkaConsumer = new KafkaConsumer<>(properties);
        }

        kafkaConsumer.subscribe(Arrays.asList("global_events", "project_events"));

        int count = 0;

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            System.out.println("Polled records: " + records.count());

            for (ConsumerRecord<String, String> record : records) {
                count += 1;
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

                        System.out.println("----------");
                        System.out.println("Received project_events - Offset = " + record.offset() +  " key: " + record.key() + " partition: " + record.partition());
                        System.out.println("---------------------------------------------------------------");

                        // 6. enable.auto.commit = false with custom committing every 10 records or faster if polled records < 5:
                        /*currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()+1, "no metadata"));
                        if ((count >= 10 && count % 10 == 0) || records.count() < 5) {
                            kafkaConsumer.commitAsync(currentOffsets, null);
                        }*/

                        break;
                    default:
                        throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
                }
            }

            // 6. enable.auto.commit = false with custom committing after all polled records:
            /*try {
                kafkaConsumer.commitSync();
            } catch (CommitFailedException e) {
                System.out.println("commit failed: " + e);
            }*/
        }
    }
}

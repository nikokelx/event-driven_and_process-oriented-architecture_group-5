package ch.unisg.eventprocessor.util;

import ch.unisg.eventprocessor.model.MachineTemperature;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class TemperatureTimeStampExtractor implements TimestampExtractor {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {

        MachineTemperature measurement = (MachineTemperature) record.value();

        if (measurement != null && measurement.getTimestamp() != null) {

            String timestamp = measurement.getTimestamp();

            try {
                Date parsedDate = dateFormat.parse(timestamp);
                return parsedDate.getTime();

            } catch (ParseException e) {

                throw new RuntimeException(e);
            }
        }
        // fallback to stream time
        return partitionTime;
    }
}

package ch.unisg.eventprocessor.controllers.rest;

import ch.unisg.eventprocessor.model.Machine;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyQueryMetadata;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IQController {

    private final KafkaStreams kafkaStreams;
    private final HostInfo hostInfo;

    @Autowired
    public IQController(KafkaStreams kafkaStreams, HostInfo hostInfo) {
        this.hostInfo = hostInfo;
        this.kafkaStreams = kafkaStreams;
    }

    @GetMapping("/machines")
    public Map<String, Machine> getAllMachines() {
        Map<String, Machine> machines = new HashMap<>();
        ReadOnlyKeyValueStore<String, Machine> store = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType("machine-store", QueryableStoreTypes.keyValueStore()));

        store.all().forEachRemaining(record -> machines.put(record.key, record.value));

        return machines;
    }

    @GetMapping("/machine/{key}")
    public Machine getMachineByKey(@PathVariable String key) {
        KeyQueryMetadata metadata = kafkaStreams.queryMetadataForKey("machine-store", key, Serdes.String().serializer());


            ReadOnlyKeyValueStore<String, Machine> store = kafkaStreams.store(
                    StoreQueryParameters.fromNameAndType("machine-store", QueryableStoreTypes.keyValueStore()));
            return store.get(key);



    }
}

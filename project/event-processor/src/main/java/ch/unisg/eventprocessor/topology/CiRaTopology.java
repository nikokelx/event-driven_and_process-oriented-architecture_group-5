package ch.unisg.eventprocessor.topology;

import ch.unisg.eventprocessor.model.Machine;
import ch.unisg.eventprocessor.model.MachineFillLevel;
import ch.unisg.eventprocessor.model.MachineProduction;
import ch.unisg.eventprocessor.model.MachineTemperature;
import ch.unisg.eventprocessor.serialization.JsonSerdes;
import ch.unisg.eventprocessor.util.ProductionTimeStampExtractor;
import ch.unisg.eventprocessor.util.TemperatureTimeStampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Map;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.state.KeyValueStore;

public class CiRaTopology {

    public static Topology build() {

        StreamsBuilder builder = new StreamsBuilder();

        /** KStream MachineFillLevel */

        Consumed<String, MachineFillLevel> machineFillLevelOptions =
                Consumed.with(Serdes.String(), JsonSerdes.MachineFillLevel());

        KStream<String, MachineFillLevel> streamMachineFillLevel = builder.stream("stream-machine-fill-level", machineFillLevelOptions);

        // Router
        Map<String, KStream<String, MachineFillLevel>> result =
               streamMachineFillLevel.split()
                        .branch((key, value) -> value.getMachineFillLevel() <= 10.0, Branched.as("low-machine-fill-level"))
                        .branch((key, value) -> value.getMachineFillLevel() <= 79.0, Branched.as("medium-machine-fill-level"))
                        .branch((key, value) -> value.getMachineFillLevel() >= 80.0, Branched.as("high-machine-fill-level"))
                        .defaultBranch();

        KStream<String, MachineFillLevel> lowMachineFillLevel = result.get("low-machine-fill-level");
        KStream<String, MachineFillLevel> mediumMachineFillLevel = result.get("medium-machine-fill-level");
        KStream<String, MachineFillLevel> highMachineFillLevel = result.get("high-machine-fill-level");



        /** KStream MachineProduction */

        Consumed<String, MachineProduction> machineProductionOptions =
                Consumed.with(Serdes.String(), JsonSerdes.MachineProduction())
                        .withTimestampExtractor(new ProductionTimeStampExtractor());


        KStream<String, MachineProduction> streamMachineProduction = builder.stream("stream-machine-production", machineProductionOptions);

        TimeWindows tumblingWindow =
                TimeWindows.ofSizeAndGrace(Duration.ofSeconds(60), Duration.ofSeconds(5));

        KTable<Windowed<String>, Long> machineProductionCounts =
                streamMachineProduction
                        .groupByKey()
                        .windowedBy(tumblingWindow)
                        .count(Materialized.as("machine-production-counts"))
                        .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded().shutDownWhenFull()));

        KStream<String, Long> machineProductionCountStream =
                machineProductionCounts
                        .toStream()
                        .map(
                                (windowedKey, value) -> {
                                    return KeyValue.pair(windowedKey.key(), value);
                                });

        machineProductionCountStream.foreach((key, value) -> System.out.println("Machine Production Count: " + key + " " + value));

        /** KStream MachineTemperature */

        Consumed<String, MachineTemperature> machineTemperatureOptions =
                Consumed.with(Serdes.String(), JsonSerdes.MachineTemperature())
                        .withTimestampExtractor(new TemperatureTimeStampExtractor());

        KStream<String, MachineTemperature> streamMachineTemperature = builder.stream("stream-machine-temperature", machineTemperatureOptions);

        // filter
        KStream<String, MachineTemperature> filteredStreamMachineTemperature = streamMachineTemperature.filter(
                (key, value) -> value.getMachineTemperature() >= 5
        );

        /** Join MachineTemperature & MachineProduction */

        StreamJoined<String, MachineProduction, MachineTemperature> joinParams =
                StreamJoined.with(Serdes.String(), JsonSerdes.MachineProduction(), JsonSerdes.MachineTemperature());

        JoinWindows joinWindows =
                JoinWindows.ofTimeDifferenceAndGrace(Duration.ofSeconds(60), Duration.ofSeconds(10));

        ValueJoiner<MachineProduction, MachineTemperature, Machine> valueJoiner =
                (machineProduction, machineTemperature) -> new Machine(machineProduction, machineTemperature);

        KStream<String, Machine> machineJoined =
                streamMachineProduction.join(filteredStreamMachineTemperature, valueJoiner, joinWindows, joinParams);

        machineJoined.foreach((key, value) -> System.out.println("################ Machine Production Count: " + key + " " + value));

        // Materialize the joined stream
        machineJoined.toTable(
                Materialized.<String, Machine, KeyValueStore<Bytes, byte[]>>as("machine-store")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.Machine())
        );

        machineJoined.to("machine-stream", Produced.with(Serdes.String(), JsonSerdes.Machine()));

        return builder.build();
    }
}

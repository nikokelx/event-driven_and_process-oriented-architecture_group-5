package ch.unisg.eventprocessor.serialization;

import ch.unisg.eventprocessor.model.Machine;
import ch.unisg.eventprocessor.model.MachineFillLevel;
import ch.unisg.eventprocessor.model.MachineProduction;
import ch.unisg.eventprocessor.model.MachineTemperature;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class JsonSerdes {

    public static Serde<Machine> Machine() {
        JsonSerializer<Machine> serializer = new JsonSerializer<>();
        JsonDeserializer<Machine> deserializer = new JsonDeserializer<>(Machine.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<MachineFillLevel> MachineFillLevel() {
        JsonSerializer<MachineFillLevel> serializer = new JsonSerializer<>();
        JsonDeserializer<MachineFillLevel> deserializer = new JsonDeserializer<>(MachineFillLevel.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<MachineProduction> MachineProduction() {
       JsonSerializer<MachineProduction> serializer = new JsonSerializer<>();
       JsonDeserializer<MachineProduction> deserializer = new JsonDeserializer<>(MachineProduction.class);
       return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<MachineTemperature> MachineTemperature() {
        JsonSerializer<MachineTemperature> serializer = new JsonSerializer<>();
        JsonDeserializer<MachineTemperature> deserializer = new JsonDeserializer<>(MachineTemperature.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
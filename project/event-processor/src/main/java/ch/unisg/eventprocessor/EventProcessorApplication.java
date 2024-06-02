package ch.unisg.eventprocessor;

import ch.unisg.eventprocessor.topology.CiRaTopology;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.HostInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Properties;

@SpringBootApplication
public class EventProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventProcessorApplication.class, args);
	}

	@Bean
	public KafkaStreams kafkaStreams() {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "cira-streams");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		Topology topology = CiRaTopology.build();

		KafkaStreams kafkaStreams = new KafkaStreams(topology, props);

		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

		System.out.println("Ready to stream!");
		kafkaStreams.start();

		return kafkaStreams;
	}

	@Bean
	public HostInfo hostInfo() {
		return new HostInfo("localhost", 4231);
	}
}

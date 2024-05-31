package ch.unisg.factory.infrastructure.adapters.http;


import ch.unisg.factory.core.ports.out.CollectMachineFillLevelPort;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class CollectMachineFillLevelWebAdapter implements CollectMachineFillLevelPort {

    String server = "http://machine-01:4001";

    @Override
    public double collectMachineFillLevel() {

        // collect machine fill level
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/machine/fill-level/collect"))
                .GET()
                .build();

        try {
             HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

             // return the machine fill level back to the service
             return Double.valueOf(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

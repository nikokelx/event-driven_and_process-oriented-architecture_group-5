package ch.unisg.factory.infrastructure.adapters.http;

import ch.unisg.factory.core.ports.out.ToggleMachineStatusPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class ToggleMachineStatusWebAdapter implements ToggleMachineStatusPort {

    String server = "http://machine-01:4000";

    @Override
    public String toggleMachineStatus() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/machine/status/toggle"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return String.valueOf(response.statusCode());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}

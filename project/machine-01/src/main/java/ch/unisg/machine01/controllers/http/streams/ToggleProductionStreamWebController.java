package ch.unisg.machine01.controllers.http.streams;

import ch.unisg.machine01.controllers.dto.ToggleProductionJsonRepresentation;
import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.CollectMachineFillLevelUseCase;
import ch.unisg.machine01.core.ports.in.streams.ToggleMachineProductionStreamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToggleProductionStreamWebController {

    // retrieve the use case
    private final ToggleMachineProductionStreamUseCase toggleMachineProductionStreamUseCase;

    @PostMapping(path = "/machine/stream/production/toggle", consumes = ToggleProductionJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> toggleProduction() {

        // toggle the production
        int result = toggleMachineProductionStreamUseCase.toggleMachineProductionStream();

        // return a response entity to the client
        return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
    }
}

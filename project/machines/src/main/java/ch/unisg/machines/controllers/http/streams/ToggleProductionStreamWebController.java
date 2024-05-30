package ch.unisg.machines.controllers.http.streams;

import ch.unisg.machines.controllers.dto.ToggleProductionJsonRepresentation;
import ch.unisg.machines.core.ports.in.streams.ToggleMachineProductionStreamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToggleProductionStreamWebController {

    // retrieve the use case
    private final ToggleMachineProductionStreamUseCase toggleMachineProductionStreamUseCase;

    // post request starting the production line (stream part)
    @PostMapping(path = "/machine/stream/production/toggle", consumes = ToggleProductionJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> toggleProduction() {

        // toggle the production
        int result = toggleMachineProductionStreamUseCase.toggleMachineProductionStream();

        // return a response entity to the client
        return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
    }
}

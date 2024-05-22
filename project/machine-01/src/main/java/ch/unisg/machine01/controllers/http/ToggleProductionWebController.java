package ch.unisg.machine01.controllers.http;

import ch.unisg.machine01.controllers.dto.ToggleProductionJsonRepresentation;
import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.ToggleProductionCommand;
import ch.unisg.machine01.core.ports.in.ToggleProductionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToggleProductionWebController {

    // retrieve uses cases to toggle the production
    private final ToggleProductionUseCase toggleProductionUseCase;

    // retrieve the machine
    private Machine machine = Machine.getMachine();

    @PostMapping(path = "/machine/production/toggle", consumes = ToggleProductionJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> toggleProduction() {

        // if the machine is active the production starts
        if (machine.getMachineStatus()) {

            // create a new command
            ToggleProductionCommand command = new ToggleProductionCommand();

            // toggle the production
            int response = toggleProductionUseCase.toggleProduction(command);

            // send a accepted response back to the client
            return new ResponseEntity<>(String.valueOf(response), HttpStatus.ACCEPTED);

        // if the machine is inactive send a conflict back to the client
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

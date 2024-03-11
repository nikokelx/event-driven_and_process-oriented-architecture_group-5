package ch.unisg.machine01.controllers.http;

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

    private final ToggleProductionUseCase toggleProductionUseCase;

    @PostMapping(path = "/machine/production/toggle")
    public ResponseEntity<String> toggleProduction() {

        Machine machine = Machine.getMachine();

        if (machine.getMachineStatus()) {
            ToggleProductionCommand command = new ToggleProductionCommand();

            int response = toggleProductionUseCase.toggleProduction(command);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

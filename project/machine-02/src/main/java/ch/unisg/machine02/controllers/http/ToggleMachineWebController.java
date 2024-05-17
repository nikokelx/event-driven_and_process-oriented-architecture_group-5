package ch.unisg.machine02.controllers.http;

import ch.unisg.machine02.controllers.dto.ToggleMachineJsonRepresentation;
import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.ports.in.ToggleMachineCommand;
import ch.unisg.machine02.core.ports.in.ToggleMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToggleMachineWebController {

    private final ToggleMachineUseCase toggleMachineUseCase;

    @PostMapping(path = "/machine/status/toggle", consumes = ToggleMachineJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<Void> toggleMachine(@RequestBody ToggleMachineJsonRepresentation payload) {

        Machine machine = Machine.getMachine();
        machine.setMachineProductionSpeed(new Machine.MachineProductionSpeed(payload.getMachineProductionSpeed()));

        ToggleMachineCommand command = new ToggleMachineCommand();

        toggleMachineUseCase.toggleMachine(command);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

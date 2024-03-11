package ch.unisg.machine01.controllers.http;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.ToggleMachineCommand;
import ch.unisg.machine01.core.ports.in.ToggleMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToggleMachineWebController {

    private final ToggleMachineUseCase toggleMachineUseCase;

    @PostMapping(path = "/machine/status/toggle")
    public ResponseEntity<String> toggleMachine() {
        ToggleMachineCommand command = new ToggleMachineCommand();

        Machine.MachineStatus machineStatus = toggleMachineUseCase.toggleMachine(command);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

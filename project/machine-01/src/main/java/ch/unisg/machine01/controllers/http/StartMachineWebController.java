package ch.unisg.machine01.controllers.http;

import ch.unisg.machine01.core.entities.Machine;
import ch.unisg.machine01.core.ports.in.StartMachineCommand;
import ch.unisg.machine01.core.ports.in.StartMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartMachineWebController {

    private final StartMachineUseCase startMachineUseCase;

    @PostMapping(path = "/machine/start/")
    public ResponseEntity<String> startMachine() {
        StartMachineCommand command = new StartMachineCommand();

        Machine.MachineStatus machineStatus = startMachineUseCase.startMachine(command);

        System.out.println(machineStatus.getValue());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

package ch.unisg.machines.controllers.http;

import ch.unisg.machines.controllers.dto.ToggleMachineJsonRepresentation;
import ch.unisg.machines.core.entities.Machine;
import ch.unisg.machines.core.ports.in.ToggleMachineCommand;
import ch.unisg.machines.core.ports.in.ToggleMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToggleMachineWebController {

    @Value("${MACHINE}")
    String machineName;

    private final ToggleMachineUseCase toggleMachineUseCase;

    @PostMapping(path = "/machine/status/toggle", consumes = ToggleMachineJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<Void> toggleMachine(@RequestBody ToggleMachineJsonRepresentation payload) {

        // get the machine (singleton)
        Machine machine = Machine.getMachine();

        // configure the production speed per seconds of the machine
        Machine.MachineProductionSpeed machineProductionSpeed = new Machine.MachineProductionSpeed(payload.getMachineProductionSpeed());

        // create a command
        ToggleMachineCommand command = new ToggleMachineCommand(new Machine.MachineProductionSpeed(machineProductionSpeed.getValue()));

        // execute the service to toggle the machine
        Machine.MachineStatus machineStatus = toggleMachineUseCase.toggleMachine(command);

        // http response
        return new ResponseEntity(String.valueOf(machineStatus), HttpStatus.ACCEPTED);
    }
}

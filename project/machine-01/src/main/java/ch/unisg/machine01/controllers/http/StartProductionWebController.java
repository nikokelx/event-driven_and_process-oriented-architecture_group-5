package ch.unisg.machine01.controllers.http;

import ch.unisg.machine01.core.ports.in.StartProductionCommand;
import ch.unisg.machine01.core.ports.in.StartProductionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartProductionWebController {

    private final StartProductionUseCase startProductionUseCase;

    @PostMapping(path = "/machine/production/start/")
    public ResponseEntity<String> startProduction() {

        StartProductionCommand command = new StartProductionCommand();

        int startProduction = startProductionUseCase.startProduction(command);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

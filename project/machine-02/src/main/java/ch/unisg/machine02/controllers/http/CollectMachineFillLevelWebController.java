package ch.unisg.machine02.controllers.http;

import ch.unisg.machine02.core.entities.Machine;
import ch.unisg.machine02.core.ports.in.CollectMachineFillLevelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CollectMachineFillLevelWebController {

    private final CollectMachineFillLevelUseCase collectMachineFillLevelUseCase;

    @GetMapping(path = "/machine/fill-level/collect")
    public ResponseEntity<String> collectMachineFillLevel() {

        Machine.MachineFillLevel machineFillLevel = collectMachineFillLevelUseCase.collectMachineFillLevel();

        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<>(String.valueOf(machineFillLevel.getValue()), responseHeaders, HttpStatus.ACCEPTED);
    }
}

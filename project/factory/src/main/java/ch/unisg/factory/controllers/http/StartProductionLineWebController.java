package ch.unisg.factory.controllers.http;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartProductionLineWebController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(path = "/factory/start-production-line")
    public ResponseEntity<String> startProductionLine() {

        runtimeService.startProcessInstanceByKey("StartProductionLineProcess");

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

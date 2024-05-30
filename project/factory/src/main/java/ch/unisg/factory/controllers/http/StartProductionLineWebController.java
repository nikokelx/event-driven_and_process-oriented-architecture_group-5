package ch.unisg.factory.controllers.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.ZeebeClient;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class
StartProductionLineWebController {

    /*

        TEST CONTROLLER

     */

    @Autowired
    private ZeebeClient client;

    @PostMapping(path = "/factory/start-production-line")
    public ResponseEntity<String> startProductionLine() {

        client.newCreateInstanceCommand()
                .bpmnProcessId("start-production-line-test")
                .latestVersion()
                .variables("{ \"machineStatus\" : \"machine01\"}")
                .send();

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

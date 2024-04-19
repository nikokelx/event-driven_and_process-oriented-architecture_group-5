package ch.unisg.machine01.controllers.dto;

import lombok.Getter;

public class ToggleMachineJsonRepresentation {

    public static final String MEDIA_TYPE = "application/json";

    @Getter
    private int machineProductionSpeed;

}

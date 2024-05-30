package ch.unisg.machines.controllers.dto;

import lombok.Getter;

public class ToggleMachineJsonRepresentation {

    public static final String MEDIA_TYPE = "application/machine+json";

    @Getter
    private int machineProductionSpeed;

}

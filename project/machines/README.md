# Microservice Machines

## Introduction

The microservice Machines represents fictitious machines that produce wood shavings.
It is part of the Factory.

## Endpoints

| Title              | File      | Path                                            | Media Type | Body |
|--------------------|-----------|-------------------------------------------------|------------|------|
| Start Machine      | [toggleMachine](src/main/java/ch/unisg/machines/controllers/http/ToggleMachineWebController.java)           | http://127.0.0.1:4000/machine/status/toggle     | MEDIA_TYPE = application/machine+json | Body = {"machineProductionSpeed": X } | 
| Start Production   | [toggleProduction](src/main/java/ch/unisg/machines/controllers/http/ToggleProductionWebController.java)          | http://localhost:4000/machine/production/toggle | MEDIA_TYPE = application/machine+json | Body = {} |
| Start Production (Stream) | [toggleProductionStream](src/main/java/ch/unisg/machines/controllers/http/streams/ToggleProductionStreamWebController.java) | MEDIA_TYPE = application/machine+json | Body = {} |

## Kafka Topics
* machine-status
* machine-fill-level
* machine-configurations
* stream-machine-fill-level
* stream-machine-production
* stream-machine-temperature

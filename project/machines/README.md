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
Any other controller is used by camunda 8 or data 

### Scripts
#### Start Machine:
```
curl -d "{\"machineProductionSpeed\": \"2\"}" -H "Content-Type: application/machine-json" http://127.0.0.1:4000/machine/status/toggle
```
#### Start Production:
```
curl -H "Content-Type: application/machine-json" -X POST http://127.0.0.1:4000/machine/production/toggle
```

OR

#### Start Production Stream
```
curl -H "Content-Type: application/machine-json" -X POST http://127.0.0.1:4000/machine/stream/production/toggle
```

## Implemented concepts:

### Business Process Model and Notation

### Kafka Streams

## Kafka Topics
* machine-status
* machine-fill-level
* machine-configurations
* stream-machine-fill-level
* stream-machine-production
* stream-machine-temperature

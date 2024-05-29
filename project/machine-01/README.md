# Microservice Machine-01

## Introduction

The microservice Machine-01 is a fictive machine that produces wood shavings. 
It is part of the Factory.

## Endpoints

| Title              | File      | Path                                            | Media Type | Body |
|--------------------|-----------|-------------------------------------------------|------------|------|
| Start Machine      | [toggleMachine](src/main/java/ch/unisg/machine01/controllers/http/ToggleMachineWebController.java)           | http://127.0.0.1:4000/machine/status/toggle     | MEDIA_TYPE = application/machine+json | Body = {"machineProductionSpeed": X } | 
| Start Production   | [toggleProduction](src/main/java/ch/unisg/machine01/controllers/http/ToggleProductionWebController.java)          | http://localhost:4000/machine/production/toggle | MEDIA_TYPE = application/machine+json | Body = {} |

### Scripts
#### Start Machine:
```
curl -d "{\"machineProductionSpeed\": \"2\"}" -H "Content-Type: application/machine-json" http://127.0.0.1:4000/machine/status/toggle
```
#### Start Production:
```
curl -H "Content-Type: application/machine-json" -X POST http://127.0.0.1:4000/machine/production/toggle
```

## Implemented concepts:

## Kafka

### Topics

* machine-status
* machine-fill-level
* machine-configurations

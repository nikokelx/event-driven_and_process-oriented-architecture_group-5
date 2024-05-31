# Microservice Factory

## Introduction

The Factory microservice coordinates various tasks including initiating the production line,
monitoring machine fill levels, publishing real-time inventory data, and orchestrating complex
process, of transporting the goods, using Camunda BPMN workflows. Notably, it employs Camunda
to automate the start of production lines and to schedule transfer commands based on factory
inventory levels, ensuring optimal resource utilization and production efficiency. Furthermore, 
it listens to the new implemented kafka stream.

## Endpoints

The microservice factory only consumes events.

## Kafka Topics
* machine-status
* machine-fill-level
* machine-configurations
* stream-machine-fill-level
* stream-machine-production
* stream-machine-temperature

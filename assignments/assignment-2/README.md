# Assignment 2: Kafka with Spring
## Michal Cislo & Raffael Rot

### Table of Content

1. Present project idea
2. Show implementation of event notification
3. Show implementation of event-carried state transfer
4. Add an error scenario

#### 1. Present project idea

The project encapsulates the supply chain of the company CiRa. 
It has various machines that work with wood. After that, the goods are processed in the factory. 
Furthermore, there is a warehouse that distributes the items currently. 
The application will be extended in the future. My is name Peter.

#### 2. Event notification

A POST request can start machines in CiRa. 
The microservice [Machine-01](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/machine-01) 
service [emits an event](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/machine-01/src/main/java/ch/unisg/machine01/infrastructure/adapters/messages/MachineStatusMessage.java) 
to the "machine-status" topic indicating its availability.
The microservice [Factory](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/factory) 
[pays attention](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/factory/src/main/java/ch/unisg/factory/controllers/event/MachineStatusEventListener.java)
to this topic and knows that the machine-01 is available.

!Sequence Diagram

Instructions: 

First, start the docker infrastructure.
```
docker-compose up --build
```

#### 3. Event-carried state transfer

The microservice Factory [saves the current inventory level](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/factory/src/main/java/ch/unisg/factory/infrastructure/repository/MachinePersistenceAdapter.java)
of the goods. 
If a certain amount of wood shavings is in stock, the microservice [emits an event](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/factory/src/main/java/ch/unisg/factory/infrastructure/adapters/messages/FactoryInventoryLevelEvent.java)
to the topic "stock-update." 
The microservice Warehouse [listens](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/warehouse/src/main/java/ch/unisg/warehouse/service/ConsumerService.java)
and keeps track of the data.

!Sequence Diagram

Instructions:

```

```

#### 4. Error scenario

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
The application will be extended in the future. 

For assignment 1, we implemented ...
* Microservice [Factory](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/factory)
* Microservice [Machine-01](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/machine-01) 
* Microservice [Warehouse](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/warehouse)

Instruction to start the project:
```
docker-compose up --build
```

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

1. Send a POST request to the [endpoint "/machine/status/toggle"](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/machine-01/src/main/java/ch/unisg/machine01/controllers/http/ToggleMachineWebController.java) to start the machine. 

```
curl    -H '' \
        -d '' \
        -X POST \
        http://127.0.0.1:4000/machine/status/toggle
```
2. The microservice Factory [receives the event](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/factory/src/main/java/ch/unisg/factory/controllers/event/MachineStatusEventListener.java), and it logs in the terminal that the Machine-01 is available. 

Conclusion:

The Event Notification pattern is implemented. For the moment, the Factory just prints the message.

#### 3. Event-carried state transfer

The microservice Factory [saves the current inventory level](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/factory/src/main/java/ch/unisg/factory/infrastructure/repository/MachinePersistenceAdapter.java)
of the goods. 
If a certain amount of wood shavings is in the inventory, the microservice [emits an event](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/factory/src/main/java/ch/unisg/factory/infrastructure/adapters/messages/FactoryInventoryLevelEvent.java)
to the topic "factory-inventory-level". 
The microservice Warehouse [listens](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/warehouse/src/main/java/ch/unisg/warehouse/service/ConsumerService.java)
listen to this topic, and it keeps track of the data.

!Sequence Diagram

Instructions:

1. Send a POST request to the [endpoint "/machine/production/toggle"](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/machine-01/src/main/java/ch/unisg/machine01/controllers/http/ToggleProductionWebController.java) to start the production of wood shavings. 

```
curl    -H '' \
        -d '' \
        -X POST \
        http://localhost:4000/machine/production/toggle
```
2. Consume the fill level of machine 01
```

```
3. Consume the inventory level of factory
```

```
4. Query the inventory level in the microservice factory
```

```

5. Query the inventory level in the microservice warehouse
```

``

Conclusion:

The Event-carried state transfer is implemented. The microservices factory and warehouse communicates over Kafka, saving the inventory level of the factory in their own and separate databases. 

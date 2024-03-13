![event_notification-sequence_diagram](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/c87775f9-cfd6-4e39-8a1a-884a481bf016)# Assignment 2: Kafka with Spring
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

For assignment 2, we implemented ...
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

![U<?xml version="1.0" encoding="utf-8" standalone="no"?><!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 20010904//EN" "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"><svg xmlns="http://www.w3.org/2000/svg" width="749" height="371" xmlns:xlink="http://www.w3.org/1999/xlink"><source><![CDATA[Title: Event Notification
Client->Machine 01: Turn on the Machine
Note over Kafka: topic='machine-status'
Machine 01-->Kafka: Produce: Machine status \nevent
Kafka-->Factory: Consume: Machine status \nevent]]></source><desc>Event Notification</desc><defs><marker viewBox="0 0 5 5" markerWidth="5" markerHeight="5" orient="auto" refX="5" refY="2.5" id="markerArrowBlock"><path d="M 0 0 L 5 2.5 L 0 5 z"></path></marker><marker viewBox="0 0 9.6 16" markerWidth="4" markerHeight="16" orient="auto" refX="9.6" refY="8" id="markerArrowOpen"><path d="M 9.6,8 1.92,16 0,13.7 5.76,8 0,2.286 1.92,0 9.6,8 z"></path></marker></defs><g class="title"><rect x="10" y="10" width="168.34375" height="29" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="15" y="30" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="15">Event Notification</tspan></text></g><g class="actor"><rect x="10" y="49" width="72.78125" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="20" y="74" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="20">Client</tspan></text></g><g class="actor"><rect x="10" y="312.4000015258789" width="72.78125" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="20" y="337.4000015258789" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="20">Client</tspan></text></g><line x1="46.390625" x2="46.390625" y1="88" y2="312.4000015258789" stroke="#000000" fill="none" style="stroke-width: 2;"></line><g class="actor"><rect x="179.6484375" y="49" width="108.171875" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="189.6484375" y="74" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="189.6484375">Machine 01</tspan></text></g><g class="actor"><rect x="179.6484375" y="312.4000015258789" width="108.171875" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="189.6484375" y="337.4000015258789" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="189.6484375">Machine 01</tspan></text></g><line x1="233.734375" x2="233.734375" y1="88" y2="312.4000015258789" stroke="#000000" fill="none" style="stroke-width: 2;"></line><g class="actor"><rect x="424.0703125" y="49" width="63.984375" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="434.0703125" y="74" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="434.0703125">Kafka</tspan></text></g><g class="actor"><rect x="424.0703125" y="312.4000015258789" width="63.984375" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="434.0703125" y="337.4000015258789" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="434.0703125">Kafka</tspan></text></g><line x1="456.0625" x2="456.0625" y1="88" y2="312.4000015258789" stroke="#000000" fill="none" style="stroke-width: 2;"></line><g class="actor"><rect x="637.5" y="49" width="81.78125" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="647.5" y="74" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="647.5">Factory</tspan></text></g><g class="actor"><rect x="637.5" y="312.4000015258789" width="81.78125" height="39" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="647.5" y="337.4000015258789" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="647.5">Factory</tspan></text></g><line x1="678.390625" x2="678.390625" y1="88" y2="312.4000015258789" stroke="#000000" fill="none" style="stroke-width: 2;"></line><g class="signal"><text x="56.390625" y="118.5" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="56.390625">Turn on the Machine</tspan></text><line x1="46.390625" x2="233.734375" y1="127" y2="127" stroke="#000000" fill="none" style="stroke-width: 2; marker-end: url(&quot;#markerArrowBlock&quot;);"></line></g><g class="note"><rect x="354.296875" y="147" width="203.53125" height="29" stroke="#000000" fill="#ffffff" style="stroke-width: 2;"></rect><text x="359.296875" y="167" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="359.296875">topic='machine-status'</tspan></text></g><g class="signal"><text x="243.734375" y="196.89999961853027" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="243.734375">Produce: Machine status</tspan><tspan dy="1.2em" x="243.734375">event</tspan></text><line x1="233.734375" x2="456.0625" y1="234.20000076293945" y2="234.20000076293945" stroke="#000000" fill="none" style="stroke-width: 2; stroke-dasharray: 6, 2; marker-end: url(&quot;#markerArrowBlock&quot;);"></line></g><g class="signal"><text x="466.0625" y="255.10000038146973" style="font-size: 16px; font-family: &quot;Andale Mono&quot;, monospace;"><tspan x="466.0625">Consume: Machine status</tspan><tspan dy="1.2em" x="466.0625">event</tspan></text><line x1="456.0625" x2="678.390625" y1="292.4000015258789" y2="292.4000015258789" stroke="#000000" fill="none" style="stroke-width: 2; stroke-dasharray: 6, 2; marker-end: url(&quot;#markerArrowBlock&quot;);"></line></g></svg>ploading event_notification-sequence_diagram.svg…]()


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
to this topic. After receiving new inventory level, the warehouse saves the new information to its database.

!Sequence Diagram

Instructions:

1. Send a POST request to the [endpoint "/machine/production/toggle"](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/blob/main/project/machine-01/src/main/java/ch/unisg/machine01/controllers/http/ToggleProductionWebController.java) to start the production of wood shavings. 

```
curl    -H '' \
        -d '' \
        -X POST \
        http://localhost:4000/machine/production/toggle
```
2. Log into the kafka container.

```
docker exec -it kafka bash
```

3. Consume the fill level of machine 01.

```
cd /opt/bitnami/kafka/bin/  &&
./kafka-console-consumer.sh --topic machine-fill-level --bootstrap-server kafka:9092
```

4. Consume the inventory level of factory.
 
```
cd /opt/bitnami/kafka/bin/  &&
./kafka-console-consumer.sh --topic factory-inventory-level --bootstrap-server kafka:9092
```

5. Open a new terminal and log in to the db_factory container.

```
docker exec -it db_factory bash
```

6. Log in to the factory database

```
psql -U postgres -d factory_db
```

7. Open a new terminal and log in to the db_warehouse container.

```
docker exec -it db_warehouse
```

8. Log in to the warehouse databse

```
psql -U postgres -d warehouse_db
```

9. Query the inventory level in the microservice factory

```
SELECT * FROM factory;
```

10. Query the inventory level in the microservice warehouse

```
SELECT * FROM factory;
```

Conclusion:

The Event-carried state transfer is implemented. The microservices factory and warehouse communicates over Kafka, saving the inventory level of the factory in their own and separate databases. 

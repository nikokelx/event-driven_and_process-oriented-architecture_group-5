# Event-driven and process-oriented Architecture - Group 5

## Table of Content
* General Description
* Changes to process-oriented architecture (part 01)
* Applied concepts
* Tutorial to run the application CiRa
* Architectural Decision Records
* Assignments
* Microservices

## General Description

The project encapsulates the supply chain of the company CiRa. Built with Spring Boot, Kafka, and
Camunda, our system comprises four key parts: Machine, Factory, Warehouse, and Logistics
microservices, each playing a vital role in making manufacturing smarter and more efficient.

## Changes to process-oriented architecture (part-01)

* Added description to README files
* Improved Camunda 8 BPMN processes (labels)
* We can create new machines in the docker compose file of the same microservice.
* Improved sequence diagrams
* More detailed description of the trade offs between Camunda 7 vs Camunda 8 
* More detailed reflection about our challenges, and implementing experience

## Applied concepts

### Event Notification

Event Notification is a critical feature within the system architecture, facilitating seamless
communication and coordination between components. In this scenario, when an employee
initiates a machine by issuing a POST request, the machine service promptly emits an event
signalling its availability. This event is broadcasted to the designated 'machine-status' topic.
This event-driven approach enhances system responsiveness and agility, enabling real-time updates
without necessitating continuous polling or manual intervention.

<img width="673" alt="Bildschirmfoto 2024-05-31 um 23 02 51" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/9322ca73-a9a3-4425-b3b6-f0cc41b65ab8">

### Event-carried State Transfer

Event-carried state transfer is a paradigm in distributed systems architecture where changes in state
are communicated through events rather than direct data transfers. In this context, the Factory
microservice is responsible for maintaining the real-time inventory levels of produced goods. Once
a predefined inventory threshold is met, the Factory emits an event to the 'stock-update' topic. The
Warehouse microservice, subscribed to this topic, captures and retains a synchronized copy of the
inventory data.
This approach offers significant advantages, including enhanced decoupling between components
and reduced computational load on the Factory microservice. However, it necessitates the
management of replicated data and introduces eventual consistency considerations, ensuring that
all replicas converge to the same state over time. Thus, while promoting scalability and resilience,
event-carried state transfer requires careful consideration of trade-offs to ensure system reliability
and performance.

Trade-offs:
* Decoupling and reduced load on Factory
* Replicated data and eventual consistency

![Event-carried State Transfer(1)](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/73cb995a-6627-4a6c-b568-f5fed1a59f8e)

### Parallel Saga

The utilization of a parallel saga pattern within our event-driven and process-oriented architecture
is instrumental in ensuring robustness, scalability, and fault tolerance. By employing parallel sagas,
we can concurrently execute multiple interdependent calls to our machines. This pattern facilitates
asynchronous communication between services, enabling them to operate independently while
maintaining consistency across the system.

<img src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/7bf49bef-03ac-4d63-8320-8311679d82ba" width="50%"> 

### Anthology Saga

The integration of the Anthology Saga pattern brings substantial advantages in managing complex
workflows with multiple related processes. By adopting the Anthology Saga, we can orchestrate
cohesive sequences of events across various services, ensuring consistency and reliability
throughout. This pattern allows us to group related sagas under a common overarching saga,
facilitating better organization and coordination of business processes. With the Anthology Saga,
we can handle intricate scenarios with ease, as it provides a structured approach to managing
dependencies and interactions between different saga instances. We use this pattern for our
“Supply Chain” process.

<img src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/a978ae3c-2964-4966-8033-7a5663347101" width="50%"> 

### Stateful Resilience Pattern - Human Intervention

In the Human Intervention pattern the system is designed to gracefully handle exceptional situations
by involving human intervention when necessary. When a critical error occurs that cannot be
resolved automatically or through traditional retry mechanisms, the system leads to human
operators for manual resolution. This pattern ensures that complex or ambiguous errors can be
addressed by human expertise, maintaining the integrity and reliability of the system. In our system
this pattern is used to make a decision about a further transfer in case of a transfer truck accident.

<img width="1142" alt="Bildschirmfoto 2024-05-31 um 23 01 52" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/d37a5ffe-b912-47e7-8d25-e649c312135c">

### Stateful Resilience Pattern - Stateful retry

In the Stateful Retry pattern within event-driven architecture, resilience is achieved by implementing
a stateful mechanism for retrying failed service tasks. Specifically, each service task is retried up to
three times upon encountering a failure. This approach aims to improve the robustness and fault
tolerance of the system by allowing failed tasks to be automatically retried.

<img width="396" alt="Bildschirmfoto 2024-05-31 um 23 00 10" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/d1b257a8-7734-4376-a308-31f96ad49e17">

### Outbox pattern

We implemented the Outbox pattern. Here, the Factory microservice listens for new machine fill levels. In the same transaction, the microservice saves the machine fill level in the database and adds a new entry to the end of the Outbox. Finally, a message relay reads the entry and sends the information further.

<img width="685" alt="Bildschirmfoto 2024-05-31 um 23 00 59" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/c4d0bacb-08e5-4fb0-8b61-86f9a6d8172c">

### Event Processor - Topology

The Machine microservice streams three different variables, each with a generated timestamp. The microservice event processor listens to the KStream "stream-machine-fill-level," "stream-machine-production," and "stream-machine-temperature". Finally, the event processor streams a joinedStream to the topic "machine-stream". The microservice Factory is listen to the kstream.

#### Event Processor - Example of a Kafka Stream Consumer

We implemented a Json Serializer, Deserializer, and a Serdes. Therefore, we can consume specific events. 

<img width="1064" alt="grafik" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/e9ca8a8d-1fbd-41c8-96dc-a7b17e136219">

#### Event Processor - Router (Map)

In the KStream MachineFillLevel, we implemented a router (map) to split events into separate branches. The reason for this is that we can respond differently to various values. A low machine fill level does not have high priority as a high machine fill level. We split the stream into three branches: "low-machine-fill-level", "medium-machine-fill-level", "high-machine-fill-level".

<img width="1064" alt="grafik" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/1073a31a-6287-4436-9532-d528292b3503">

#### Event Processor - Filter

For the KStream MachineTemperature, we implemented a simple filter to ignore values below a certain threshold. Temperature values are only important in the upper range.

<img width="1064" alt="Bildschirmfoto 2024-06-01 um 07 43 40" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/f0dab00f-7128-4d2d-b367-66aa88e001bf">

#### Event Processor - Tumbling Window

For the KStream "stream-machine-production," we implemented a tumbling window of 60 seconds with a grace period of 5 seconds.

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 08 14" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/727e0d10-93dc-4c65-b3dc-cf3e0bbc6226">

#### Event Processor - KTable, Aggregate, and Suppress

Next, we added a KTable to our topology. In the stream "stream-machine-production," we want to count the number of received production events. First, we group the stream by key. The reason for this is that multiple machines can send their production data. Then, we use the previously mentioned tumbling window. We count and store the KeyValue pair under the key "machine-production-counts." Finally, we configure a suppression to emit only the "final results" from the window.

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 08 33" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/b3bcd159-d31f-4ce6-84b6-27f18d630f61">

#### Event Processor - Remap

After successfully creating the KTable, we map new KeyValue pairs to a stream, using the time window as keys and the data from the KTable as values.

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 09 45" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/5f2b62b7-cd70-4c56-b991-aed2dd4ba72d">

#### Event Processor - Time Extractor

We decided to create the timestamp in the Machine microservice. This is intended to provide higher accuracy and directly mark the data when reading it out. To achieve this, we had to program a time extractor in the microservice event processor.

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 10 37" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/be5b6090-f633-40ca-9557-d7d47d8a2a26">

#### Event Processor - For Each

For debugging purposes, we implemented several for-each loops to print the current state of the stream.

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 09 57" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/c758dc92-0e01-48de-a7d3-42f8b049370f">

#### Event Processor - Join MachineTemperature & MachineProduction

In the end, we join the KStream MachineTemperature and MachineProduction. Therefore, we create a join window with a time difference of 60 seconds and a grace period of 10 seconds.

<img width="1064" alt="Bildschirmfoto 2024-06-01 um 07 50 45" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/c90e5d39-5b27-45ea-834d-283af50d56d2">

## Tutorial to run the application CiRa
1. Go to the path /project/.

2. Execute the command for process-oriented architecture (a)
```
docker-compose up --build
```

2. Execute the command for event-driven architecture (b)
```
docker-compose -f docker-compose-kstreams.yml up --build
```

3a. Run the StartProductionLineProcess on Camunda 8

OR

3b. Start Kafka Streams
```
curl --location 'localhost:4001/machine/status/toggle' \
--header 'Content-Type: application/machine+json' \
--data '{"machineProductionSpeed": 2}' && curl --location --request POST 'localhost:4001/machine/stream/production/toggle' \
--header 'Content-Type: application/machine+json'
```

### Note: Please do not run it simultaneously. Either run step 3 or step 4. Afterwards, restart the application.

## Architectural Decision Records
* [Architecture/Decisions](doc/architecture/decisions)

## Assignments
* [Assignment 01](assignments/assignment-1)
* [Assignment 02](assignments/assignment-2)
* [Assignment 03](assignments/assignment-3)
* [Assignment 04](assignments/assignment-4)
* [Assignment 06](assignments/assignment-6)
* [Assignment 09](assignments/assignment-9)

## Microservices
* [Factory](project/factory)
* [Warehouse](project/warehouse)
* [Logistics](project/logistics)
* [Machines](project/machines)

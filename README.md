# Event-driven and process-oriented Architecture - Group 5

## Changes to process-driven architecture (part-01)

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

<img width="685" alt="Bildschirmfoto 2024-05-31 um 23 00 59" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/c4d0bacb-08e5-4fb0-8b61-86f9a6d8172c">

### Event Processor - Topology

#### Event Processor - Example of a Kafka Stream Consumer

<img width="1064" alt="grafik" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/e9ca8a8d-1fbd-41c8-96dc-a7b17e136219">

#### Event Processor - Router (Map)

<img width="1064" alt="grafik" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/1073a31a-6287-4436-9532-d528292b3503">

#### Event Processor - Filter

<img width="1064" alt="grafik" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/1e4bd217-5418-4cd5-89c6-58ea24415304">


#### Event Processor - Tumbling Window

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 08 14" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/727e0d10-93dc-4c65-b3dc-cf3e0bbc6226">

#### Event Processor - KTable, Aggregate, and Suppress

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 08 33" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/b3bcd159-d31f-4ce6-84b6-27f18d630f61">

#### Event Processor - Remap

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 09 45" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/5f2b62b7-cd70-4c56-b991-aed2dd4ba72d">

#### Event Processor - Time Extractor

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 10 37" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/be5b6090-f633-40ca-9557-d7d47d8a2a26">

#### Event Processor - For Each

<img width="1064" alt="Bildschirmfoto 2024-05-31 um 23 09 57" src="https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/c758dc92-0e01-48de-a7d3-42f8b049370f">

## Tutorial to run the application CiRa
1. Go to the path /project/.

2. Execute the command:
```
docker-compose up --build
```

3. Run the process on Camunda 8

OR

4. Start Kafka Streams
```
POST Request to Machine X && POST Request start production line stream
```

### Note: Please do not run it simultaneously. Either run step 3 or step 4. Afterwards, restart the application.

## Architectural Decision Records
* [Architecture/Decisions](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/doc/architecture/decisions)

## Assignments
* [Assignment 01](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/assignments/assignment-1)
* [Assignment 02](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/assignments/assignment-2)
* [Assignment 03](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/assignments/assignment-3)
* [Assignment 04](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/assignments/assignment-4)

## Microservices
* [Factory](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/factory)
* [Warehouse](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/warehouse)
* [Logistics](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/logistics)
* [Machines](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/tree/main/project/machines)

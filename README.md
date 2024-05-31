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

![StartProductionLineProcess](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/7bf49bef-03ac-4d63-8320-8311679d82ba)

### Anthology Saga

The integration of the Anthology Saga pattern brings substantial advantages in managing complex
workflows with multiple related processes. By adopting the Anthology Saga, we can orchestrate
cohesive sequences of events across various services, ensuring consistency and reliability
throughout. This pattern allows us to group related sagas under a common overarching saga,
facilitating better organization and coordination of business processes. With the Anthology Saga,
we can handle intricate scenarios with ease, as it provides a structured approach to managing
dependencies and interactions between different saga instances. We use this pattern for our
“Supply Chain” process.

<img src="[path/to/image.png](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/3e649556-a21e-4bbf-b278-eb0e5bec01f5)" alt="Description" style="width:300px;">

![Supply Chain Process](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/3e649556-a21e-4bbf-b278-eb0e5bec01f5)


### Stateful Resilience Pattern - Human Intervention

In the Human Intervention pattern the system is designed to gracefully handle exceptional situations
by involving human intervention when necessary. When a critical error occurs that cannot be
resolved automatically or through traditional retry mechanisms, the system leads to human
operators for manual resolution. This pattern ensures that complex or ambiguous errors can be
addressed by human expertise, maintaining the integrity and reliability of the system. In our system
this pattern is used to make a decision about a further transfer in case of a transfer truck accident.

### Stateful Resilience Pattern - Stateful retry

In the Stateful Retry pattern within event-driven architecture, resilience is achieved by implementing
a stateful mechanism for retrying failed service tasks. Specifically, each service task is retried up to
three times upon encountering a failure. This approach aims to improve the robustness and fault
tolerance of the system by allowing failed tasks to be automatically retried.

### Outbox pattern



### Event Processor - Topology
#### Event Processor - Router (Map)
#### Event Processor - Filter
#### Event Processor - Tumbling Window
#### Event Processor - KTable
#### Event Processor - Aggregate
#### Event Processor - Surpress
#### Event Processor - Time Extractor
#### Event Processor - For Each

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

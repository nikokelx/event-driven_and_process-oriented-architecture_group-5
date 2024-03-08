# Exercise 2: Kafka with Spring
## Michal Cislo & Raffael Rot

### Table of Content

1. Present project idea
2. Show implementation of event notification
3. Show implementation of event-carried state transfer
4. Add an error scenario

#### Present project idea

The project encapsulates the supply chain of the company CiRa. 
It has various machines that work with wood. After that, the goods are processed in the factory. 
Furthermore, there is a warehouse that distributes the items currently. 
The application will be extended in the future. My is name Peter.

#### Event notification

A POST request can start a machine. 
The machine-01 service emits an event to the "machine-status" topic indicating its availability.  
The Factory service pays attention to this topic and knows that the machine-01 is available.

!Sequence Diagram

Instructions: 

```
pyhton3 main.py
Java .ct sd s
```

#### Event-carried state transfer

The microservice Factory saves the current inventory level of the goods. 
If a certain amount of wood shavings is in stock, the microservice sends a message called "stock-update." 
The microservice Warehouse listens and keeps track of the data.

!Sequence Diagram

Instructions:

```

```

#### Error scenario

# Test 7: Acknowledgement configuration

## Introduction
Configuration of the `acks` parameter is the number of brokers who need to
acknowledge receiving the message before it is considered a successful write.<br>

### `acks=0`
When acks=0 producers consider messages as "written successfully" the moment the message was sent without waiting for the broker to accept it at all.<br>
<span style="color:red">**!!!** </span> If the broker goes offline or an exception happens, we won’t know and will lose data. This is useful for data where it’s okay to potentially lose messages, such as metrics collection, and produces the highest throughput setting because the network overhead is minimized.

### `acks=1`
When acks=1 , producers consider messages as "written successfully" when the message was acknowledged by only the leader.<br>
<span style="color:red">**!!!** </span> Leader response is requested, but replication is not a guarantee as it happens in the background. If the leader broker goes offline unexpectedly but replicas haven’t replicated the data yet, we have a data loss.

### `acks=all`
When acks=all, producers consider messages as "written successfully" when the message is accepted by all in-sync replicas (ISR). The lead replica for a partition checks to see if there are enough in-sync replicas for safely writing the message. The request will be stored in a buffer until the leader observes that the follower replicas replicated the message, at which point a successful acknowledgement is sent back to the client.<br>
The `min.insync.replicas` can be configured both at the topic and the broker-level. The data is considered committed when it is written to all in-sync replicas - `min.insync.replicas`.

## Setup
### 1. Replicas factor = 1 (1 broker):
`docker-compose.yml`:
```dockerfile
version: "3"
services:
  zookeeper:
    image: bitnami/zookeeper:latest
    container_name: zookeeper
    environment:
      ALLOW_ANONYMOUS_LOGIN: "yes"
    ports:
      - 2181:2181

  kafka1:
    image: bitnami/kafka:latest
    container_name: kafka1
    hostname: kafka1
    ports:
      - 19092
      - 9092:9092
    environment:
      ALLOW_PLAINTEXT_LISTENER: "yes"
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_BROKER_ID: 1
      KAFKA_CFG_LISTENERS: CLIENT://:19092,EXTERNAL://:9092
      KAFKA_CFG_ADVERTISED_LISTENERS: CLIENT://kafka1:19092,EXTERNAL://localhost:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_INTER_BROKER_LISTENER_NAME: CLIENT
    depends_on:
      - zookeeper
```

`prducer.properties`:
```properties
acks=all
retries=0
bootstrap.servers=localhost:9092
key.serializer=org.apache.kafka.common.serialization.StringSerializer
value.serializer=org.apache.kafka.common.serialization.StringSerializer
```

In this experiment there are 3 possible configurations to test. For every test make sure to configure the property `acks` accordingly to the testing configuration.<br>
Possible options `acks=` `all`/`1`/`0`. Make sure to modify the `prducer.properties` file and later run:

```
docker-compose up --build
```

Open an additional terminal window and create `project_events` topic by running:
```
docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka1:9092 --replication-factor 1 --partitions 1 --topic project_events
```

Finally, start the `Producer` class and wait for the output which will show the result of how long did it take to send 100000 messages. 


### 2. Replicas factor = 1 (2 brokers)
The second experiment is very similar to the first one. Only make sure to modify the `docker-compose.yml` by adding another kafka broker:

```dockerfile
  kafka2:
    image: bitnami/kafka:latest
    container_name: kafka2
    hostname: kafka2
    ports:
      - 19092
      - 9093:9093
    environment:
      ALLOW_PLAINTEXT_LISTENER: "yes"
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_BROKER_ID: 2
      KAFKA_CFG_LISTENERS: CLIENT://:19092,EXTERNAL://:9093
      KAFKA_CFG_ADVERTISED_LISTENERS: CLIENT://kafka2:19092,EXTERNAL://localhost:9093
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_INTER_BROKER_LISTENER_NAME: CLIENT
    depends_on:
      - zookeeper
```

Follow the same steps as in the first experiment to run docker compose, create a topic and start `Producer`.

### 3. Replicas factor = 2 (2 brokers)
When it comes to the third experiment follow the steps from the previous to set up `docker-compose.yml`.<br>
Before starting the experiment run:
```
docker-compose up --build
```

Open an additional terminal window and create `project_events` topic ***this time with `--replication-factor 2`*** by running:
```
docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka1:9092 --replication-factor 2 --partitions 1 --topic project_events
```

### 4. Replicas factor = 3 (3 brokers)
For the fourth experiment make sure to add another broker to kafka by modifying `docker-compsoe.yml` from the third experiment:
```dockerfile
  kafka3:
    image: bitnami/kafka:latest
    container_name: kafka3
    hostname: kafka3
    ports:
      - 19092
      - 9094:9094
    environment:
      ALLOW_PLAINTEXT_LISTENER: "yes"
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_BROKER_ID: 3
      KAFKA_CFG_LISTENERS: CLIENT://:19092,EXTERNAL://:9094
      KAFKA_CFG_ADVERTISED_LISTENERS: CLIENT://kafka3:19092,EXTERNAL://localhost:9094
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_INTER_BROKER_LISTENER_NAME: CLIENT
    depends_on:
      - zookeeper
```

Before starting the experiment run:
```
docker-compose up --build
```

Open an additional terminal window and create `project_events` topic ***this time with `--replication-factor 3`*** by running:
```
docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka1:9092 --replication-factor 3 --partitions 1 --topic project_events
```

## Conduct the experiment
### 1. Replicas factor = 1 (1 broker)
Time taken to send 100000 messages:<br>
`acks = all` ~= 586 ms<br>
`acks = 1` ~= 550 ms<br>
`acks = 0` ~= 510 ms

### 2. Replicas factor = 1 (2 brokers)
Time taken to send 100000 messages:<br>
`acks = all` ~= 582 ms<br>
`acks = 1` ~= 543 ms<br>
`acks = 0` ~= 518 ms

### 3. Replicas factor = 2 (2 brokers)
`acks = all`:
- Time taken to send 100000 messages: ~= 615 ms<br>
- Time taken to send 1000000 messages: ~= 4960 ms<br>

`acks = 1`:
- Time taken to send 100000 messages: ~= 554 ms<br>
- Time taken to send 1000000 messages: ~= 3405 ms<br>

`acks = 0`:
- Time taken to send 100000 messages: ~= 505 ms<br>
- Time taken to send 1000000 messages: ~= 2689 ms<br>

### 4. Replicas factor = 3 (3 brokers)
`acks = all`:
- Time taken to send 100000 messages: ~= 726 ms<br>
- Time taken to send 1000000 messages: ~= 8475 ms<br>

`acks = 1`:
- Time taken to send 100000 messages: ~= 631 ms<br>
- Time taken to send 1000000 messages: ~= 4060 ms<br>

`acks = 0`:
- Time taken to send 100000 messages: ~= 515 ms<br>
- Time taken to send 1000000 messages: ~= 3701 ms<br>

## Conclusion
Based on the conducted Kafka experiments in Spring Boot, several conclusions can be drawn. Firstly, with a replica factor of 1 and one broker, there is a marginal difference in the time taken to send 100,000 messages regardless of the acknowledgment setting, with acks = 0 showing slightly better performance. When introducing a second broker with the same replica factor, again, there's minimal variation in message sending time among acknowledgment settings, with acks = 0 consistently being the fastest. This is due to the fact that in both cases we only used `--replication-factor 1`.<br>
<span style="color:red">**!!!** </span> However, with a replica factor of 2 and 2 brokers, there's a noticeable increase in the time taken, especially when acknowledging all replicas. This trend continues as the replica factor and number of brokers increase, indicating a trade-off between data redundancy and message throughput. Notably, as redundancy increases, so does message transmission time, particularly evident with acknowledgment of all replicas. Therefore, depending on the application's requirements, the choice of replica factor and acknowledgment setting should be carefully considered to strike a balance between fault tolerance and performance.
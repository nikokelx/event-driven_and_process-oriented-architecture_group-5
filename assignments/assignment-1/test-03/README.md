# Test 3: The outage of Zookeper

## Introduction
ZooKeeper has a critical role in a Kafka cluster by providing distributed coordination and synchronization services. It maintains the cluster's metadata, manages leader elections, and enables consumers to track their consumption progress.

## Setup
### Update (Investigate what happens, with more than 1 broker, when Zookeeper is down):
Configure 2 brokers and run docker compose:
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

```
docker-compose up --build
```

Start `Consumer` and `Producer`.

## Conduct the experiment
Start docker compose and then producer and consumer. Turn off Zookeeper in docker, start producer again, make sure consumer is running. Once producer has started sending messages, consumer will read them normally.

### Update (Investigate what happens, with more than 1 broker, when Zookeeper is down)::
When `Consumer` and `Producer` are running, pause the Zookeeper in docker and turn off `Consumer`.<br>
You should see some reconnect tries in the terminal:
```
kafka1       | org.apache.zookeeper.ClientCnxn$SessionTimeoutException: Client session timed out, have not heard from server in 12000ms for session id 0x1000032b94e0000
kafka1       |  at org.apache.zookeeper.ClientCnxn$SendThread.run(ClientCnxn.java:1257)
kafka2       | [2024-03-09 13:46:43,520] WARN Client session timed out, have not heard from server in 12004ms for session id 0x1000032b94e0001 (org.apache.zookeeper.ClientCnxn)
kafka2       | [2024-03-09 13:46:43,520] WARN Session 0x1000032b94e0001 for server zookeeper/172.23.0.2:2181, Closing socket connection. Attempting reconnect except it is a SessionExpiredException. (org.apache.zookeeper.ClientCnxn)
kafka2       | org.apache.zookeeper.ClientCnxn$SessionTimeoutException: Client session timed out, have not heard from server in 12004ms for session id 0x1000032b94e0001
kafka2       |  at org.apache.zookeeper.ClientCnxn$SendThread.run(ClientCnxn.java:1257)
```

Now, start the `Consumer` again and what you will notice is that it will not read the messages anymore. This is due to the Zookeeper being down.
This is the error you should see in the terminal:
```
kafka.common.StateChangeFailedException: Failed to elect leader for partition __consumer_offsets-27 under strategy OfflinePartitionLeaderElectionStrategy(false)
```

When you start the Zookeeper again after a while it will reestablish the connection and `Consumer` will continue to read messages.


## Conclusion
If you stop a Zookeeper everything should still work fine, because the controller broker is still operating. However, if the Zookeeper would be down for a longer period of time it could lead to some serious cascading effect problems.

### Update
Zookeeper enables consumers to track their consumption progress, so if the Zookeeper is not working consumers cannot continue to consume messages after they have to be for example restarted.
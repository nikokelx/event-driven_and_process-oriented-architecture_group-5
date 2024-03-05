# Test 5: The risk of data loss due to offset misconfigurations

## Introduction
Every message that producers send to a Kafka partition has an offset—a sequential index number that identifies each message. To keep track of which messages have already been processed, consumer needs to commit the offsets of the messages that were already read.<br>
Misconfiguration of offset may introduce problems like ***data loss*** or ***data duplication***.

## Setup

Before every test:
1. Modify the `consumer.properties` file
2. Run Docker Compose to start Zookeeper and Kafka
3. Create topic with 1 partition:
```
docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic project_events
```

#### 1. auto.offset.reset = earliest
`.../resources/consumer.properties`:

```
bootstrap.servers=localhost:9092
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
group.id=test-05
auto.offset.reset=earliest
```
#### 2. auto.offset.reset = latest
`.../resources/consumer.properties`:

```
bootstrap.servers=localhost:9092
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
group.id=test-05
auto.offset.reset=latest
```

#### 3. enable.auto.commit = false with auto.offset.reset = earliest
`.../resources/consumer.properties`:

```
bootstrap.servers=localhost:9092
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
group.id=test-05
auto.offset.reset = earliest
enable.auto.commit=false
```

#### 4. enable.auto.commit = false with auto.offset.reset = latest
`.../resources/consumer.properties`:

```
bootstrap.servers=localhost:9092
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
group.id=test-05
auto.offset.reset = latest
enable.auto.commit=false
```

#### 5. enable.auto.commit = true with auto.commit.interval.ms
`.../resources/consumer.properties`:

```
bootstrap.servers=localhost:9092
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
group.id=test-05
enable.auto.commit=true
auto.commit.interval.ms=100
```

#### 6. enable.auto.commit = false with custom committing
`.../resources/consumer.properties`:

```
bootstrap.servers=localhost:9092
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
group.id=test-05
enable.auto.commit=false
```

Part1 - Uncomment the code in `Consumer.java` necessary for this test. **Committing after all polled records:**
```java
79| try {
80|     kafkaConsumer.commitSync();
81| } catch (CommitFailedException e) {
82|     System.out.println("commit failed: " + e);
83| }
```

Part2 - Uncomment the code in `Consumer.java` necessary for this test. **Committing after every 10 records or when polled records amount is less than 5:**
```java
15| private static Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
|
|
67| currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()+1, "no metadata"));
68| if ((count >= 10 && count % 10 == 0) || records.count() < 5) {
69|    kafkaConsumer.commitAsync(currentOffsets, null);
70| }
```

## Conduct the experiment

#### 1. auto.offset.reset = earliest:
```
Partition 0: Last Offset Stored by Broker = 2323, Last Committed Offset = 2238, Current Lag = 85
----------
Received project_events - Offset = 2321 key: project_id_2321 partition: 0
---------------------------------------------------------------
Polled records: 1
Partition 0: Last Offset Stored by Broker = 2324, Last Committed Offset = 2238, Current Lag = 86
----------
Received project_events - Offset = 2322 key: project_id_2322 partition: 0
---------------------------------------------------------------
Polled records: 1
Partition 0: Last Offset Stored by Broker = 2325, Last Committed Offset = 2238, Current Lag = 87
----------
Received project_events - Offset = 2323 key: project_id_2323 partition: 0
---------------------------------------------------------------
```
Last offset stored by broker `2325` <br>
Last committed offset `2238` <br>
Last current offset `2323`

When `Consumer` is up again:

```
Polled records: 500
Partition 0: Last Offset Stored by Broker = 4197, Last Committed Offset = 2238, Current Lag = 1959
----------
Received project_events - Offset = 2238 key: project_id_2238 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 4197, Last Committed Offset = 2238, Current Lag = 1959
----------
Received project_events - Offset = 2239 key: project_id_2239 partition: 0
---------------------------------------------------------------
```

Messages from `2238` to `2323` duplicated.

#### 2. auto.offset.reset = latest:

```
Partition 0: Last Offset Stored by Broker = 1871, Last Committed Offset = 1519, Current Lag = 352
----------
Received project_events - Offset = 1869 key: project_id_1869 partition: 0
---------------------------------------------------------------
Polled records: 1
Partition 0: Last Offset Stored by Broker = 1872, Last Committed Offset = 1519, Current Lag = 353
----------
Received project_events - Offset = 1870 key: project_id_1870 partition: 0
---------------------------------------------------------------
```
Last offset stored by broker `1872` <br>
Last committed offset `1519` <br>
Last current offset `1870`

When `Consumer` is up again:

```
Partition 0: Last Offset Stored by Broker = 4015, Last Committed Offset = 1519, Current Lag = 2496
----------
Received project_events - Offset = 1519 key: project_id_1519 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 4015, Last Committed Offset = 1519, Current Lag = 2496
----------
Received project_events - Offset = 1520 key: project_id_1520 partition: 0
---------------------------------------------------------------
```

Messages from `1519` to `1870` duplicated.

#### 3. enable.auto.commit = false with auto.offset.reset = earliest:
Last message received before going down:
```
----------
Received project_events - Offset = 1317 key: project_id_1317 partition: 0
---------------------------------------------------------------
```

Reading messages from beginning after going up:
```
Polled records: 500
----------
Received project_events - Offset = 0 key: project_id_0 partition: 0
---------------------------------------------------------------
----------
Received project_events - Offset = 1 key: project_id_1 partition: 0
---------------------------------------------------------------
.
.
.
```

#### 4. enable.auto.commit = false with auto.offset.reset = latest:
Last message received before going down:
```
Received project_events - Offset = 2010 key: project_id_2010 partition: 0
---------------------------------------------------------------
```

Reading messages from beginning after going up:
```
Received project_events - Offset = 10100 key: project_id_10100 partition: 0
---------------------------------------------------------------
----------
Received project_events - Offset = 10101 key: project_id_10101 partition: 0
---------------------------------------------------------------
.
.
.
```

#### 5. enable.auto.commit = true with auto.commit.interval.ms:
Offset is committed automatically every 100ms or when polled records are processed.
```
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 119, Last Committed Offset = 43, Current Lag = 76
----------
Received project_events - Offset = 68 key: project_id_68 partition: 0
---------------------------------------------------------------
Polled records: 23
Partition 0: Last Offset Stored by Broker = 122, Last Committed Offset = 69, Current Lag = 53
----------
Received project_events - Offset = 69 key: project_id_69 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 123, Last Committed Offset = 69, Current Lag = 54
----------
Received project_events - Offset = 70 key: project_id_70 partition: 0
---------------------------------------------------------------
```


#### 6. enable.auto.commit = false with custom committing:
Consumer polled 5 records and as shown below custom committing `commitSync()` commits the offset after looping through all polled records.
```
Polled records: 5
Partition 0: Last Offset Stored by Broker = 31, Last Committed Offset = 13, Current Lag = 18
----------
Received project_events - Offset = 13 key: project_id_13 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 32, Last Committed Offset = 13, Current Lag = 19
----------
Received project_events - Offset = 14 key: project_id_14 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 33, Last Committed Offset = 13, Current Lag = 20
----------
Received project_events - Offset = 15 key: project_id_15 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 34, Last Committed Offset = 13, Current Lag = 21
----------
Received project_events - Offset = 16 key: project_id_16 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 35, Last Committed Offset = 13, Current Lag = 22
----------
Received project_events - Offset = 17 key: project_id_17 partition: 0
---------------------------------------------------------------
Polled records: 12
Partition 0: Last Offset Stored by Broker = 38, Last Committed Offset = 18, Current Lag = 20
----------
Received project_events - Offset = 18 key: project_id_18 partition: 0
---------------------------------------------------------------
```
We can notice that it significantly decreases current consumer lag.

We can also modify the code so that we `commitAync()` every time we process 10 messages or when poll amount was less than 5.<br>
Polled less than 5 - immediate commit:
```
Polled records: 1
Partition 0: Last Offset Stored by Broker = 219, Last Committed Offset = 216, Current Lag = 3
----------
Received project_events - Offset = 216 key: project_id_216 partition: 0
---------------------------------------------------------------
Polled records: 2
Partition 0: Last Offset Stored by Broker = 220, Last Committed Offset = 217, Current Lag = 3
----------
Received project_events - Offset = 217 key: project_id_217 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 220, Last Committed Offset = 218, Current Lag = 2
----------
Received project_events - Offset = 218 key: project_id_218 partition: 0
---------------------------------------------------------------
Polled records: 1
Partition 0: Last Offset Stored by Broker = 221, Last Committed Offset = 219, Current Lag = 2
----------
Received project_events - Offset = 219 key: project_id_219 partition: 0
---------------------------------------------------------------
```

Commit every 10 records:
```
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 133, Last Committed Offset = 100, Current Lag = 33
----------
Received project_events - Offset = 109 key: project_id_109 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 133, Last Committed Offset = 110, Current Lag = 23
----------
Received project_events - Offset = 110 key: project_id_110 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 134, Last Committed Offset = 110, Current Lag = 24
----------
Received project_events - Offset = 111 key: project_id_111 partition: 0
---------------------------------------------------------------
.
.
Partition 0: Last Offset Stored by Broker = 140, Last Committed Offset = 110, Current Lag = 30
----------
Received project_events - Offset = 119 key: project_id_119 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 141, Last Committed Offset = 120, Current Lag = 21
----------
Received project_events - Offset = 120 key: project_id_120 partition: 0
---------------------------------------------------------------
Partition 0: Last Offset Stored by Broker = 142, Last Committed Offset = 120, Current Lag = 22
----------
Received project_events - Offset = 121 key: project_id_121 partition: 0
---------------------------------------------------------------
```

## Conclusion

`1. auto.offset.reset = earliest` and `2. auto.offset.reset = latest` both duplicated messages and started again after Consumer was up from the `Last committed offset`.<br><br>
`auto.offset.reset` - This property controls the behavior of the consumer when it starts reading a partition
for which it doesn’t have a committed offset or if the committed offset it has is invalid. This is the reason why both options `=true` and `=false` started from the last committed offset (It was valid committed offset).<br>

`3. enable.auto.commit = false with auto.offset.reset = earliest` this test did not commit any offset and since the offset was invalid when the Consumer was up again it started reading messages from the beginning.<br>

`4. enable.auto.commit = false with auto.offset.reset = latest` this test did not commit any offset and since the offset was invalid when the Consumer was up again and connected back to the topic it started reading the newest messages that Producer sent.<br>

`enable.auto.commit = true with auto.commit.interval.ms` this test commits new offset after every custom interval has passed.<br>

`enable.auto.commit = false with custom committing` custom committing has its advantages and drawback, so it has to be implemented wisely. This test is constructed out of two parts.<br>
Part 1: `commitSync()` commits the offset after looping through all polled records. Alternative `commitAsync()`, not to wait for the response if the offset was committed.<br>
Part 2: 'very' custom committing using `commitAync()` every time we process 10 messages or when poll amount was less than 5.

To conclude, developers have to be very careful when it comes to configuring offset otherwise there might be many duplicated or lost data read by consumers.













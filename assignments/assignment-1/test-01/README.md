# Test 1: Testing the impact of load and batch size on processing latency

## Introduction

For this experiment, wer define the benchmarking goals:
1. Throughput: Measure how many maessages per seconds Kafka can handle.
2. Latency: Measure the delay between the production of a message and its consumption.

[The documentation of confluent](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html#batch-size) provides an good overview how the batch size impact throughput and latency.
* A small batch size will make batching less common and may reduce throughput.
* A very large batch size may use memory a bit more wastefully as we will always allocate a buffer of the specified batch size in anticipation of additional records. 

## Setup

For this experiment, we use the script kafka-producer-perf-test.sh. 

| Experiment | num records | record size | throughput | batch.size | linger.ms |
|------------|-------------|-------------|------------|------------|-----------|
| 1          | 100         | 10'000'000  | -1         | 5'000      | 0         |
| 2          | 100         | 10'000'000  | -1         | 10'000     | 0         |
| 3          | 100         | 10'000'000  | -1         | 20'000     | 0         |
| 4          | 100         | 10'000'000  | -1         | 5'000      | 5         |
| 5          | 100         | 10'000'000  | -1         | 10'000     | 5         |
| 6          | 100         | 10'000'000  | -1         | 20'000     | 5         |
| 7          | 100         | 10'000'000  | -1         | 5'000      | 10        |
| 8          | 100         | 10'000'000  | -1         | 10'000     | 10        |
| 9          | 100         | 10'000'000  | -1         | 20'000     | 10        |

## Conduct the experiment

![experiment-01](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/adc585d2-29b1-4929-be9b-6b230bf85289)

![experiment-02](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/553e1485-5960-42cb-83d5-094e4d673759)

![experiment-03](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/632ed6bc-2557-48df-99f6-dc23f959e4ff)

![experiment-04](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/d8f5b990-d979-473b-8540-298548c86dbd)

![experiment-05](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/b4ed31c8-03b5-4a22-89f1-507c617056fe)

![experiment-06](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/086e8a40-c28b-4c1d-af01-c759e0243f11)

![experiment-07](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/0eccee02-444c-45f1-8213-0550539a63c2)

![experiment-08](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/eefdf8a8-96ba-4033-a563-f537da31ae11)

![experiment-09](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/52456cba-345e-45f9-8bd0-1742a2487cee)


## Conclusion

| Experiment | Throughput (records/sec) | avg latency (ms) | max latency (ms)  |
|------------|--------------------------|------------------|-------------------|
| 1          | 14.99                    | 1871.38          | 2627              |
| 2          | 18.51                    | 1509.28          | 2621              |
| 3          | 19.24                    | 1468.39          | 2705              |
| 4          | 16.81                    | 1671.24          | 2611              |
| 5          | 23.52                    | 1185.41          | 2600              |
| 6          | 33.44                    | 822.38           | 2297              |
| 7          | 14.42                    | 1940.79          | 3764              |
| 8          | 18.33                    | 1511.54          | 2420              |
| 9          | 32.90                    | 848.39           | 2784              |

The experiments show that with increasing parameters
* the throughput increases,
* the average latency decreases.

The experiment with the best results has the parameters batch.size=20'000 and linger.ms=5.



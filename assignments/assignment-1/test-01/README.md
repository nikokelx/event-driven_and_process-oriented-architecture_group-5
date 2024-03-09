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

## Conclusion

| Experiment | Throughput (records/sec) | avg latency (ms) | max latency (ms)  |
|------------|--------------------------|------------------|-------------------|
| 1          |                          |                  |                   |
| 2          |                          |                  |                   |
| 3          |                          |                  |                   |
| 4          |                          |                  |                   |
| 5          |                          |                  |                   |
| 6          |                          |                  |                   |
| 7          |                          |                  |                   |
| 8          |                          |                  |                   |
| 9          |                          |                  |                   |

The experiments show that with increasing batch size the throughput increases. Additionally, the average latency deacreses with increasing batch size. 


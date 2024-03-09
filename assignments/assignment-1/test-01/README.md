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

### Experiement 1
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 5'000 \
linger.ms: 0 \

### Experiment 2
Num-records: 10'000'000 \
record-size: 100\
throughput -1 \
batch.size: 10'000 \
linger.ms: 0 \

### Experiment 3
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 20'000 \
linger.ms: 0 \

### Experiment 4
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 5'000 \
linger.ms: 5 \

### Experiment 5
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 10'000 \
linger.ms: 5 \

### Experiment 6
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 20'000 \
linger.ms: 5 \

### Experiment 7
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 5'000 \
linger.ms: 10 \

### Experiment 8
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 10'000 \
linger.ms: 10 \

### Experiment 9
Num-records: 10'000'000 \
record-size: 100 \
throughput -1 \
batch.size: 20'000 \
linger.ms: 10 \

## Conduct the experiment

## Conclusion
The experiments show that with increasing batch size the throughput increases. Additionally, the average latency deacreses with increasing batch size. 


# Test 2: Testing the risk of data loss due to dopped messages

## Introduction

For this experiment, wer define the benchmarking goal:
1. How significant is the data loss if the Kafka container shuts down unexpectedly?

To test the experiment, we are going to use the implementation of the producer and consumer.
Additionally, we run kafka inside a docker container, and we test the kafka configurations acks and retries. \

Experiments:
1. acks=0; retries=0
2. acks=0; retries=1
3. acks=1; retries=0
4. acks=1; retries=1

The producer will send messages to a specifc topic. The consumer reads the messages. After some time, 
I will shut down the docker container for a period of time. Afterwards, we evaluate how significant is the data loss.

## Setup

1. Run the docker container
2. Execute the producer
3. Execute the consumer
4. Shut down the docker container for few seconds
5. Restart the docker container 
6. Evaluate the behavior of the producer and consumer
7. Restart the experiment with different kafka configurations

## Conduct the experiment

![test-1_consumer-0-0](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/d1fd841f-fdee-4588-96bb-ccc1d039bccd)

The first experiment shows 136`025 dropped messages.

![test-2_consumer_0-1](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/5e286075-cbbd-46bf-977a-73c5956f82bc)

The second experiment shows 77'804 dropped messages.

![test-3_consumer_1-0](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/2f966047-4aa2-4dfb-8038-703cd5508efd)

The third experiment shows 945 dropped messages.

![test-4_consumer_1-1](https://github.com/nikokelx/event-driven_and_process-oriented-architecture_group-5/assets/95875428/104ec423-4cfc-442c-94d9-3a2a6926597f)

The fourth experiment shows zero dropped messages.

## Conclusion

The Kafka config acks has a significant impact on the risk of data loss.
With each stronger setting, the number of dropped messages decreases.
On the other hand, adjusting the Kafka config reduces the amount of dropped messages,
but the impact is not that significant. A setting of acks=1 and retries=1 is enough
to reduce the number of dropped messages to zero.

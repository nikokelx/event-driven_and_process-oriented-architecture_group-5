# Test 3: The outage of Zookeper

## Introduction
ZooKeeper has a critical role in a Kafka cluster by providing distributed coordination and synchronization services. It maintains the cluster's metadata, manages leader elections, and enables consumers to track their consumption progress.

## Setup
_

## Conduct the experiment
Start docker compose and then producer and consumer. Turn off Zookeeper in docker, start producer again, make sure consumer is running. Once producer has started sending messages, consumer will read them normally.

## Conclusion
If you stop a Zookeeper everything should still work fine, because the controller broker is still operating. However, if the Zookeeper would be down for a longer period of time it could lead to some serious cascading effect problems.

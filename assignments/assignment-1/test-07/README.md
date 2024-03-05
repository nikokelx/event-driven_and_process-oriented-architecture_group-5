# Test 7: Acknowledgement configuration

## Introduction

## Setup
### 1. Replicas factor = 1 (1 broker)
`acks = all` ~= 586 ms
`acks = 1` ~= 550 ms
`acks = 0` ~= 510 ms

### 2. Replicas factor = 1 (2 brokers)
`acks = all` ~= 586 ms
`acks = 1` ~= 550 ms
`acks = 0` ~= 510 ms

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

## Conduct the experiment

## Conclusion

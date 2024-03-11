# 5. Code sharing

Date: 2024-03-11

## Status

Accepted

## Context

The current codebase exhibits signs of code duplication, particularly in the Kafka configuration setup. As the codebase is predicted to grow in the future, this duplication may lead to maintainability issues and significant redundancy problems.

## Decision

We decided against implementing shared libraries for Kafka configuration at this stage of the project. However, as the project is expected to grow, this decision will be revisited and evaluated again in the future.

## Consequences

* ***Potential for increased code duplication***: Without the implementation of a shared library, there is a risk of continued code duplication in Kafka configuration across different modules of the application.
* ***Potential for inconsistent configuration***: The absence of a centralized configuration approach may result in inconsistencies in Kafka configuration settings across different parts of the codebase.
<br><br>
* ***Flexibility for current needs***: By not implementing a shared library for Kafka configuration at this stage, we can maintain flexibility to address immediate needs and priorities without the overhead of designing, implementing, and integrating a shared library.
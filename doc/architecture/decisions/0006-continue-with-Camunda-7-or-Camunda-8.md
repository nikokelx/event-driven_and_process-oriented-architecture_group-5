# 6. Continue with Camunda 7 or Camunda 8

Date: 2024-04-05

## Status

Accepted

## Context

We must decide whether to continue with our current version, Camunda 7, or migrate to the newer version, Camunda 8. This decision requires careful consideration of the benefits, features, and alignment with our team goals and individual skills.

## Decision

After evaluation, we have decided to migrate from Camunda 7 to Camunda 8. This decision is based on several factors outlined below:
1.	**Desire to experiment:** We had been using Camunda 7 since the beginning of the semester, so the moment we had the opportunity to use Camunda 8 and learn about the changes and differences between the versions, we decided to migrate.
2.	**New skills:** Camunda 8 introduced new developments to its engine. We decided to switch to Camunda 8 in order to gain more skills in using the platform and the new developments in the code.
3.	**Future-proof solution:** After considering the scalability and architectural differences between the Zeebe engine in Camunda 8 and the Camunda 7 engine, we've decided to use to Camunda 8. This decision is based on the scalability of Zeebe, does not rely on a relational database, eliminating its major bottleneck for scaling the engine.

## Consequences

* Integration with existing systems and workflows may require adjustments to ensure smooth transition and minimal disruption.
* Adoption of new features and technologies may entail a learning curve.

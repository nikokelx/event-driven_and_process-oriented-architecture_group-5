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

## Advantages

1.	**Improved Scalability**: The Zeebe engine in Camunda 8 offers enhanced scalability by eliminating the reliance on a relational database. This makes it more suitable for handling larger workloads and more complex processes.
2.	**Modern Architecture**: Camunda 8 introduces a more modern architecture that aligns with current industry standards and practices, potentially making it easier to integrate with other contemporary systems.
3.	**Enhanced Features**: The new features and improvements in Camunda 8 provide opportunities to learn and utilize the latest advancements in process automation, keeping our skills up-to-date.
4.	**Community and Support**: As the latest version, Camunda 8 is likely to receive more active support and updates from the community and developers, ensuring we have access to the latest fixes and improvements.

## Disadvantages

1.	**Learning Curve**: Adopting Camunda 8 involves a learning curve. We will need to invest time and effort to understand the new features and changes, which might temporarily slow down our progress.
2.  **Compatibility Issues**: There may be challenges in ensuring that our existing systems and workflows integrate smoothly with Camunda 8. This might require adjustments and additional development work.
3.  **Initial Disruption**: The transition from Camunda 7 to Camunda 8 could cause some initial disruption. We will need to manage this carefully to minimize the impact on ongoing projects.
4.	**Resource Allocation**: Migrating to a new version may require reallocation of resources, both in terms of time and personnel, to handle the migration process and address any issues that arise.

## Consequences

* **Integration Adjustments**: Moving to Camunda 8 will require modifications to our current systems and workflows. We need to plan to ensure these adjustments are made smoothly, reducing the risk of significant disruptions.
* **Learning Curve**: Adopting Camunda 8 will involve a learning curve as we familiarise ourselves with new features.
* **Temporary Disruption**: The migration process may cause some short-term disruption.

# High Level Architecture

![System Architecture](diagrams/system-architecture.png)

The ServiceHub platform is designed using
a microservices architecture.

Each service is independently deployable
and communicates using REST APIs and events.

## Core Components

Client Applications
- Web
- Mobile

API Gateway
- Responsible for routing requests
- Provides authentication and rate limiting

Microservices
- User Service
- Provider Service
- Booking Service
- Review Service
- Notification Service

Messaging System
- Kafka for asynchronous communication

Databases
- Each service maintains its own database

Infrastructure
- Docker
- Kubernetes

Monitoring
- Splunk
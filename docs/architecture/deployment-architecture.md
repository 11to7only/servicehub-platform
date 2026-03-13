# Deployment Architecture

![Kubernetes Deployment](diagrams/kubernetes-deployment.png)

The platform will be deployed using containerized
microservices.

## Components

Containers
- Each service runs in its own Docker container

Orchestration
- Kubernetes manages container deployment

CI/CD
- Jenkins pipelines build and deploy services
  ![CI/CD Pipeline](diagrams/cicd-pipeline.png)

Monitoring
- Splunk collects application logs
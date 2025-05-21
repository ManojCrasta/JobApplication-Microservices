# 🧾 Job Application Microservices

Welcome to the Job Application Microservices project. This application allows you to manage job postings, company profiles, and company reviews. 
It is built using a microservices architecture powered by Spring Boot and Docker, 
using PostgreSQL as the database and pgAdmin for DB administration.

## 📚 Table of Contents

* Features

* Tech Stack

* Architecture

* Getting Started

* Installation

* Configuration

* Usage

* Checking Service Status

* API Endpoints

## ✅ Features

This application provides the following core features:

### Company Service
* Create, update, and delete companies

* Get company details by ID or list all companies

* Automatically updates the average rating when reviews are added (via RabbitMQ)

### Job Service
* Post new jobs for companies

* Update or delete job postings

* Get job details by ID or list all jobs

### Review Service
* Add, update, and delete reviews for companies

* Fetch individual or all reviews (filterable by companyId)

* Automatically calculates and updates the company’s average rating via RabbitMQ

### Service Registry
Register and discover microservices.

### Configuration Server
Manage centralized configurations for microservices.

### API Gateway
Gateway for accessing microservices.

## 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Cloud Gateway
* Spring Cloud Config Server
* Netflix Eureka Server
* RabbitMQ (Asynchronous messaging)
* PostgreSQL
* pgAdmin
* Docker & Docker Compose
* Zipkin (Distributed tracing for microservices)


## Getting Started

Ensure you have the following installed:

* Docker
* Git


## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ManojCrasta/Job-Application-Microservices.git 
   ``` 

2. Navigate to the directory:
   ```bash
   cd JobApplication
   ``` 

3. Start services:
   ```bash
   docker-compose up --build
   ``` 

This command will spin up all the microservices, along with PostgreSQL, pgAdmin, RabbitMQ, Zipkin, Eureka, and the Config Server.

## Configuration
* All configuration files are managed centrally through the Spring Cloud Config Server.
* Environment variables for PostgreSQL and other services are set in docker-compose.yml.
* pgAdmin is preconfigured to connect to the PostgreSQL service.
* PostgreSQL databases are initialized via SQL scripts placed in the db-init/ directory.

## Usage
Once the containers are up, you can interact with the microservices via Postman or cURL.

* Access the API Gateway at: http://localhost:8084

* All requests to services go through the gateway.

## Checking Service Status
✅ Eureka Server
URL: http://localhost:8761

View all registered services and instances.


✅ RabbitMQ
URL: http://localhost:15672

Default user: guest, password: guest

✅ Zipkin (Monitoring & Tracing)
URL: http://localhost:9411

Use this to trace inter-service communication and performance.

✅ pgAdmin
URL: http://localhost:5050

Pre-configured to connect to PostgreSQL

## API Endpoints (via Gateway)
Here are routes once the services are registered and gateway is up:

#### Job Service Routes (jobms)
* Create a job:
POST http://localhost:8084/jobs

* Update a job:
PUT http://localhost:8084/jobs/{id}

* Delete a job:
DELETE http://localhost:8084/jobs/{id}

* Get a job by ID:
GET http://localhost:8084/jobs/{id}

* Get all jobs:
GET http://localhost:8084/jobs

#### Company Service Routes (companyms)
* Create a company:
  POST http://localhost:8084/companies

* Update a company:
  PUT http://localhost:8084/companies/{id}

* Delete a company:
  DELETE http://localhost:8084/companies/{id}

* Get a company by ID:
  GET http://localhost:8084/companies/{id}

* Get all companies:
  GET http://localhost:8084/companies

#### Review Service Routes (reviewms)
* Create a review:
  POST http://localhost:8084/reviews?companyId={companyId}

* Update a review:
  PUT http://localhost:8084/reviews/{reviewId}

* Delete a review:
  DELETE http://localhost:8084/reviews/{reviewId}

* Get a review by ID:
  GET http://localhost:8084/reviews/{reviewId}

* Get all reviews of a company:
  GET http://localhost:8084/reviews?companyId={companyId}






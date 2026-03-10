# Event Management Backend API

## Overview

This project is a **backend REST API for an Event Management System** built using Spring Boot.
The system allows users to create events, book tickets, manage users, and process secure payments.

It follows a **layered architecture** consisting of controller, service, repository, entity, DTO, and configuration layers to maintain clean and scalable code.

The backend also integrates **Google OAuth authentication** and a **payment gateway API** to allow secure login and online event booking payments.

---

# Tech Stack

* Spring Boot
* Spring Data JPA
* Hibernate ORM
* MySQL Database
* Maven
* Embedded Tomcat Server
* REST API Architecture

---

# Key Features

* User registration and login
* Google OAuth login integration
* Event creation and management
* Event booking system
* Payment gateway API integration
* Secure REST APIs
* Layered backend architecture
* DTO-based data transfer
* Database integration using JPA
* CORS configuration for frontend connection

---

# Project Architecture

The backend follows a **layered architecture**:

Client (Frontend Application)
│
▼
Controller Layer
│
▼
Service Layer
│
▼
Repository Layer
│
▼
Database (MySQL)

### Layer Explanation

Controller
Handles incoming HTTP requests and returns responses.

Service
Contains business logic and processing.

Repository
Interacts with the database using Spring Data JPA.

Entity
Represents database tables using Java classes.

DTO
Transfers data between client and backend.

Config
Contains configuration such as CORS settings.

---

# Project Structure

src/main/java/com/eventmanagement

controller → REST API endpoints
dto → Data Transfer Objects
entity → Database entities
repository → Database operations
service → Business logic
config → Application configurations

---

# Authentication

The application supports **Google OAuth authentication** which allows users to securely log in using their Google account.

Flow:

User clicks "Login with Google"
Frontend sends request to backend
Backend verifies token using Google OAuth API
User account is created or logged in successfully

This improves security and simplifies login for users.

---

# Payment Gateway Integration

The system includes a **payment gateway API** to allow users to securely pay for event bookings.

Payment flow:

User selects an event
User proceeds to booking
Frontend sends payment request to backend
Backend creates a payment intent using the payment gateway API
Payment is processed securely
Booking is confirmed after successful payment

This ensures secure and reliable payment processing for event tickets.

---

# Example API Endpoints

### User APIs

POST /api/users/register
POST /api/users/login
POST /api/users/google-login
GET /api/users
GET /api/users/{id}

---

### Event APIs

GET /api/events
GET /api/events/{id}
POST /api/events
PUT /api/events/{id}
DELETE /api/events/{id}

---

### Booking APIs

POST /api/bookings
GET /api/bookings
GET /api/bookings/{id}

---

### Payment APIs

POST /api/payments/create-intent
POST /api/payments/confirm-payment

---

# Installation

Clone the repository

git clone https://github.com/ramesh3214/eventmanagement-backend.git

Move to the project directory

cd eventmanagement-backend

Build the project

mvn clean install

Run the application

mvn spring-boot:run

---

# Configuration

Update database configuration in **application.properties**

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/event_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

---
# Author

Ramesh

GitHub
https://github.com/ramesh3214

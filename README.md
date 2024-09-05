# Card Service

The **Card Service** is a microservice that handles card-related operations in the banking application. It manages issuing, blocking, unblocking, and retrieving cards for users.

### Table of Contents

* Features
* Technologies
* Architecture
* Endpoints
* Setup Instructions
* Usage

### Features

* Issue a new card to a user.
* Block or unblock a card.
* Retrieve cards associated with a user.
* Supports both debit and credit cards.
* Sets default credit limit and expiry date for new cards.

### Technologies

* Spring Boot (v3.1.0)
* Java (v17)
* Hibernate for ORM
* MySQL as the database
* Lombok for cleaner code
* Spring Data JPA

[//]: # (* JUnit for testing)

### Architecture

This service follows the **microservices** architecture and connects to other services like the User Service for managing cards related to a particular user.

### Entity

Card:

Attributes:
* id: Unique identifier for the card.
* cardNumber: Auto-generated card number.
* cardHolderName: Name of the cardholder.
* cardType: Enum (CREDIT or DEBIT).
* creditLimit: Applicable only for credit cards.
* availableLimit: Remaining available credit limit.
* expiryDate: Expiry date of the card.
* userId: Foreign key linking to the user.
* status: Enum for card status (e.g., ACTIVE, BLOCKED, EXPIRED).

### Endpoints

1. Issue a New Card <br>
      URL: /cards/issue <br>
      Method: POST <br>
      Request Body:

         {
         "cardHolderName": "John Doe",
         "cardType": "CREDIT",
         "creditLimit": 5000,
         "userId": 1
         }
   Response:

         {
         "id": 1,
         "cardNumber": "4123456789012345",
         "cardHolderName": "John Doe",
         "cardType": "CREDIT",
         "creditLimit": 5000,
         "availableLimit": 5000,
         "expiryDate": "2033-09-01",
         "userId": 1,
         "status": "ACTIVE"
         }
2. Block a Card <br>
      URL: /cards/block/{cardId} <br>
      Method: PUT <br>
      Path Variable: cardId (Long) <br>
      Response: 
HTTP 200 OK <br>
   Card blocked successfully

3. Unblock a Card<br>
   URL: /cards/unblock/{cardId}<br>
   Method: PUT<br>
   Path Variable: cardId (Long)<br>

   Response: HTTP 200 OK <br>
   Card unblocked successfully

4. Get Cards by User ID <br>
   URL: /cards/user/{userId} <br>
   Method: GET <br>
   Path Variable: userId (Long) <br>

   Response:

         [
         {
         "id": 1,
         "cardNumber": "4123456789012345",
         "cardHolderName": "John Doe",
         "cardType": "CREDIT",
         "creditLimit": 5000,
         "availableLimit": 3000,
         "expiryDate": "2033-09-01",
         "userId": 1,
         "status": "ACTIVE"
         }
         ]
### Setup Instructions
   #### Prerequisites
   * Java 17
   * Maven
   * MySQL
### Steps
   #### Clone the repository:

bash

      git clone https://github.com/hasratparwez9162/cards-service.git
      cd card-service
#### Update the application.properties with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/[Enter Your Database Name] <br>
username: [Enter username] <br>
password: [Enter password]

#### Update the application.properties with Eureka Configuration,JPA Properties and port:
spring.jpa.hibernate.ddl-auto=update <br>
spring.jpa.show-sql=true <br>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect <br>

server.port=8083 <br>

eureka.client.service-url.defaultZone=http://localhost:8761/eureka <br>

### Run the application

Start main method as per your IDE.

### Test the service using Postman or another API client.

### Usage
* Issuing a New Card <br>
* Make a POST request to /cards/issue with the cardholder’s details. <br>
* Ensure that the user ID exists in the User Service before issuing the card. <br>
* Blocking or Unblocking a Card <br>
* To block a card, use the /cards/block/{cardId} endpoint. <br>
* To unblock a card, use the /cards/unblock/{cardId} endpoint. <br>

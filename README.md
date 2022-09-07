# Nathan-Chris-P1 : A Reimbursement System API

## Description
An API for a reimbursement system.

## Usage
* Create a new user account by sending a POST request to http://3.140.254.10:8080/nathan-chris-p1/signup with the following JSON form: 
```json
{
  "username": "username",
  "email": "email@email.com",
  "password": "password",
  "given_name": "Given",
  "surname": "Surname",
  "role": "EMLOYEE / ADMIN / FINANCE_MANAGER"
}
```

* Log into the system and receive your Authentication token after an Admin has made your account Active by sending a POST request to http://3.140.254.10:8080/nathan-chris-p1/login with the following JSON form:
```json
{
    "username": "username",
    "password": "password"
}
```

* Get a list of users by sending a GET request to http://3.140.254.10:8080/nathan-chris-p1/admin with a valid Admin Authentication Token. Results can be filtered using the user parameter or the active parameter (or even both).<br/>

* Activate or deactivate a user by sending a PUT request to http://3.140.254.10:8080/nathan-chris-p1/admin with a valid Admin Authentication Token, the action parameter set to "activate" or "deactivate", and the following JSON form:
```json
{
    "username":"username"
}
```

* Reset a user's password by sending a PUT request to http://3.140.254.10:8080/nathan-chris-p1/admin with a valid Admin Authentication Token, the action parameter set to "password", and the following JSON form:
```json
{
    "username":"username",
    "password":"password"
}
```

* Delete a user account by sending a DELETE request to http://3.140.254.10:8080/nathan-chris-p1/admin with a valid Admin Authentication Token and the following JSON form:
```json
{
    "username":"username"
}
```

* Get a list of reimbursements by sending a GET request to http://3.140.254.10:8080/nathan-chris-p1/finance with a valid Finance Manager Authentication Token and the view parameter set to "pending" or "resolved". Results can be filtered using the type parameter.<br/>

* Approve or deny a reimbursement by sending a PUT request to http://3.140.254.10:8080/nathan-chris-p1/finance with a valid Finance Manager Authentication Token and the following JSON form:
```json

```

* Request a reimbursement by sending a POST request to http://3.140.254.10:8080/nathan-chris-p1/employee with a valid Employee Authentication Token and the following JSON form:
```json
{
    "amount":"####.##",
    "description":"Description.",
    "payment_id":"UUID to external payment account",
    "type":"LODGING / TRAVEL / FOOD / OTHER"
}
```

* View your reimbursement requests by sending a GET request to http://3.140.254.10:8080/nathan-chris-p1/employee with a valid Employee Authentication Token.
* 

## Brief

## User Story

## Roles
* Admin
* Finance Manager
* Employee

## Technologies
* Java 8 (Written in Intellij IDEA Community)
* PostgreSQL (Written in DBeaver, running using Docker)
* Apache Maven
* JUnit
* Mockito
* Lucid Chart

## ERD
<img src="src/main/resources/P0 ERD.png" alt="ERD" style="float: left; margin-right: 10px;" />
<br><br>

## Contributors
* Chris Waters
* Nathan Gilbert
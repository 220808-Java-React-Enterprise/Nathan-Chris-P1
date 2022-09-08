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
  "role": "EMPLOYEE / ADMIN / FINANCE_MANAGER"
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
{
    "reimb_id":"UUID of the reimbursement to update",
    "status":"APPROVED / DENIED"
}
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
* Update your pending reimbursement by sending a PUT request to http://3.140.254.10:8080/nathan-chris-p1/employee with a valid Employee Authentication Token and the following JSON form:
```json
{
    "reimb_id":"UUID of the reimbursement to update",
    "amount":"####.##",
    "description":"Description.",
    "payment_id":"UUID to external payment account",
    "type":"LODGING / TRAVEL / FOOD / OTHER"
}
```

* Delete your pending reimbursement by sending a DELETE request to http://3.140.254.10:8080/nathan-chris-p1/employee with a valid Employee Authentication Token and the following JSON form:
```json
{
    "reimb_id":"UUID of the reimbursement to delete"
}
```

## Brief
"For the foundations module of your training you are tasked with building an API that will support a new internal expense reimbursement system. This system will manage the process of reimbursing employees for expenses incurred while on company time. All registered employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement."

## User Story
As an Employee I want to view my pending reimbursement request so that I can verify the information or get the UUID to update it.

## Roles
* Admin
* Finance Manager
* Employee
  <img src="src/main/resources/Images/ERS Use Case Diagram.png" alt="ERD" style="float: left; margin-right: 10px;" />
  <br><br>

## Technologies
* Java 8 (Source Code)
* JDBC (Database Connection)
* Java EE Servlet (Web request mapping)
* JSON Web Token (Authentication Handling)
* Intellij IDEA Community (IDE)
* PostgreSQL (Database)
* DBeaver (Database Interaction)
* Tomcat (Backend Server)
* Jenkins (Real Time Deployment)
* AWS EC2 (Tomcat and PostgreSQL Hosting)
* Docker (Operations Environment)
* Apache Maven (Dependency Manager)
* JUnit (Testing)
* Mockito (Testing)
* Postman (Network Interaction)

## ERD
<img src="src/main/resources/Images/ERS Relational Model.png" alt="ERD" style="float: left; margin-right: 10px;" />
<br><br>

## Contributors
* Chris Waters
  * Coded bulk of Employee classes
  * Coded bulk of Admin classes
  * Set up Tomcat and Jenkins servers
  * Wrote the majority of Postman Scripts
  * Made this ReadMe
  * Miscellaneous Code
* Nathan Gilbert
  * Coded bulk of Finance Manager classes
  * Coded Network Exception System
  * Wrote SQL for structuring Database
  * Coded JUNIT Testing
  * Automated Postman Authentication Token handling
  * Cleaned and refactored code
  * Miscellaneous Code
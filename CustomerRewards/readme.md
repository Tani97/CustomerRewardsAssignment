Customer Rewards Project:
--------------------------
Problem Statement -
------------------
1. A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction. 
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). 
  
2. Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total. 


Implementation -
------------------
CustomerController.java has 2 api endpoints
1. http://localhost:8080/api/customers - POST endpoint which saves data into DB

sample structure of request and response :
Api -http://localhost:8080/api/customers
RequestBody -
-------------
{
  "customerId": 102,
  "customerName": "peter",
  "emailId": "peter@yopmail.com",
  "transactions": [
    {
      "amount": 200,
      "date": "2025-01-10"
    },
    {
      "amount": 180,
      "date": "2025-01-15"
    },
    {
      "amount": 50,
      "date": "2025-02-15"
    },
    {
      "amount": 150,
      "date": "2025-02-20"
    }
  ]
}


Response -
---------
{
    "customerId": 102,
    "customerName": "peter",
    "emailId": "peter@yopmail.com",
    "transactions": [
        {
            "transactionId": 7,
            "amount": 200.0,
            "date": "2025-01-10"
        },
        {
            "transactionId": 8,
            "amount": 180.0,
            "date": "2025-01-15"
        },
        {
            "transactionId": 9,
            "amount": 50.0,
            "date": "2025-02-15"
        },
        {
            "transactionId": 10,
            "amount": 150.0,
            "date": "2025-02-20"
        }
    ]
}


2. http://localhost:8080/api/customers/{customerId} - GET endpoint that fetches data from DB based on the customer ID

Sample structure of request and response :
http://localhost:8080/api/customers/102
Response if user detail is present on DB-
{
    "customerId": 102,
    "customerName": "peter",
    "emailId": "peter@yopmail.com",
    "totalPoints": 1070,
    "monthlyPoints": {
        "JANUARY,2025": 920,
        "FEBRUARY,2025": 150
    }
}

If there's no customer data found with mentioned Id then below error should be shown as response:
{
    "message": "Customer with ID: 999 not found",
    "status": 404,
    "timestamp": "2025-05-07T18:14:57.618879"
}
This is being handled by creating custom exception in the com.customerRewards.Exception package

Junit test class for testing the logic in customerService class is written here - /CustomerRewards/src/test/java/com/customerRewards/service/CustomerServiceTest.java

Database snapshots-
____________________

![image](https://github.com/user-attachments/assets/763c174f-925f-4f0b-8327-f5620d0e5de1)

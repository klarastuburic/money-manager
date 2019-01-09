# Money manager

A Java RESTful API for managing money between group of people

### How to run

-for building and executing:
```sh
mvn exec:java
```
-after building the user is asked to choose the mode in command line, possible modes:  
 &emsp;&emsp;W = web service  
 &emsp;&emsp;C = console client  
-web service:  
&emsp;&emsp;-starts a server on localhost port 8080  
&emsp;&emsp;-it's possible to test methods in browser  
&emsp;&emsp;-to test all CRUD methods it's possible to use REST Client in browser (i.e. YARC in Chrome)  
    
    | METHOD | PATH           | DESCRIPTION          |
    | ------ | -------------- | -------------------- |
    | GET    | /user/{userId} | get user by user id  | 
    | GET    | /user/all      | get all users        | 
    | PUT    | /user/add      | creates new user     | 
    | PUT    | /user/{userId} | updates user         | 
    | DELETE | /user/{userId} | deletes user         |
    | PUT    | /transaction   | creates transaction  |
    | PUT    | /profit        | creates profit       |
    | PUT    | /expense       | creates expense      |
    | GET    | /debts         | get all current debts| 
    
-console client:  
    &emsp;&emsp;-it's possible to enter the wanted action and necessary arguments:  
     &emsp;&emsp;&emsp;&emsp; T - transaction between to users  
     &emsp;&emsp;&emsp;&emsp; E - expanse between more than one user  
     &emsp;&emsp;&emsp;&emsp; P - profit between more than one user  
        
### How to test
-enter the command inside the working directory
```sh
mvn test
```

### JSON for User, Transaction, Expense and Profit
##### User : 
```sh
{  
  "userId":"1"
  "userName":"example",
  "emailAddress":"example@gmail.com"
} 
```
##### Transaction: : 

```sh
{  
   "fromUserId":"1",
   "toUserId":"2",
   "amount":"100"
} 
```

#### Expense:
```sh
{  
   "payerId":"1",
   "debtors":"2 3",
   "amount":"400"
}
```

#### Profit:
```sh
{  
   "debtorId":"1",
   "others":"2 3",
   "amount":"400"
}
```

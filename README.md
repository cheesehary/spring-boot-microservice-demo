# spring-boot-microservice-demo

APIs through microservices pattern using spring boot

## Description

### High-level integration design

![flow chart](https://user-images.githubusercontent.com/19163855/66541855-6edd9c00-eb7c-11e9-89e2-2964c49c3d32.png)

### Security mechanism

The api gateway calls auth server which checks authentication and authorization for users, then the gateway will either send back new tokens to users, reject their requests or proceed to call other resource servers such as the customer server and return data requested. I will implement the security mechanism in a week through this way:  
Day 1 & 2. Write swagger doc. Implement auth server which uses Roles (or add Groups if necessary) to manage privileges in other resources and exposes auth related apis for gateway.  
Day 3 & 4. Implement token mechanism using JWT for gateway and auth server. Write unit tests.  
Day 5. Write more tests including integration test and fix bugs discovered.  

## API Documentation

see swagger documentation.

**customer service**
```
/spring-boot-microservice-demo/customer-service/src/main/resources/swagger-yaml 
```
**api gateway**
```
/spring-boot-microservice-demo/api-gateway/src/main/resources/swagger-yaml 
```

## How to run

### 1. Download
 ```
 git clone git@github.com:cheesehary/spring-boot-microservice-demo.git
 ```
 
### 2. a) With Docker

simply run
 ```
 docker-compose up --build
 ```

### 2. b) Without Docker

### Dependencies

 1. JDK 8
	 [download from oracle](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
 2. Mysql5.7
	 [download from mysql](https://dev.mysql.com/downloads/mysql/5.7.html)
	 
### Setup

Import 3 projects as existing maven projects, then customize your database info in 
```
/spring-boot-microservice-demo/customer-service/src/main/resources/application.properties 
```

### Run

in each project, run
```
mvnw spring-boot:run
```

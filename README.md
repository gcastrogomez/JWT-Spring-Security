# JWT-Spring-Security
JWT-Spring-Security

Project to provide security to user registration and access. We added two roles, admin and user to allow different permissions. Employees are managed safely. 

## Running JWT-Spring-Security locally
It's a backend application built with maven and spring in which an API is exposed to protect user registration and authentication through JWT. You can start it with the following commands:


```
git clone https://github.com/gcastrogomez/JWT-Spring-Security
cd JWT-Spring-Security
mvn spring-boot:run
```

The application is pure backend, so we will have to consume the services through postman or a similar client. A user can be registered, with a password, through a JSON, authenticate it in the same way and a token will be obtained that will allow access to "/hello" or "/modifyEmployee".


## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`

```properties
spring.datasource.url= jdbc:mysql://localhost:3306/testdb?useSSL=false
spring.datasource.username= root
spring.datasource.password= 123456

spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto= update

# App Properties
bezkoder.app.jwtSecret= bezKoderSecretKey
bezkoder.app.jwtExpirationMs= 3600000
bezkoder.app.jwtRefreshExpirationMs= 86400000
```

## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_WORKER');
INSERT INTO roles(name) VALUES('ROLE_ANALYZE');
```

<API Example>

http://localhost:8080/api/auth/signup
{
    "email": "pinkesh.job2020@gmail.com",
    "password": "12312312",
    "username": "07422409093",
    "role": ["admin","mod"],
    "name":"Pinkesh Soni",
    "createdAt":"2021-09-17T11:48:06Z",
    "createdBy":"07422409093"
}

http://localhost:8080/api/auth/signin
{
    "username": "07422409093",
    "password": "12312312"
}

#docker

docker compose up --build




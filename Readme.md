# **User Management 3rd Party Service Rest Api**
User Management Service API Built on Spring Boot. It use as backend service for User Management Environment.
User Management Service is application that built for user management / 3rd party apps to manage all vendor and their employee in company area.
-------
### 1. Application Specification
| Requirement        | Version     |
|--------------------|-------------|
| Spring Boot        | >= v2.7.13   |
| JDK                | >= v17.0.10  |
| PostgreSQL Driver  | >= v13.13 |
| Apache Maven       | >= v3.9.6   |

[*More information*](http://https://spring.io/quickstart "*More information*")
-------

### 2. Database
- PostgreSQL v14

[*More Information*](https://www.postgresql.org/ "*More Information*")

------
### 3. Installation
```bash
# Install dependency
$ mvn clean install

# run spring boot
$ mvn spring-boot:run

```

------

### 4. Database Configuration
Go to into file src/main/resources/bootstrap.yml and then :
- change spring active profile :
>spring.active.profile=localricky

Go to into file repo utils-config-repo/user_management/user_management-localricky.yml
- change url, port, and database name :
>spring.datasource.url=jdbc:postgresql://localhost:5432/db_userman

- change username :
>spring.datasource.username=username

- change password :
>spring.datasource.password=password


-------

### 5. Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)
* [PostgreSQL documentation](https://www.postgresql.org/docs/14/index.html)
* [Constructor Based Injection - Medium](https://medium.com/@dulanjayasandaruwan1998/spring-doesnt-recommend-autowired-anymore-05fc05309dad)
* [Minio documentation](https://min.io/docs/minio/container/index.html)


-------

### 6. Rest API Documentation
* Run the application and then
[**Click here**](http://localhost:8080/swagger-ui/index.html "**Click here**")

-------

### 7. Contributors
- Ricky
- Anton
  
@TDI-2 PT. GMF-AeroAsia, Tbk.


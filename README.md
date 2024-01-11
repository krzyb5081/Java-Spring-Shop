# Online Shop
## About
This is Java Spring Boot REST API project using MySQL database, working with client-side [website](https://github.com/krzyb5081/Vue-Shop-Client).
I created this project to learn how to use Spring and Hibernate. I also wanted to learn about REST API and Test Driven Development.
Project is still in progress and I am learning new technologies.

## Technologies

- Hibernate
- JPA
- Spring
- Spring Data
- Spring Boot (v2.4.2)
- Spring Security
- Spring Session
- Jackson
- MySQL
- JUnit - in future

## Targets
- Shop have session and authorisation system
- Users get roles - customers and merchants (in progress)
- Merchants are able to manage customer orders and to add new items to shop
- Customers have money ballance and be able to see their orders details (in progress)
- Customers are able to chat with merchants (in progress)
- Unit tests (in progress)

## Launch on Eclipse (for web dev)
1. Clone repository https://github.com/krzyb5081/Java-Spring-Shop.git
2. Install lombok plugin (https://projectlombok.org/download)
3. Setup MySQL server and create database (you can use XAMPP for example - https://www.apachefriends.org)
4. Change database connection settings in Java-Spring-Shop/src/main/resources/[application.properties](src/main/resources/application.properties)
5. Edit spring.datasource.url=jdbc:mysql://[host]:[port]/[database name] - for example jdbc:mysql://localhost:3306/shop_db
6. Edit spring.datasource.username=[database username]
7. Edit spring.datasource.password=[database password]
8. Save changes
9. Select Java-Spring-Shop/src/main/java/com/shop/project/[ShopApplication.java](src/main/java/com/shop/project/ShopApplication.java) and run as Java Application -> Spring Boot Application


## 1. Main Logic

| Requirements | Implementation Method|
|-------------|---------------------------------------|
| Get a product’s total number of customer reviews <br /> whose ratings are within a given range (inclusive) | [**count**](https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/application/review/ReviewService.java#L81)  method in _ReviewService_ class|
| Create a customer review after performing these checks:<br />  (1) Check if Customer’s comment does not contain any of these curse words<br />  (2) Check if the rating is not out of range, mimimum rating > 0| [**save**](https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/application/review/ReviewService.java#L50)  method in _ReviewService_ class|

[For details see **ReviewService**](https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/application/review/ReviewService.java)

## 2. Screenshots


- [Review Management]( https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_04_Reviews.png )
- [Users Master Data](https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_03_Users.png)
- [Products Master Data](https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_02_Products.png)
- [Configuration - _rating range and restricted words_](https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_01_Configurations.png)

## 3. Execution

- Download the **customer-review.jar** in *dist* directory
- Run the following command:

```console
java -jar customer-review.jar
```
- Open the following URL in a browser:
http://localhost:8080/

This will execute the main class [*MainApplication*](https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/framework/MainApplication.java) in *customer-review.jar*. This an uber-jar including all dependencies including an embedded **H2 database server**, and **Tomcat web server**.
For convinience, application will initialize _5 users_ and _5 products_ test data on startup.

**Requirements**: Java Runtime Enviroment (JRE) version 8 or higher.

## 4. Compile

Run the following command:
```console
mvn clean install;
```
**Requirements**: Java Development Kit(JDK) version 8 or higher.

## 5. Framework

| Library | Version | Usage |  
|---------|---------|---------|
| Vaadin | 8.1.7 | Web Application UI |
| Spring | 4.3.13 | DI, Web application components, Service transaction |
| Hibernate  | 5.0.12 | ORM / JPA |

Built with spring-boot version 1.5.9





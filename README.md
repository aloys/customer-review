
### Main Logic

| Requirements | Implementation Method|
|-------------|---------------------------------------|
| Get a product’s total number of customer reviews <br /> whose ratings are within a given range (inclusive) | **count**  method in _ReviewService_ class|
| Create a customer review after performing these checks:<br />  (1) Check if Customer’s comment does not contain any of these curse words<br />  (2) Check if the rating is not out of range, mimimum rating > 0| **save**  method in _ReviewService_ class|

See: (https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/application/review/ReviewService.java)


### Execution

- Download the **customer-review.jar** in *dist* directory
- Run the following command:

```console
java -jar customer-review.jar
```
- Open this URL in the browser:
http://localhost:8080/

This will execute main class: customer.review.framework.MainApplication
(https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/framework/MainApplication.java)

That jar is an uber-jar including all dependencies with an embedded H2 database server, and tomcat web server.

**Requirements**: Java Runtime Enviroment (JRE) version 8 or higher. (For compiling a JDK is required)

### Compile

- Compile project
- Execute main class: customer.review.framework.MainApplication
(https://github.com/aloys/customer-review/tree/master/src/main/java/customer/review/framework)


#### Screenshots


- Review:( https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_04_Reviews.png )
- Users: (https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_03_Users.png)
- Products: (https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_02_Products.png)
- Configuration: (https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_01_Configurations.png)

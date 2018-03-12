
### Main Logic
See: customer.review.application.review.ReviewService class
(https://github.com/aloys/customer-review/blob/master/src/main/java/customer/review/application/review/ReviewService.java)

| Requirements | Implementation Method|
|-------------|-------------|
| Provide a way to get a product’s total number of customer reviews whose ratings are within a given range (inclusive) | **count**  method in _ReviewService_ class|
| Create a customer review after performing these checks: (1) Check if Customer’s comment does not contain any of these curse words (2) Check if the rating is not out of range, mimimum rating > 0| **save**  method in _ReviewService_ class|


#### Execution

- Compile project
- Execute main class: customer.review.framework.MainApplication
(https://github.com/aloys/customer-review/tree/master/src/main/java/customer/review/framework)


#### Screenshots


- Review:( https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_04_Reviews.png )
- Users: (https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_03_Users.png)
- Products: (https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_02_Products.png)
- Configuration: (https://github.com/aloys/customer-review/blob/master/doc/Screen_Shot_01_Configurations.png)

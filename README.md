# RestaurantBackend
This is a REST API Java Spring Boot project for the Restaurant Frontend [app](https://github.com/bilalsarac/RestaurantFrontend).

### Project Requirements
* All existing restaurants should be listed. The average of the rating scores should be in the list view.
* Each restaurant should have a page. The page should contain the name of the restaurant, its type(such as kebab shop, tavern, artisan restaurant, pizzeria), address, photograph and all comments made by users for that restaurant.
* The same user should be able to comment on the same restaurant only once and should be able to rate the same restaurant only once.
* The user should be able to rate three different kinds of quality of the restaurant such as service, taste and price. The rate scores should be in the range of 1-10.
* User should register to the system only once with the same e-mail address.
* User can define an unlimited number of restaurants.
* The user should be able to change their own scores as well as their own comments and should be able to delete them. Users should only delete restaurants that are defined by herself/himself.
* The application should be developed to work efficiently with a large number of restaurants, comments and ratings.
* It should include an authorization mechanism. For example, standard users should be able to comment, and give ratings. Promoted users should be able to add and edit restaurants. Only admins should be able to delete a restaurant.

### System Requirements

* JDK 19 is required to build and run the application.
* Maven must be installed and assigned PATH in the environment variables if the program is going to be run without using an IDE.

### Build The Application

This command will create a .jar extension file in our application's target directory.

app-0.0.1-SNAPSHOT.jar

`./mvnw package`

### Run The Application
Since the backend server uses a relational database such as MySQL or PostgreSQL which is not embedded to the application. Database configurations need to be adjusted from the src/main/resources/application.properties file. 

spring.datasource.url=CONNECTION_URL
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD

After that, this command will run the application. By default, the application will run at http://localhost:8080. From application.properties in resources, the port number can be changed.

`./mvnw spring-boot:run`

The application can be run from the jar file with this command as well.

`java -jar app-0.0.1-SNAPSHOT.jar`

### System Architecture

#### Config
In this layer, define which requests such as GET, POST, PUT, PATCH, and DELETE can be made by users and who and which kind of role such as admin, senior, user can access which endpoints with the specified requests. In this app, only the admin has the authority to delete restaurants, and only seniors have the authority to add and edit restaurants.

#### Controllers
The app provides five base endpoints which are /auth, /restaurants, /comments, /users and /ratings endpoints. Each endpoint branches within itself to fulfil the POST, GET, PUT, and DELETE requests.
##### Auth Controller
This controller provides /login and /register (POST) request options. Since all users should be able to log in and register no authorization is required.
##### Comment Controller
This controller provides /comments(GET, POST) endpoint to receive all comments and post a comment. Since one comment can belong to the user and restaurant, /comments/userId/restaurantId(POST, PUT, DELETE) endpoint is used for this purpose.
##### Rating Controller
This controller provides the same logic with comments. Since one rating can belong to the user and restaurant /ratings/userId/restaurantId(POST, PUT, DELETE) endpoint is used for this purpose, but this time /restaurantratings/restaurantId endpoint is used to receive the ratings for a restaurant.
##### Restaurant Controller
This controller provides /restaurants(GET) endpoint to get all restaurants,/restaurants/userId/restaurants(GET) endpoint to get all restaurants defined by the user, /restaurants/restaurantId(PUT, DELETE, GET) to update, delete and get one restaurant. Preauthorize annotation is used to distribute the authorities such as only seniors can edit and create restaurants.
##### User Controller
This controller provides /users(GET) endpoint to receive all the users, /users/userId(GET,PUT,POST,DELETE) endpoint to get one user, update the user, add the user and delete the user.
#### Repository
In this project, the JPA repository, which contains built-in elementary queries, was used to speed up the development process. In addition, native queries were used for extra features.
#### Services
The service layer is the layer that covers all the business logic of the application. This layer is responsible mostly for CRUD operations of the entities and converts entity objects to response objects. 
#### Request and Responses
Since there is data that should not be leaked in the API returns of users such as passwords, response objects were used in this project and entity objects were mapped to them, since we do not need to change the id fields when updating the data, request classes are used.
#### Authentication
JWT token-based authentication was used in this project.

#### ER Diagram
Users can have many comments, as well as restaurants can have many comments, but one comment only can be assigned to a restaurant and user. With the same logic, users can have many ratings, as well as restaurants, can have many ratings, but one rating only can be assigned to the restaurant and user.

![image](https://github.com/bilalsarac/RestaurantBackend/assets/80422331/7cc410df-db2c-40c9-9683-94117905703c)

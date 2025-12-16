##Project Overview

This project is a full-stack microblogging platform built using Java Spring Boot with a MySQL backend. The application allows users to create posts, interact with other users through likes, bookmarks, comments, and follows, and discover content via hashtag-based search.

The system follows a layered architecture consisting of:

Presentation layer (Mustache templates)

Controller layer (Spring MVC)

Service layer (business logic + JDBC)

Persistence layer (MySQL accessed via JDBC)

In addition to the required features, the project implements multiple non-trivial extensions, including a notification system, avatar selection and upload, and post deletion with authorization checks.


##Team Members & Contributions

Pushya Damania
-> Created ER models and recorded video demonstration

-> Designed and implemented notification system that alerts the user when another user follows,
comments, bookmarks, or likes their content. It is non-trivial as it integrates front-end UI, 
controller logic, and service-layer database operations. To access the feature click on the
notifications tab. After clicking the tab the user will see all the notifications they have
and they can choose to delete specific notifications or delete all of them.
 - UI: src/main/resources/templates/notifications_page.mustache
 -    UI: src/main/resources/templates/fragments/notifications_dropdown.mustache
 - Controller: src/main/java/uga/menik/csx370/controllers/NotificationController.java
 - Service: src/main/java/uga/menik/csx370/services/NotificationService.java
 -    Modified: src/main/java/uga/menik/csx370/services/UserService.java - getUserById() method
 -    Modified: Post, people, bookmark, comment services to call + create a notification on action
 - SQL: Add database operations related to creating and deleting notifications


Olin Cordell
-> Implemented Home Page Logic, add/remove heart functionality, and hashtag search.

-> Designed and implemented select avatar feature that allows users to
  select from 20 default avatars or upload their own avatar icon. It is non-trivial
  as it integrates front-end UI, controller logic, service-layer database operations,
  server-side file management into a smooth, interactive experience. To access this
  feature, simply click on the avatar icon located in the top bar field to the right
  of the hashtag search feature. Upon clicking the icon, the user will be redirected
  to the select avatar page.
  - UI: resources/templates/select-avatar.mustache
  - Controller: Controllers/ProfileImageController.java
  - Service: Services/ProfileImageService.java
  - SQL: database operations with profileImagePath in user field.
  - File Handling: uses Java's Path/Files API + MultipartFile


Sean McGrath
-> Implemented Comments Logic and Bookmarks fixes.

-> Implemented a remove post feature. There is a button on the profile screen which users
can use to remove their own posts.
  - UI: resources\templates\fragments\post.mustache
  - Controller: Controllers/PostController.java - deletePost() method.
  - Service: Services/PostService.java - deletePost() method.
  - SQL: Delete sql query only when postId and user match.
  - Model: models/ExpandedPost.java - IsOwnProfile() method, delete button only shows on your own profile.


Meghana Madduri
-> Created hashtag, hashtag_post, like_post, and bookmark tables in databse_setup.sql.  
Bookmark feature 
- Controller: src/main/java/uga/menik/csx370/controllers/PeopleController.java
- Service: src/main/java/uga/menik/csx370/service/BookmarkService.java
- Data insertions for all tables in database_setup.sql


Shafat Hasan
-> Implemented Logic work and people page and follow/unfollow feature with Last Active display also worked on some listing issues.
Debugging and code integration. 

- UI: resources/templates/people_page.mustache; resources/templates/fragments/followable_user.mustache
- Controller: src/main/java/uga/menik/csx370/controllers/PeopleController.java
- Service: src/main/java/uga/menik/csx370/services/PeopleService.java
- SQL: SELECT with LEFT JOIN on follows; INSERT/DELETE for follow/unfollow; uses user.lastActiveDate for display


##Third-Party Libraries

No external libraries beyond those provided in the starter code Maven dependencies were used, in compliance with project restrictions. Only:

* Java standard libraries

* Spring Boot starter dependencies

* JDBC / SQL APIs

##Database & JDBC Details

Database schema designed using ER modeling

Tables include:

* user, post, comment, follow

* hashtag, hashtag_post

* like_post, bookmark

* notification

All tables are created and populated in:

* database_setup.sql

All DML queries used by the application are documented in:

* dml.sql

Each query includes:

* Purpose

* Related application URL path

* Database access is handled using plain JDBC with prepared statements inside the service layer.

##How to Run the Project (Local)

1. Start MySQL Docker Container

Ensure MySQL is running and mapped correctly:

docker container inspect mysql-server --format '{{.NetworkSettings.Ports}}'


Expected mapping:

3306 -> 33306

2. Initialize Database (First Run Only)

From MySQL prompt:

SOURCE database_setup.sql;

3. Run the Application

Navigate to the directory containing pom.xml.

macOS / Linux

mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081'


Windows (Command Prompt)

mvn spring-boot:run -D"spring-boot.run.arguments=--server.port=8081"


Windows (PowerShell)

mvn spring-boot:run --% -Dspring-boot.run.arguments="--server.port=8081"

4. Access the App

Open a browser and navigate to:

http://localhost:8081/


Create an account and log in to begin using the platform.


##Additional Notes

Empty posts are not allowed.

Home page shows posts from followed users and the logged-in user.

Last active date is derived from most recent post; users with no posts display Unknown.

Time zone differences are acceptable as long as application behavior is consistent.

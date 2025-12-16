# HCI Insights — DBMS Term Project

**Team Members

Pushya Damania
-> Created and populated the database with realistic, large-scale data using automated SQL scripts. Authored documentation files: data.sql, ddl.sql, populate.sql, datasource.txt, security.txt, generate_data.inpyb.

Olin Cordell
-> Created Models, Controllers, Services and implemented UI/UX across the web service.

Sean McGrath
-> Added study filtering to Analytics page with dropdown selector, modified AnalyticsService queries to support study ID parameters, updated AnalyticsController to handle study selection, and designed the study dropdown UI on the analytics dashboard.

Meghana Madduri
-> created queries.sql, wrote down all the SQL queries from the services (filled in ?s w/ example values), their java classes and methods used in, purpose, and example URLs. Created perf.txt and made two indexes. 

Shafat Hasan
-> Wrote documentation such as the Read me file. Did smoke tests and worked on core flows. 

## Project Overview
- HCI Insights is a web application to manage Human–Computer Interaction study data: users, studies, participants, tasks, subtasks, sessions, and session results.
- Visitors can register and log in; passwords are hashed using BCrypt. The app supports browsing and CRUD operations for core entities.
- The dataset is synthetically generated for demonstration and testing.

## Technologies Used
- Backend: Java, Spring Boot
  - Web (REST + MVC)
  - JDBC (database access via `PreparedStatement`)
  - Mustache (server-side templating)
  - Spring Security Crypto (BCrypt password hashing)
- Database: MySQL
- Build: Maven
- Frontend: Server-rendered Mustache templates
- Data generation: Python (Faker), Jupyter Notebook (`generate_data.ipynb`)

## Third-Party Libraries (Key)
- Spring Boot Starter Web
- Spring Boot Starter Mustache
- Spring Boot Starter JDBC
- MySQL Connector/J
- Spring Boot Starter Test
- Spring Security Crypto (BCrypt)
- Python Faker (data generation)

## Database and JDBC Details
- Database name: `hci_insights`
- JDBC URL: `jdbc:mysql://localhost:33306/hci_insights`
- Database username: `root`
- Database password: `mysqlpass`
- Driver: `com.mysql.cj.jdbc.Driver`

## Security Overview
- Passwords are stored as BCrypt hashes (no plaintext).
- Authentication uses session scope; logout clears session.
- All SQL goes through `PreparedStatement` to mitigate injection.
- Registration enforces minimum 8-character passwords and repeat-match validation.

## Demo Data
- Schema DDL: `ddl.sql` (creates Users, Study, Participant, Task, Subtask, Session, Result)
- Small seed: `populate.sql` (quick testing)
- Full seed: `data.sql` (large synthetic dataset)
- Data source notes: `datasource.txt` (explains synthetic generation with Faker)

### Test Accounts (for demo)
Use any of the following sample users from the small seed:
- Username: `Alice` — Password: `Password3`
- Username: `Alex` — Password: `Password3`
- Username: `Bob` — Password: `Password3`

Note: All seeded users share the same demo password (hashed with BCrypt in SQL inserts). If you changed the seed, update the plaintext accordingly.

## How to Run (Local)
1. Start MySQL and create an empty database named `hci_insights`.
2. Apply schema: run `ddl.sql`.
3. Load seed data: run `populate.sql` (quick) or `data.sql` (large; takes time).
4. Configure `application.properties` (already set to `jdbc:mysql://localhost:33306/hci_insights`, user `root`, pass `mysqlpass`).
5. Build and run:
   - Maven: `mvn spring-boot:run` (from `hciinsights/`)
   - Or run the generated JAR: `java -jar target/hciinsights-1.0-SNAPSHOT.jar`
6. Open the app in the browser at `http://localhost:8081/`.

## Notes
- If the large `data.sql` load is slow, use `populate.sql` first to verify the app.
- Prepared statements and BCrypt hashing are in place; never store plaintext passwords.
- Update team member names and contribution lines before submission.

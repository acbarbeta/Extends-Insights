# Extends Insights - Personal Blog

Welcome to Extends Insights, a personal blog application developed in Java using the Spring Boot framework.
This project provides a platform where users can create, edit, view, and delete posts, as well as comments on their blog.  comments on their blog.
Users can also add tags and categories to their posts.

## 💻 Prerequisites

Before getting started, make sure you have the following requirements installed in your development environment:

- [Java](https://www.java.com/) - JDK 17 or higher
- [DBeaver](https://dbeaver.io/) or [PgAdmin](https://www.pgadmin.org/) - For database management
- [PostgreSQL](https://www.postgresql.org/) or [Docker](https://www.docker.com/) - For running the database
- [Postman](https://www.postman.com/) - For queries testing
- Ensure that you have a Windows, Linux, or macOS machine;
- Ensure that you have read the project documentation before running
  
## 💻 Technologies and Libraries used
- Java
- Maven
- Spring Boot
- PostgreSQL
- Docker
- Spring Data JPA
- Spring Security
- Spring devtools
- Spring Web
- Lombok
- Postman
- Swagger

## ⚙️ Environment Setup

1. **Database:**
   - You can choose to install PostgreSQL directly or use Docker. To use Docker, run the following command:

     ```bash
     docker-compose up -d db
     ```

   Make sure to adjust the password as needed.

2. **Application Configuration:**
   - Clone this repository:

     ```bash
     git clone https://github.com/acbarbeta/Extends-Insights.git
     ```

   - Open the project in your preferred IDE (e.g., Eclipse, IntelliJ, VSCode).

   - Configure the database properties in the `application.properties` file:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Running the Application:**
   - Run the application from the `ExtendsInsightsApplication` main class.

4. **Using the Application on Postman:**
   - Install and open Postman on your machine.
   - In your workspace, select the menu in the upper-right corner and go to File > Import.
   - Select the `postman` file contained in the `resources > docs` folder
   - Import the collection
   - You should then see all the queries available in our application
   - Ensure the "Authorization" tab is configured with the credentials you setted up in the `application.properties` file.

5. **Using the Application on Swagger:**
   - Open your browser and go to `http://localhost:8080/swagger-ui.html` to access the Swagger documentation.
   - Enter the username and password set up in the `application.properties` file:

     ```properties
     spring.security.user.name=user
     spring.security.user.password=12345
     ```

   - You should then see all the queries available in our application. 
   - You can test the queries directly from the Swagger documentation.

## 📚 Concepts learned
- Spring Boot Framework
- REST routes implementation with Spring Web
- DataBase manipulation
- Queries implementation (parameters, body, requests)
- Authentication implementation with Spring Security
- Swagger documentation

## 📁 Entities

The project has the following entities:

1. **User:**
   - User management with JWT for authorization and authentication.

2. **Publication:**
   - CRUD operations for creating, editing, viewing, and deleting posts.

3. **Comment:**
   - Management of comments associated with posts.

4. **Tag:**
   - Creation and management of tags to categorize posts.

5. **Category (Enum):**
   - Enumeration representing the available categories for posts.

## 🚩 Future Improvements

Extends Insights is currently in its first version. Looking ahead, several new features and improvements are planned, including:
- Front-end design and development
- Security improvement
- Likes Implementation: We plan to implement a 'Likes' feature. This will allow users to express their appreciation for posts and comments within the application.
- User Roles Implementation: This system will allow for the creation of distinct roles such as Admin, Editor, and Viewer, each with specific permissions and capabilities.
- Admin Dashboard Implementation: We plan to introduce an admin dashboard that provides insights into views, likes, and overall engagement.

## 💡📝 Contribution

Feel free to contribute to this project.
Open an issue to discuss new features or submit a pull request to fix existing issues.

##  🙋🏽‍♀️️ The Authores
Feel free to reach us on LinkedIn or GitHub! 
- Ana Clara Barbeta Vicente - [LinkedIn](https://www.linkedin.com/in/anaclara-barbeta/) profile and [Github](https://github.com/acbarbeta) page
- Ariane Padilha Oishi - [LinkedIn](https://www.linkedin.com/in/ariane-padilha-oishi/) profile and [Github](https://github.com/apoishi) page
- Elisa Maria Costa de Souza - [LinkedIn](https://www.linkedin.com/in/elisa-souzaa/) profile and [Github](https://github.com/ElisaSouzaaa) page
- Maynara Ellen Nachbar - [LinkedIn](https://www.linkedin.com/in/maynara-nachbar/) profile and [Github](https://github.com/MayNachbar) page
- Milena Lara Reis - [LinkedIn](https://www.linkedin.com/in/milenalarareis/) profile and [Github](https://github.com/milenalara) page


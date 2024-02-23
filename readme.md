# Extends Insights - Personal Blog

Welcome to Extends Insights, a personal blog application developed in Java using the Spring Boot framework.
This project provides a platform where users can create, edit, view, and delete posts and comments on their blog.
As well as adding tags and a category to your posts.

## 💻 Prerequisites

Before getting started, make sure you have the following requirements installed in your development environment:

- [Java](https://www.java.com/) - JDK 17 or higher
- [DBeaver](https://dbeaver.io/) or [PgAdmin](https://www.pgadmin.org/) - For database management
- [PostgreSQL](https://www.postgresql.org/) or [Docker](https://www.docker.com/) - For running the database

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

   - Open the project in your preferred IDE.

   - Configure the database properties in the `application.properties` file:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Running the Application:**
   - Run the application from the `ExtendsInsightsApplication` main class.

## 📚 Concepts learned

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

## 💡📝 Contribution

Feel free to contribute to this project.
Open an issue to discuss new features or submit a pull request to fix existing issues.

##  🙋🏽‍♀️️ The Authores
- Ana Clara Barbeta Vicente - [LinkedIn]() profile
- Ariane Padilha Oishi - [LinkedIn]() profile
- Elisa Maria Costa de Souza - [LinkedIn]() profile
- Maynara Ellen Nachbar - [LinkedIn](https://www.linkedin.com/in/maynara-nachbar/) profile
- Milena Lara Reis - [LinkedIn]() profile


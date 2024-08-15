Shop Project
A Java-based client-server application for managing a shop's inventory and user authentication. This project includes a backend server that handles item management and user authentication, along with a frontend built using JavaFX.

Table of Contents
Project Overview
Features
Installation
Usage
API Endpoints
Configuration
Technologies Used
Contributing
License

Project Overview
The Shop Project is a simple client-server application designed to manage shop inventory. 
The backend server is responsible for handling CRUD operations on items and user management, including login authentication.
The frontend is a JavaFX application that interacts with the server.

Features
User Management:
Create new users with roles (e.g., regular, moderator, admin).
Login authentication with password hashing.
Item Management:
Create, read, update, and delete items in the inventory.
Automatically handle stock levels.
Role-Based Access Control:
Different roles have different levels of access within the application.
Installation
Prerequisites
Java JDK 11 or higher
PostgreSQL: A running PostgreSQL instance with access credentials.
Maven (optional): For managing dependencies and building the project.
JavaFX SDK: For the frontend application.
Setup
Clone the Repository:

bash
Copy code
git clone https://github.com/yourusername/shop-project.git
cd shop-project
Set Up the Database:

Use the provided SQL scripts in the database/ directory to set up the required tables in your PostgreSQL instance.
Configure Environment Variables:

Set the following environment variables for database connectivity:
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
Build the Project:

If using Maven, you can build the project with:
bash
Copy code
mvn clean install
Run the Server:

Start the server by running the ServerApp class:
bash
Copy code
java -cp target/ShopProject-1.0-SNAPSHOT.jar com.github.jokrkr.shopproject.server.ServerApp
Run the Client:

Start the frontend by running the MainApp class in the frontend module:
bash
Copy code
java -cp target/ShopProject-1.0-SNAPSHOT.jar com.github.jokrkr.shopproject.ui.MainApp
Usage
Starting the Server
The server listens on http://localhost:8080. It handles API requests for managing users and items.

Interacting with the Client
The JavaFX client allows you to:

Login with a username and password.
Access different features based on your role.
Manage items in the inventory.
API Endpoints
User Management:
POST /users: Create a new user.
POST /login: Authenticate a user.
Item Management:
GET /items: Retrieve a list of items.
POST /items: Create a new item.
PUT /items: Update an existing item.
DELETE /items: Delete an item.
Configuration
Database: The database connection is configured via environment variables (DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD).
Logging: Logback is used for logging. You can configure the logging behavior via logback.xml located in the src/main/resources directory.
Technologies Used
Java: The core programming language.
JavaFX: For the frontend user interface.
PostgreSQL: The relational database used to store users and items.
SLF4J with Logback: For logging.
Gson: For parsing JSON data.
Apache Maven: For dependency management and building the project.
Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Make sure to follow the existing coding style and include tests for any new functionality.

License
This project is licensed under the MIT License. See the LICENSE file for more information.

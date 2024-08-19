# Shop Project

A Java-based client-server application for managing a shop's inventory and user authentication. This project includes a backend server that handles item management and user authentication, along with a frontend built using JavaFX.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Project Overview

The Shop Project is a simple client-server application designed to manage a shop inventory.
The backend server is responsible for handling CRUD operations on items, handle user management and interactions, as well as login authentication .
The frontend is a JavaFX application that interacts with the server.

## Features

- **User Management**: 
  - Create new users with roles (e.g., regular, moderator, admin).
  - Login authentication with password hashing.
- **Item Management**:
  - Create, read, update, and delete items in the inventory.
  - Automatically handle stock levels.
- **Role-Based Access Control**:
  - Different roles have different levels of access within the application.

## Installation

### Prerequisites

- **Java JDK 11 or higher**
- **PostgreSQL**: A running PostgreSQL instance with access credentials.
- **JavaFX SDK**: For the frontend application.

### Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/shop-project.git
   cd shop-project
   ```

2. **Set Up the Database**:
   - Run the provided SQL scripts in the `database/` directory to set up the required tables in your PostgreSQL instance.
     You can use a tool like pgAdmin or the psql command-line tool.

   ### Using pgAdmin:
   1. Open pgAdmin and connect to your PostgreSQL server.
   2. Select the target database where you want to create the tables.
   3. Open the Query Tool (`Tools > Query Tool`).
   4. Load the SQL script (e.g., `create_users_table.sql`) by clicking the folder icon or by copying and pasting the script content into the Query Tool.
   5. Execute the script by clicking the execute button (lightning bolt icon).
   
   ### Using psql Command-Line:
   1. Open your terminal.
   2. Connect to your PostgreSQL database:
      ```bash
      psql -U your_username -d your_database
      ```
   3. Run the SQL script:
      ```bash
      \i path/to/database/create_users_table.sql
      \i path/to/database/create_items_table.sql
      ```

   - Replace `your_username` and `your_database` with your actual PostgreSQL username and database name.
   - Ensure you are in the correct directory where the `.sql` files are located when using the psql command-line.

3. **Configure Environment Variables**:
   - Set the following environment variables for database connectivity:
     - `DATABASE_URL`
     - `DATABASE_USERNAME`
     - `DATABASE_PASSWORD`

4. **Build the Project**:
   - Compile the Java code using the following command (replace with your build method if different):
     ```bash
     javac -d out/production/Shop_Project $(find ./src -name "*.java")
     ```

5. **Run the Server**:
   - Start the server by running the `ServerApp` class:
     ```bash
     java -cp out/production/Shop_Project com.github.jokrkr.shopproject.server.ServerApp
     ```

6. **Run the Client**:
   - Start the frontend by running the `MainApp` class:
     ```bash
     java -cp out/production/Shop_Project com.github.jokrkr.shopproject.ui.MainApp
     ```

## Usage

### Starting the Server

The server listens on `http://localhost:8080`. It handles API requests for managing users and items.

### Interacting with the Client

The JavaFX client allows you to:
- Login with a username and password.
- Access different features based on your role.
- Manage items in the inventory.

## API Endpoints

- **User Management**:
  - `POST /users`: Create a new user.
  - `DELETE /user` : Delete a user
- **Item Management**:
  - `GET /items`: Retrieve a list of items.
  - `POST /items`: Create a new item.
  - `PUT /items`: Update an existing item.
  - `DELETE /items`: Delete an item.

## Configuration

- **Database**: The database connection is configured via environment variables (`DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`).
- **Logging**: Logback is used for logging. You can configure the logging behavior via `logback.xml` located in the `src/main/resources` directory.

## Technologies Used

- **Java**: The core programming language.
- **JavaFX**: For the frontend user interface.
- **PostgreSQL**: The relational database used to store users and items.
- **SLF4J with Logback**: For logging.
- **Gson**: For parsing JSON data.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

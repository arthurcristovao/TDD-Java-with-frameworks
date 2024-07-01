# FrameworksTrabFinal

## Status

_Work in progress._

## Environment Setup

Ensure you have atleast JDK 17 and Maven installed before proceeding with the setup.

1. Database Configuration: The application uses environment variables to configure the database connection by default. If not set, it falls back to the following settings:
   - **DATABASE_URL**: `jdbc:mysql://localhost:3306/frameworksTrab` (MySQL 8.3.x server with the `frameworksTrab` database already created)
   - **DATABASE_USERNAME**: `root` (Default user; should have appropriate permissions for Hibernate schema management)
   - **DATABASE_PASSWORD**: empty

_Note: These default values are suitable for a freshly installed MySQL server running locally. It is recommended
to change them._

## Build and Run Instructions

### Linux & macOS

1. Navigate to the directory where `pom.xml` is located using the terminal.
2. Execute the following command to build the package: `mvn clean package`
3. To run the built package, execute: `./target/taskapp.jar`

### Windows

Alternatively, you can use the provided script to automate building and running the application:

1. Navigate to the project directory in the Command Prompt or PowerShell.
2. Execute `build_and_run.cmd`. The script will handle building the package and running it automatically,
   utilizing environment variables from a `.env` file if present. Check out `.env.example` for what it should look like.

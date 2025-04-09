
<div style="text-align: center;">
  <img src="front/src/assets/logo_p6.png" alt="Texte alternatif" width="400"/>
</div>

# 🌐 Monde de Dév (MDD)

MDD (Monde de Dév) is a social network dedicated to developers.  
It promotes collaboration, article sharing, and professional networking opportunities.  
This project was developed for **ORION** as a **Minimum Viable Product (MVP)**.

---

## 📑 Table of Contents

- [🌐 Monde de Dév (MDD)](#-monde-de-dév-mdd)
- [✨ Main Features](#-main-features)
- [🛠️ Technology Stack](#-technology-stack)
- [🧠 Best Practices Implemented](#-best-practices-implemented)
- [⚙️ Project Installation](#-project-installation)
- [📚 API Routes](#-api-routes)
- [📝 Notes](#-notes)

---

## ✨ Main Features

- Secure authentication (registration, login, logout)
- Profile management and updates
- Subscribe/unsubscribe to programming-related topics
- Personalized feed based on user subscriptions
- Article publication and consultation
- Commenting on articles
- Fully responsive application (mobile, tablet, desktop)

## 🛠️ Technology Stack

### Backend

- **Framework**: Spring Boot 3.4.3
- **Security**: Spring Security with OAuth2 Resource Server
- **Database**: MySQL 8
- **Database ORM**: Spring Data JPA
- **Authentication**: JWT (JSON Web Tokens) with refresh token mechanism
- **API Documentation**: Springdoc OpenAPI (Swagger UI)
- **Dependency Management**: Maven
- **Environment Variables**: dotenv-java
- **Password Encryption**: BCrypt

### Frontend

- **Framework**: Angular 18
- **UI Components**: Angular Material
- **Language**: TypeScript
- **Styling**: SCSS (Sass)
- **Environment Configuration**: Angular Environments (`environment.ts` / `environment.prod.ts`)
- **HTTP Communication**: Angular HttpClient with JWT Interceptor
- **Responsive Design**: Material components and CSS Flex/Grid

---

## 🧠 Best Practices Implemented

- **SOLID Principles**  
  Followed object-oriented programming best practices to ensure maintainability, scalability, and flexibility.

- **Global Error Handling**  
  Centralized exception handling using `@ControllerAdvice` and custom exception classes.

- **Use of Lombok**  
  Lombok annotations (e.g., `@Getter`, `@Setter`, `@Builder`, etc.) are used to reduce boilerplate Java code.

- **DTO (Data Transfer Object) Usage**  
  DTOs are used to expose only necessary data externally, preventing direct entity exposure.

- **Strict Frontend/Backend Separation**  
  Backend (Spring Boot API) and Frontend (Angular application) are completely independent, communicating through RESTful APIs.

- **JWT Authentication with OAuth2ResourceServer**  
  Spring Security is configured using `oauth2ResourceServer` and a custom `AuthenticationEntryPoint` to handle unauthorized access.

- **Refresh Token with httpOnly Cookie**  
  JWT refresh tokens are managed securely using **httpOnly** cookies to prevent XSS attacks.

- **Environment Variables Management (.env)**  
  Sensitive configurations (JWT secrets, database credentials) are externalized using `.env` files and the `dotenv-java` library.

- **Password Hashing with BCrypt**  
  All user passwords are hashed securely using **BCrypt** before being stored in the database.

- **CORS Configuration**  
  Cross-Origin Resource Sharing (CORS) is properly configured on the backend to allow safe communication with the Angular frontend.

- **Angular HTTP Interceptor**  
  An HTTP interceptor automatically attaches the JWT token to outgoing requests, and handles 401/403 responses gracefully.

- **Swagger API Documentation**  
  The backend exposes auto-generated API documentation via **Springdoc OpenAPI** (`/api/swagger-ui`).

- **Validation of Request Bodies**  
  All incoming data is validated with **Jakarta Bean Validation** annotations like `@NotBlank`, `@Size`, `@Email`, etc.

- **Responsive Frontend (Angular Material)**  
  The frontend UI is fully responsive and built using Angular Material components.


## ⚙️ Project Installation

## Prerequisites

- **Node.js** >= 18
- **Angular CLI** >= 18
- **Java JDK** >= 21
- **Maven** >= 3.8
- **MySQL Server** >= 8
- **Git**

---

### 1. MySQL database

To set up the MySQL database required for this application, you need to:
1. Install MySQL on your system.
2. Create a database, user and password for this application.

For detailed instructions on how to configure MySQL, refer to the [MySQL Setup Guide](MYSQLREADME.md)

### 2. Clone the Repository

First, clone the project repository from GitHub to your local machine:

```
   git clone https://github.com/MatthieuSKRZYPCZAK/P06_MondeDeDev.git
```

Navigate to the project directory:

```
   cd P06_MondeDeDev
```

## Backend Installation

Go inside folder:

```
    cd back
```


### 1. Environment Configuration

The application requires an ```.env``` file to manage environment variables. Below is a detailed explanation of the variables you need to configure:

#### Application Configuration

* **APP_PORT** (optional): The port on which the application will run. Default: ```8080```.

#### Database Configuration

* **DB_USERNAME** (required): The username to conntect to the MySQL database.
* **DB_PASSWORD** (required): The password associated with the database user.
* **DB_NAME** (required): The name of the MySQL database.
* **DB_PORT** (optional): The port used by the MySQL database. Default: ```3306```.

#### Security Configuration

* **JWT_SECRET** (required): A Base64-encoded 256-bit key used for cryptographic operations to sign and verify JWT tokens.

#### Setting Up the ```.env``` File

```
   cp .env.example .env
```

Edit the ``` .env ``` file and set your own values for :
* Database connection details (```DB_USERNAME```, ```DB_PASSWORD```, ```DB_NAME```).
* Security Key (```JWT_SECRET```).

For testing purposes, you can use the following key in your ```.env``` file :
```txt
JWT_SECRET=cce37e45add6e72167eddddc7891504bdcb6a12ce4c9ea4f77160395f4a4ff1f
```



Navigate to the project directory and run the following command to compile the application and download all required dependencies:
```
  mvn clean install
```

### 2. Start the Application

```
  mvn spring-boot:run
```

### 3. Verify the Application

Once the application is running, you can access it at:

* **Base URL:** http://localhost:8080 (or the port configured in ```APP_PORT```).
* **Swagger UI:** http://localhost:8080/api/swagger-ui (for interactive API documentation).

### 4. Database Seeding

Upon application startup, the backend automatically seeds the MySQL database with initial test data.

This is achieved using a `data.sql` file located in the backend's `resources` folder.

The seed data includes:

- **Users**: pre-created accounts (with bcrypt-hashed passwords)
- **Posts**: several articles related to different programming topics (Java, JavaScript, Python, Angular, React, etc.)
- **Comments**: example comments on posts

This allows immediate testing of the application without manually inserting records.

**Important configuration points:**

- `spring.jpa.hibernate.ddl-auto=update`: ensures that tables are created automatically if they do not exist.
- `spring.sql.init.mode=always`: enables the automatic execution of SQL scripts like `data.sql`.
- `spring.jpa.defer-datasource-initialization=true`: ensures that Hibernate initializes the schema **before** executing the `data.sql` script.

**Default user accounts created:**

| Username | Email             | Password   |
|:---------|:------------------|:-----------|
| johnDoe  | john@example.com   | *bcrypt-hashed (password known by the dev)* |
| janeDoe  | jane@example.com   | *bcrypt-hashed* |
| adminUser| admin@example.com  | *bcrypt-hashed* |

> ⚡ These users have pre-hashed passwords stored using **BCrypt**.  
> ⚡ You can use the API (e.g., `/api/auth/login`) to authenticate using the known emails and associated test passwords.

If you need to modify the initial dataset, you can edit the `data.sql` file directly.

## Frontend Installation

After setting up and running the backend, you can now install and run the frontend (Angular application).
Go inside folder:

### 1. Navigate to the Frontend Directory

First, go back to the root project directory and enter the `front` folder:

```
    cd ..
    cd front
```

### 2. Install Angular Dependencies
```
    npm install
```

### 3. Environment Configuration

The Angular application uses the `src/environments` folder to manage different settings for development and production.

There are two environment files:

- `environment.ts` → used during development (`ng serve`)
- `environment.prod.ts` → used when building for production (`ng build --prod`)

Both files contain the base URL of the backend API:

```Typescript
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080/api'
};
```

### 4. Start the Angular Application

```
  ng serve
```

By default, the frontend will be available at:

URL: http://localhost:4200

⚡ Make sure the backend server is running at http://localhost:8080 before starting the frontend to avoid connection errors.


## 📚 API Routes

Below is a complete list of the available API routes for the **MDD (Monde de Dév)** application.  
For detailed information on request/response formats, refer to the **Swagger UI** or use **Postman** for testing.

---

### 🔑 Authentication Routes

| HTTP Method | Endpoint               | Description                              | Authentication Required |
|:------------|:------------------------|:-----------------------------------------|:-------------------------|
| POST        | `/api/auth/register`     | Register a new user                      | No                      |
| POST        | `/api/auth/login`        | Log in and obtain a JWT                  | No                      |
| GET         | `/api/auth/me`           | Retrieve authenticated user's information | Yes                   |
| POST        | `/api/auth/refresh`      | Refresh JWT token                        | Yes                     |

---

### 👤 User Management

| HTTP Method | Endpoint                        | Description                                | Authentication Required |
|:------------|:---------------------------------|:-------------------------------------------|:-------------------------|
| PUT         | `/api/user/update`               | Update authenticated user's profile       | Yes                     |
| PUT         | `/api/user/password`             | Update authenticated user's password      | Yes                     |
| POST        | `/api/user/subscribe/{topicName}` | Subscribe to a topic                      | Yes                     |
| POST        | `/api/user/unsubscribe/{topicName}` | Unsubscribe from a topic                | Yes                     |

---

### 📚 Topic Routes

| HTTP Method | Endpoint          | Description                        | Authentication Required |
|:------------|:-------------------|:-----------------------------------|:-------------------------|
| GET         | `/api/topics`       | Get list of available topics       | Yes                     |

---

### 📰 Post (Article) Management Routes

| HTTP Method | Endpoint                 | Description                             | Authentication Required |
|:------------|:--------------------------|:----------------------------------------|:-------------------------|
| GET         | `/api/posts/feed`          | Get user's personalized feed            | Yes                     |
| POST        | `/api/posts`               | Create a new post                       | Yes                     |
| GET         | `/api/posts/{postId}`       | Get details of a specific post          | Yes                     |

---

### 💬 Comment Management Routes

| HTTP Method | Endpoint                              | Description                   | Authentication Required |
|:------------|:---------------------------------------|:-------------------------------|:-------------------------|
| POST        | `/api/posts/{postId}/comment`          | Add a comment to a specific post | Yes                   |

---

### 📖 API Documentation (Swagger)

| HTTP Method | Endpoint                | Description         | Authentication Required |
|:------------|:-------------------------|:--------------------|:-------------------------|
| GET         | `/api/swagger-ui`         | Access Swagger UI   | No                      |


---

## 📝 Notes

- All protected routes require a valid **JWT** in the `Authorization` header:
  ```http
  Authorization: Bearer <your_token>
  ```
  
---

[🔝 Back to top](#-monde-de-dév-mdd)
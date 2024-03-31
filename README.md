
---

# JWT Spring Boot Application

This is a simple Spring Boot application demonstrating JSON Web Token (JWT) authentication.

## Requirements

- Java Development Kit (JDK) 8 or later(used record class for AuthDto for that java 17 required)
- Apache Maven
- MySQL (or any other supported database)

## Installation and Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/jwt-spring-boot.git
    cd jwt-spring-boot
    ```

2. **Build the application:**

    ```bash
    mvn clean install
    ```

3. **Database Configuration:**

    - Create a MySQL database.
    - Configure the database connection properties in `src/main/resources/application.properties`.

4. **Run the application:**

    ```bash
    java -jar target/jwt-spring-boot.jar
    ```

    Alternatively, you can run the application directly from your IDE by running the `JwtSpringBootApplication` class.

## Usage

1. **Obtain JWT Token:**

    Send a POST request to `/api/auth/login` with the following JSON payload:

    ```json
    {
        "username": "your-username",
        "password": "your-password"
    }
    ```

    This will return a JWT token if authentication is successful.

2. **Access Protected Resource:**

    Include the obtained JWT token in the Authorization header of subsequent requests to protected endpoints:
    access: homepage

    ```
    Authorization: Bearer <your-jwt-token>
    ```

## Endpoints

- **POST /api/auth/login**: Authenticates a user and returns a JWT token.
- **POST /api/auth/sign-up**: Register new user and JWT token , return error if user already exists.

## Configuration

- **JWT Secret**: The secret key used for JWT token generation and validation is defined in `src/main/resources/application.properties`. Make sure to use a strong, secure secret in production.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

---

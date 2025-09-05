# Spring Boot JWT Authentication Project

A complete JWT (JSON Web Token) authentication system built with Spring Boot, Spring Security, and PostgreSQL.

## Features

- ✅ JWT-based authentication
- ✅ User registration and login
- ✅ Role-based authorization (USER, ADMIN)
- ✅ Secure password encoding with BCrypt
- ✅ Stateless session management
- ✅ PostgreSQL database integration
- ✅ RESTful API endpoints
- ✅ Cross-origin resource sharing (CORS) support

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL database

## Database Setup

1. Create a PostgreSQL database named `jwt_pg`
2. Create a user `jwt_user` with password `jwtpass123`
3. Update `src/main/resources/application.yaml` with your database connection details if needed

## Running the Application

1. **Clone and navigate to the project:**
   ```bash
   cd spring-boot-jwt-token
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Public Endpoints (No Authentication Required)

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/public` - Public API endpoint

### Protected Endpoints (Authentication Required)

- `GET /api/auth/validate` - Validate JWT token
- `GET /api/secured` - Secured API endpoint

## Usage Examples

### 1. Register a New User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

Response will include a JWT token:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "message": "Login successful"
}
```

### 3. Access Protected Endpoint

```bash
curl -X GET http://localhost:8080/api/secured \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

### 4. Validate Token

```bash
curl -X GET http://localhost:8080/api/auth/validate \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## Default Users

The application automatically creates a default admin user:
- **Username:** `admin`
- **Password:** `admin123`
- **Role:** `ADMIN`

## Project Structure

```
src/main/java/com/auth/
├── config/
│   ├── SecurityConfig.java          # Spring Security configuration
│   ├── JwtAuthenticationFilter.java # JWT authentication filter
│   └── DataInitializer.java        # Data initialization
├── controller/
│   ├── AuthController.java          # Authentication endpoints
│   └── TestController.java          # Test endpoints
├── dto/
│   ├── AuthRequest.java             # Login/Register request DTO
│   └── AuthResponse.java            # Authentication response DTO
├── model/
│   ├── User.java                    # User entity
│   └── Role.java                    # Role entity
├── repository/
│   ├── UserRepository.java          # User data access
│   └── RoleRepository.java          # Role data access
└── service/
    ├── JwtService.java              # JWT utilities
    └── UserService.java             # User business logic
```

## Configuration

### JWT Configuration (`application.yaml`)

```yaml
jwt:
  secret: mySecretKey123456    # Change this in production!
  expiration: 3600000          # 1 hour in milliseconds
```

### Database Configuration

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jwt_pg
    username: jwt_user
    password: jwtpass123
```

## Security Features

- **Password Encryption:** BCrypt password hashing
- **JWT Security:** HMAC-SHA256 signature algorithm
- **Stateless:** No server-side session storage
- **CORS:** Cross-origin requests support
- **Role-based Access:** Fine-grained authorization

## Production Considerations

1. **Change JWT Secret:** Use a strong, random secret key
2. **HTTPS:** Always use HTTPS in production
3. **Token Expiration:** Adjust token expiration based on security requirements
4. **Database Security:** Use strong database passwords and network security
5. **Logging:** Implement proper logging for security events

## Troubleshooting

### Common Issues

1. **Database Connection Error:**
   - Verify PostgreSQL is running
   - Check database credentials in `application.yaml`
   - Ensure database and user exist

2. **JWT Token Invalid:**
   - Check token expiration
   - Verify token format (Bearer + space + token)
   - Ensure JWT secret matches

3. **Authentication Failed:**
   - Verify username/password
   - Check if user exists and is enabled
   - Ensure proper role assignment

## Testing

Run the test suite:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

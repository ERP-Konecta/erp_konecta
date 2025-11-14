# Testing Guide - Authentication System

## Prerequisites

1. **PostgreSQL Database**: Make sure PostgreSQL is running on `localhost:5432`

   - Database name: `gp`
   - Username: `postgres`
   - Password: `123`
   - (Update these in `application.properties` if different)

2. **Java 21**: Ensure Java 21 is installed

## Starting the Application

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Using Maven Wrapper (Windows)

```bash
./mvnw.cmd spring-boot:run
```

### Option 3: Using Maven Wrapper (Linux/Mac)

```bash
./mvnw spring-boot:run
```

The application will start on **http://localhost:8080**

## Available Endpoints

### 1. Register User

- **URL**: `POST /api/v1/auth/register`
- **Authentication**: Not required
- **Request Body**:

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "role": "ADMIN"
}
```

**Available Roles**: `ADMIN`, `DOCTOR`, `STUDENT`

### 2. Login

- **URL**: `POST /api/v1/auth/login`
- **Authentication**: Not required
- **Request Body**:

```json
{
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```

**Response**:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "statusCode": "ACCEPTED",
  "message": "login successful"
}
```

### 3. Approve User Registration (Admin Only)

- **URL**: `PUT /api/v1/auth/{userId}/approve`
- **Authentication**: Required (Bearer Token with ADMIN role)
- **Headers**: `Authorization: Bearer {token}`

### 4. Reject User Registration (Admin Only)

- **URL**: `PUT /api/v1/auth/{userId}/reject`
- **Authentication**: Required (Bearer Token with ADMIN role)
- **Headers**: `Authorization: Bearer {token}`

### 5. Change Password

- **URL**: `PUT /api/v1/auth/{userId}/change-password`
- **Authentication**: Required (Bearer Token)
- **Request Body**:

```json
{
  "oldPassword": "SecurePass123!",
  "newPassword": "NewSecurePass456!"
}
```

## Testing Methods

### Method 1: Using Swagger UI (Recommended)

1. Start the application
2. Open your browser and navigate to:
   ```
   http://localhost:8080/swagger-ui.html
   ```
3. You'll see all available endpoints with interactive forms
4. Click "Try it out" on any endpoint
5. Fill in the request body and click "Execute"
6. For protected endpoints, click the "Authorize" button (lock icon) and enter: `Bearer {your_token}`

### Method 2: Using Postman

1. **Register a User**:

   - Method: `POST`
   - URL: `http://localhost:8080/api/v1/auth/register`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):

   ```json
   {
     "username": "admin_user",
     "email": "admin@example.com",
     "password": "AdminPass123!",
     "role": "ADMIN"
   }
   ```

2. **Login**:

   - Method: `POST`
   - URL: `http://localhost:8080/api/v1/auth/login`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):

   ```json
   {
     "email": "admin@example.com",
     "password": "AdminPass123!"
   }
   ```

   - Copy the `token` from the response

3. **Test Protected Endpoint** (e.g., Approve User):
   - Method: `PUT`
   - URL: `http://localhost:8080/api/v1/auth/1/approve`
   - Headers:
     - `Content-Type: application/json`
     - `Authorization: Bearer {your_token_here}`
   - Body: Leave empty

### Method 3: Using cURL

#### Register User

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "email": "test@example.com",
    "password": "TestPass123!",
    "role": "STUDENT"
  }'
```

#### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "TestPass123!"
  }'
```

#### Test Protected Endpoint (with token)

```bash
curl -X PUT http://localhost:8080/api/v1/auth/1/approve \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Password Requirements

Password must meet these criteria:

- At least 8 characters long
- Contains at least one uppercase letter
- Contains at least one lowercase letter
- Contains at least one digit
- Contains at least one special character (@$!%\*?&)

**Valid Examples**:

- `SecurePass123!`
- `MyP@ssw0rd`
- `Test123!@#`

**Invalid Examples**:

- `password` (no uppercase, digit, or special char)
- `PASSWORD123` (no lowercase or special char)
- `Pass123` (less than 8 characters)

## Testing Scenarios

### Scenario 1: Complete User Registration and Login Flow

1. **Register an ADMIN user**:

```json
POST /api/v1/auth/register
{
  "username": "admin",
  "email": "admin@test.com",
  "password": "AdminPass123!",
  "role": "ADMIN"
}
```

2. **Login with the admin credentials**:

```json
POST /api/v1/auth/login
{
  "email": "admin@test.com",
  "password": "AdminPass123!"
}
```

3. **Register a STUDENT user**:

```json
POST /api/v1/auth/register
{
  "username": "student",
  "email": "student@test.com",
  "password": "StudentPass123!",
  "role": "STUDENT"
}
```

4. **Approve the student** (using admin token):

```bash
PUT /api/v1/auth/{studentId}/approve
Headers: Authorization: Bearer {admin_token}
```

5. **Login as student**:

```json
POST /api/v1/auth/login
{
  "email": "student@test.com",
  "password": "StudentPass123!"
}
```

### Scenario 2: Testing Password Change

1. Login and get token
2. Change password:

```json
PUT /api/v1/auth/{userId}/change-password
Headers: Authorization: Bearer {token}
{
  "oldPassword": "OldPass123!",
  "newPassword": "NewPass456!"
}
```

3. Try logging in with new password

### Scenario 3: Testing Error Cases

1. **Invalid email format**:

```json
{
  "username": "test",
  "email": "invalid-email",
  "password": "TestPass123!",
  "role": "STUDENT"
}
```

Expected: Validation error

2. **Weak password**:

```json
{
  "username": "test",
  "email": "test@example.com",
  "password": "weak",
  "role": "STUDENT"
}
```

Expected: InvalidPasswordException

3. **Duplicate email**:
   Try registering the same email twice
   Expected: UserAlreadyExistException

4. **Login with wrong password**:
   Expected: 401 Unauthorized

5. **Access protected endpoint without token**:
   Expected: 403 Forbidden

## Database Verification

You can check the database to verify user creation:

```sql
SELECT * FROM users;
```

The users table should have columns:

- `id`
- `username`
- `email`
- `password` (encrypted)
- `role`
- `status`
- `photo_path`
- `photo_filename`

## Troubleshooting

### Application won't start

- Check PostgreSQL is running
- Verify database credentials in `application.properties`
- Check port 8080 is not in use

### Getting 401/403 errors

- Verify token is correctly formatted: `Bearer {token}`
- Check token hasn't expired (tokens expire after 7 days)
- Ensure user status is `ACCEPTED` (not `PENDING` or `REJECTED`)

### Validation errors

- Check request body matches the DTO structure
- Verify email format is correct
- Ensure password meets requirements
- Check role is one of: ADMIN, DOCTOR, STUDENT

### Swagger UI not loading

- Verify SpringDoc dependency is in `pom.xml`
- Check application.properties has Swagger config
- Try accessing: `http://localhost:8080/v3/api-docs` directly

## Quick Test Script

Here's a quick sequence to test everything:

```bash
# 1. Register admin
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","email":"admin@test.com","password":"Admin123!","role":"ADMIN"}'

# 2. Login and save token
TOKEN=$(curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@test.com","password":"Admin123!"}' \
  | grep -o '"token":"[^"]*' | cut -d'"' -f4)

echo "Token: $TOKEN"

# 3. Test protected endpoint
curl -X PUT http://localhost:8080/api/v1/auth/1/approve \
  -H "Authorization: Bearer $TOKEN"
```

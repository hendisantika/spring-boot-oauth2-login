# Spring Boot OAuth2 Login Demo

This project demonstrates how to implement OAuth2 authentication in a Spring Boot application using custom login pages
with Thymeleaf (Java Template Engine). It includes OAuth2 login with Google.

## Features

- Custom login page using Thymeleaf
- OAuth2 authentication with Google
- Protected dashboard page
- Secure logout functionality
- CSRF protection

## Prerequisites

- Java 17 or later
- Maven 3.9.9
- Google Cloud account (for Google OAuth)

## Quick Start

1. Clone the repository

```bash
git clone https://github.com/hendisantika/spring-boot-oauth2-login.git
cd spring-boot-oauth2-login
```

2. Configure OAuth credentials (see OAuth2 Setup sections below)

3. Set environment variables

```bash
export GOOGLE_CLIENT_ID=your_google_client_id
export GOOGLE_CLIENT_SECRET=your_google_client_secret
```

4. Run the application

```bash
mvn clean spring-boot:run
```

5. Visit http://localhost:8080

## Google OAuth2 Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)

2. Create a new project or select an existing one

3. Configure the OAuth consent screen:
   - Go to "APIs & Services" > "OAuth consent screen"
   - Choose "External" user type
   - Fill in required information:
      - App name
      - User support email
      - Developer contact information
   - Add scopes: email, profile, openid
   - Add test users if using external user type

4. Create OAuth2 credentials:
   - Go to "APIs & Services" > "Credentials"
   - Click "Create Credentials" > "OAuth client ID"
   - Choose "Web application"
   - Add these URLs:
     ```
     Authorized JavaScript origins:
     http://localhost:8080

     Authorized redirect URIs:
     http://localhost:8080/login/oauth2/code/google
     ```
   - Note your client ID and client secret

## Configuration

Create or update `application.yml`:

```yaml
spring:
   security:
      oauth2:
         client:
            registration:
               google:
                  client-id: ${GOOGLE_CLIENT_ID}
                  client-secret: ${GOOGLE_CLIENT_SECRET}
                  scope:
                     - email
                     - profile
               github:
                  client-id: ${GITHUB_CLIENT_ID}
                  client-secret: ${GITHUB_CLIENT_SECRET}
                  scope:
                     - user:email
                     - read:user

```

## Project Structure

```
src/
  main/
    java/
      id.my.hendisantika.config/
          SecurityConfig.java    # Spring Security configuration
          LoginController.java   # Login handling
          DashboardController.java # Dashboard pages
    resources/
      application.yml           # Application configuration
    jte/
      layout/
        default.jte            # Base template
      pages/
        login.jte             # Login page
        dashboard.jte         # Dashboard page
        home.jte              # Home page
```

## Troubleshooting

### OAuth2 Issues

1. Redirect URI Mismatch
   - Verify the exact URIs in your OAuth provider settings
   - For Google: `http://localhost:8080/login/oauth2/code/google`
   - For GitHub: `http://localhost:8080/login/oauth2/code/github`
   - No trailing slashes
   - Correct protocol (http/https)
   - Correct port number
   - http://localhost:8080/api/auth/callback
   - Check the response
      ```json
      // 20250319062027
      // http://localhost:8080/api/auth/callback
      
      {
      "status": "success",
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZW5kaXNhbnRpa2FAZ21haWwuY29tIiwiaWF0IjoxNzQyMzQwMDI2LCJleHAiOjE3NDIzNzYwMjZ9.9aGBdImCTm53Tl4xbUP8F4W3aZczSHCAFi8k0jG8dr4"
      }
      ```

2. Authentication Errors
   - Clear browser cookies and cache
   - Check environment variables are set correctly
   - Verify OAuth provider console settings
   - Check application logs for detailed error messages

### Common Issues

1. Login Page Not Loading
   - Verify JTE configuration
   - Check template paths
   - Clear browser cache

2. Authentication Not Working
   - Verify default user credentials
   - Check OAuth configuration
   - Ensure CSRF token is present in forms

## Security Considerations

1. Production Deployment
   - Use HTTPS
   - Update OAuth redirect URIs for production domain
   - Secure client secrets
   - Enable CSRF protection
   - Consider session management settings

2. OAuth Provider Setup
   - Restrict OAuth scopes to minimum required
   - Verify redirect URIs
   - Protect client secrets
   - Use environment variables

### Image Screenshots

Login Page

![Login Page](img/login.png "Login Page")

Check Account Page

![Check Account Page](img/check-account.png "Check Account Page")

Dashboard Page

![Dashboard Page](img/dashboard.png "Dashboard Page")

Logout Page

![Logout Page](img/logout.png "Logout Page")

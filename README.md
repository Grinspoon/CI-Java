# CI-Java

## Project description

#### Assignment Part 2

Test login functionality for https://www.saucedemo.com

- Test 1: Validate successful login
- Test 2: Validate successful logout
- Test 3: Validate unsuccessful login

*File path: JavaScript/login-test.js*

#### Assignment Part 3

Test GET request and API response for https://fakestoreapi.com/products.

- Test 1: Validate GET Response status
- Test 2: Validate the number of total products and compare with the mock data and API response
- Test 3: Validate number of fields in ID 15 and compare with the mock data and API response
- Test 4: Validate specific field data (Title, Price and Category) from ID 15 and compare with the mock data and API response
- Test 5: Validate the whole object for ID 15 and compare with the mock data and API response

*File path: JavaScript/get-request-test.js*

## GitHub Actions

- Check the log of the latest push in the "Run Selenium tests"-build step: https://github.com/Grinspoon/CI-Java/actions
- Part 2: All tests should pass
- Part 3: All tests should pass

## How to install and run the project locally

1. Check your Chrome version and download corresponding chromedriver: https://googlechromelabs.github.io/chrome-for-testing/#stable
2. Add the chromedriver to your respective operating system
3. Create Java boilerplate from: https://start.spring.io and use configuration:
    - Project: Maven
    - Language: Java
    - Spring Boot: 4.0.3
    - Group: ContinuousIntegration
    - Artifact: Selenium
    - Package name: Selenium
    - Packaging: Jar
    - Configuration: YAML
    - Java: 17
4. Create a new Maven project from the boilerplate
5. Use project pom.xml and then update the project to get the dependencies https://github.com/Grinspoon/CI-Java/blob/main/pom.xml
6. Run tests:
    - LoginTest.java
    - GetRequestTest.java

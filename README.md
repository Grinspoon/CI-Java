# CI-Java

## Project description

#### Assignment Part 2

Test login functionality for https://www.saucedemo.com

- Test 1: Validate successful login
- Test 2: Validate successful logout
- Test 3: Validate unsuccessful login

*File path: src/test/java/ContinuousIntegration/Selenium/LoginTest.java*

#### Assignment Part 3

Test GET request and API response for https://fakestoreapi.com/products.

- Test 1: Validate GET Response status
- Test 2: Validate the number of total products and compare with the mock data and API response
- Test 3: Validate number of fields in ID 15 and compare with the mock data and API response
- Test 4: Validate specific field data (Title, Price and Category) from ID 15 and compare with the mock data and API response
- Test 5: Validate the whole object for ID 15 and compare with the mock data and API response

*File path: src/test/java/ContinuousIntegration/Selenium/GetRequestTest.java*

## GitHub Actions

- Check the log of the latest push in the "Java Build & Test" section: https://github.com/Grinspoon/CI-Java/actions
- Part 2: All tests should pass
- Part 3: All tests should pass

## How to install and run the project locally

1. Check your Chrome version and download corresponding chromedriver: https://googlechromelabs.github.io/chrome-for-testing/#stable
2. Add the chromedriver to your respective operating system
3. Update the project to get the pom.xml dependencies
4. Run the tests and observe output:
    - LoginTest.java
    - GetRequestTest.java

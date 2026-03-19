package ContinuousIntegration.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com");
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    void testSuccessfulLoginAndLogout() throws InterruptedException {
        // Setup credentials for successful login
        String passedUsername = "visual_user";
        String passedPassword = "secret_sauce";

        // Automate successful login
        WebElement usernameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        usernameField.sendKeys(passedUsername);
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordField.sendKeys(passedPassword);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();
        WebElement inventoryHeader = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
        Thread.sleep(1000);

        // Validate successful login
        String inventoryHeaderText = inventoryHeader.getText();
        assertEquals("Products", inventoryHeaderText, "Expected to land on Products page after login");
        assertTrue(inventoryHeader.isDisplayed(), "Inventory header should be displayed after login");
        System.out.println("- Passed: Should login successfully with correct credentials");


        // Automate logout
        WebElement menuButton = driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]"));
        menuButton.click();
        Thread.sleep(1000);
        WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]"));
        logoutButton.click();
        WebElement loginButtonElement = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));

        // Validate successful logout
        assertTrue(loginButtonElement.isDisplayed(), "Login button should be displayed after logout");
        String loginButtonText = loginButtonElement.getAttribute("value");
        assertEquals("Login", loginButtonText, "Login button text should be 'Login' after logout");
        System.out.println("- Passed: Should logout successfully");
    }

    @Test
    void testFailedLogin() throws InterruptedException {
        // Setup credentials for failed login
        String failedUsername = "wrong_user";
        String failedPassword = "no_secret_sauce";

        // Automate failed login
        WebElement usernameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        usernameField.sendKeys(failedUsername);
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordField.sendKeys(failedPassword);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();
        Thread.sleep(1000);

        // Validate unsuccessful login
        WebElement errorMessage = driver.findElement(By.xpath("//*[@data-test=\"error\"]"));
        String errorMessageText = errorMessage.getText();
        assertEquals("Epic sadface: Username and password do not match any user in this service", errorMessageText, "Error message on failed login not asserted.");

        WebElement errorContainer = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]"));
        assertTrue(errorContainer.isDisplayed(), "Error container should be displayed after failed login");
        System.out.println("- Passed: Should show error on failed login attempt with wrong credentials");
    }
}
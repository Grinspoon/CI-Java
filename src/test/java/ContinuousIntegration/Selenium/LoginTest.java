package ContinuousIntegration.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    void login() throws InterruptedException {
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
        String inventoryHeaderText = inventoryHeader.getText();
        Thread.sleep(1000);

        // Validate successful login
        if (!"Products".equals(inventoryHeaderText)) {
            throw new AssertionError("Expected 'Products' but got '" + inventoryHeaderText + "'");
        }

        if (inventoryHeader.isDisplayed()) {
            System.out.println("- Login successful: Passed");
        } else {
            System.out.println("- Login successful: Failed");
        }

        Thread.sleep(1000);

        // Automate logout
        WebElement menuButton = driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]"));
        menuButton.click();

        Thread.sleep(1000);

        WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]"));
        logoutButton.click();

        WebElement loginButtonElement = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));

        if (loginButtonElement.isDisplayed()) {
            System.out.println("- Logout successful: Passed");
        } else {
            System.out.println("- Logout successful: Failed");
        }

        String loginButtonText = loginButtonElement.getAttribute("value");
        if (!"Login".equals(loginButtonText)) {
            throw new AssertionError("Expected 'Login' but got '" + loginButtonText + "'");
        }
        // Setup credentials for failed login
        String failedUsername = "wrong_user";
        String failedPassword = "no_secret_sauce";

        // Automate failed login
        usernameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        usernameField.sendKeys(failedUsername);

        passwordField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordField.sendKeys(failedPassword);

        loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();

        Thread.sleep(1000);

        WebElement errorMessage = driver.findElement(By.xpath("//*[@data-test=\"error\"]"));
        String errorMessageText = errorMessage.getText();

        // Validate failed login
        if (!errorMessageText.equals("Epic sadface: Username and password do not match any user in this service")) {
            throw new AssertionError("Error message on failed login not asserted.");
        }

        WebElement errorContainer = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]"));

        if (errorContainer.isDisplayed()) {
            System.out.println("- Login unsuccessful: Passed");
        } else {
            System.out.println("- Login unsuccessful: Failed");
        }
    }
}
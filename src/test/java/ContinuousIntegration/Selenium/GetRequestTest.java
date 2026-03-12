package ContinuousIntegration.Selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GetRequestTest {
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
        driver.manage().window().maximize();
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    void testGetRequestResponseCode200() {
        driver.get("https://fakestoreapi.com");

        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest
                    .newBuilder(java.net.URI.create("https://fakestoreapi.com/products"))
                    .GET()
                    .build();
            java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            Assertions.assertEquals(200, statusCode, "Expected status response to be 200");
            System.out.println("- Passed: Got a 200 status response");
        } catch (Exception e) {
            Assertions.fail("Request failed with status response: " + e.getMessage());
            System.out.println("- Failed: 200 status response not valid, instead got: " +  e.getMessage());
        }

        String pageSource = driver.getPageSource();

        // Simple page load validation with Selenium
        assert pageSource != null;
        Assertions.assertFalse(pageSource.isEmpty(), "Page did not load content.");
    }
}

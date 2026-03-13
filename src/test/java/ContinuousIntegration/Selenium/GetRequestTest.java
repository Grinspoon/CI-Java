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
        // Run headless Chrome instance
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
    void testGetRequest() {

        try {
            // GET Request
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest
                    .newBuilder(java.net.URI.create("https://fakestoreapi.com/products"))
                    .GET()
                    .build();
            java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            // Validate GET Response
            Assertions.assertEquals(200, statusCode, "Expected status response to be 200");
            System.out.println("- Passed: Got a 200 status response");

        try {
            String responseBody = response.body();
            org.json.JSONArray jsonArray = new org.json.JSONArray(responseBody);

            int itemCount = jsonArray.length();
            int mockDataItemCount = 0;

            try {
                // Import the mock data JSON file
                java.nio.file.Path mockDataPath = java.nio.file.Paths.get("src/test/java/ContinuousIntegration/Selenium/MockData.json");
                String mockDataJsonString = java.nio.file.Files.readString(mockDataPath);
                org.json.JSONArray mockDataArray = new org.json.JSONArray(mockDataJsonString);

                // Compare and validate the number of total ID's (Products) from the mock data and API response
                mockDataItemCount = mockDataArray.length();
                Assertions.assertEquals(mockDataItemCount, itemCount, "Number of items in API response does not match the mock data");
                System.out.println("- Passed: Number of ID's (Products) from API response, total ID's: " + itemCount + " / " + mockDataItemCount);

                // Find the item with ID 15 from the mock data
                org.json.JSONObject mockDataId15 = null;
                for (int i = 0; i < mockDataArray.length(); i++) {
                    org.json.JSONObject obj = mockDataArray.getJSONObject(i);
                    if (obj.has("id") && obj.getInt("id") == 15) {
                        mockDataId15 = obj;
                        break;
                    }
                }

                // Find the item with ID 15 from API response
                org.json.JSONObject apiDataId15 = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    org.json.JSONObject obj = jsonArray.getJSONObject(i);
                    if (obj.has("id") && obj.getInt("id") == 15) {
                        apiDataId15 = obj;
                        break;
                    }
                }

                // Compare and validate the item from ID 15 from the mock data and API response
                assert mockDataId15 != null;
                assert apiDataId15 != null;
                Assertions.assertEquals(mockDataId15.toString(), apiDataId15.toString(),
                        "The item with ID 15 is not the same in the mock data and API response");
                System.out.println("- Passed: Item with ID 15 is equal in both the mock data and API response.");

            } catch (Exception e) {
                System.out.println("Failed to read or parse the mock data with error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("- Failed: Number of ID's from API response with error: " + e.getMessage());
        }

        } catch (Exception e) {
            System.out.println("- Failed: 200 status response not valid, instead got: " +  e.getMessage());
        }
    }
}

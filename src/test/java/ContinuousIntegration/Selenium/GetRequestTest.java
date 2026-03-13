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

            // Assert responseBody from the API response to be used in responseArray
            String responseBody = response.body();
            org.json.JSONArray responseArray = new org.json.JSONArray(responseBody);

            // Import data from MockData.json to be used in mockDataArray
            java.nio.file.Path mockDataPath = java.nio.file.Paths.get("src/test/java/ContinuousIntegration/Selenium/MockData.json");
            String mockDataJsonString = java.nio.file.Files.readString(mockDataPath);
            org.json.JSONArray mockDataArray = new org.json.JSONArray(mockDataJsonString);

            // Setup data points
            int statusCode = response.statusCode();
            int itemCount = responseArray.length();
            int mockDataItemCount = 0;

            // Find the item and data with ID 15 from API response
            org.json.JSONObject apiDataId15 = null;
            for (int i = 0; i < responseArray.length(); i++) {
                org.json.JSONObject obj = responseArray.getJSONObject(i);
                if (obj.has("id") && obj.getInt("id") == 15) {
                    apiDataId15 = obj;
                    break;
                }
            }

            // Find the item and data with ID 15 from the mock data
            org.json.JSONObject mockDataId15 = null;
            for (int i = 0; i < mockDataArray.length(); i++) {
                org.json.JSONObject obj = mockDataArray.getJSONObject(i);
                if (obj.has("id") && obj.getInt("id") == 15) {
                    mockDataId15 = obj;
                    break;
                }
            }

            // TEST 1: Validate GET Response status
            Assertions.assertEquals(200, statusCode, "Expected status response to be 200, instead got: " + statusCode);
            System.out.println("- Passed: Got a 200 status response");

            // TEST 2: Compare and validate the number of total ID's (Products) against the mock data and API response
            mockDataItemCount = mockDataArray.length();
            Assertions.assertEquals(mockDataItemCount, itemCount, "Number of items in API response does not match the mock data");
            System.out.println("- Passed: Number of total ID's (Products) from API response: " + itemCount + " / " + mockDataItemCount);

            // TEST 3: Compare and validate specific field and data (Title, Price and Category) from ID 15 against the mock data and API response
            assert mockDataId15 != null;
            assert apiDataId15 != null;

            Assertions.assertEquals(mockDataId15.getString("title"), apiDataId15.getString("title"));
            Assertions.assertEquals(mockDataId15.getDouble("price"), apiDataId15.getDouble("price"));
            Assertions.assertEquals(mockDataId15.getString("category"), apiDataId15.getString("category"));
            System.out.println("- Passed: ID 15 has equal fields and data for Title, Price and Category in both the mock data and API response");

            // TEST 4: Compare and validate all the items and data from ID 15 against the mock data and API response
            Assertions.assertEquals(mockDataId15.toString(), apiDataId15.toString(),
                    "The item with ID 15 is not the same in the mock data and API response");
            System.out.println("- Passed: All the data in ID 15 is equal in both the mock data and API response.");

        } catch (Exception e) {
            System.out.println("Catch error: " + e.getMessage());
        }
    }
}

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XamproAutomationTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
       // WebDriverManager.chromedriver().setup();  // Ensure driver setup
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
    }

    @DisplayName("Automated Login Using JSON Data")
    @Test
    public void testLogin() throws IOException {
        driver.get("https://www.xampro.org/login");

        // Load user data from JSON file
        JsonObject userData = readJsonFile("data.json");

        // Fill login form with JSON data
        fillFormField("email", userData.get("email").getAsString());
        fillFormField("password", userData.get("password").getAsString());

        clickElement(By.cssSelector(".account-form-submit-button button"));
        validateLogin();

        System.out.println("Login successful, current URL: " + driver.getCurrentUrl());
    }

    @DisplayName("Update Profile After Login")
    @Test
    public void testUpdateProfileAfterLogin() throws IOException {
        // Login before updating the profile
        testLogin();

        driver.get("https://www.xampro.org/profile");

        // Fill in all fields in the profile form
        fillProfileFields();

        // Scroll to the submit button and click it
        scrollToElement(By.cssSelector(".profile-submit-page-btn"));
        clickElement(By.cssSelector(".profile-submit-page-btn"));

        // Validate updated profile information
        validateProfileUpdate("Updated Name", "9876543210", "2000-01-01");

        System.out.println("Profile updated successfully.");
    }

    private void fillProfileFields() {
        fillFormField("fullName", "Updated Name");
        fillFormField("phoneNumber", "9876543210");

        // Set the date field using JavaScript with correct format
        setDateField("dob", "2000-01-01");

        // Scroll to each section to ensure visibility
        scrollToElement(By.id("radio-gender-male"));
        WebElement genderMale = wait.until(ExpectedConditions.elementToBeClickable(By.id("radio-gender-male")));
        genderMale.click();

        scrollToElement(By.id("education"));
        selectDropdownOption("education", "BSC");

        scrollToElement(By.id("react-select-3-input"));
        fillFormField("react-select-3-input", "Central University");
    }

    private JsonObject readJsonFile(String fileName) throws IOException {
        try (FileReader reader = new FileReader(Paths.get("src", "test", "resources", fileName).toFile())) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private void fillFormField(String id, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        element.clear();
        element.sendKeys(value);
    }

    private void selectDropdownOption(String id, String visibleText) {
        Select dropdown = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id(id))));
        dropdown.selectByVisibleText(visibleText);
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    private void setDateField(String id, String date) {
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        js.executeScript("arguments[0].value = arguments[1];", dateField, date);
        dateField.sendKeys(Keys.TAB);  // Ensure the field loses focus to trigger any date validation
    }

    private void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void validateLogin() {
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    private void validateProfileUpdate(String expectedName, String expectedPhone, String expectedDOB) {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.urlContains("/profile"));

        assertEquals(expectedName, driver.findElement(By.id("fullName")).getAttribute("value"));
        assertEquals(expectedPhone, driver.findElement(By.id("phoneNumber")).getAttribute("value"));
        assertEquals(expectedDOB, driver.findElement(By.id("dob")).getAttribute("value"));

        System.out.println("Profile update successfully validated.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

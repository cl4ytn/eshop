package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();

        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void userInteractionTestSubmit(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement checkSubmitButton = driver.findElement(By.tagName("button"));

        WebElement inputForName = driver.findElement(By.id("nameInput"));
        inputForName.clear();

        String testProduct = "a product";
        inputForName.sendKeys(testProduct);

        WebElement inputForQuantity = driver.findElement(By.id("quantityInput"));
        inputForQuantity.clear();

        String testQuantity = "1";
        inputForQuantity.sendKeys(testQuantity);

        checkSubmitButton.click();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);

        assertEquals(currentUrl, expectedUrl);

        List<WebElement> cells = driver.findElements(By.tagName("td"));
        assertEquals("a product", cells.get(0).getText());
        assertEquals("1", cells.get(1).getText());

        driver.quit();
    }
}
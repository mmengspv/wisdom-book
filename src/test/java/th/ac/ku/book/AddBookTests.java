package th.ac.ku.book;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddBookTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static WebDriverWait wait;

    @FindBy(id = "nameInput")
    private WebElement nameField;

    @FindBy(id = "authorInput")
    private WebElement authorField;

    @FindBy(id = "priceInput")
    private WebElement priceField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;


    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 1000);
    }

    @BeforeEach
    void beforeEach(){
        driver.get("http://localhost:" + port + "/book/add");
        PageFactory.initElements(driver, this);
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        Thread.sleep(3000);
    }

    @AfterAll
    static void afterAll(){
        driver.quit();
    }

    @Test
    void testAddBook(){
        // find in /book/add ตามใน beforeEach()
//        WebElement nameField = wait.until(webDriver ->
//                webDriver.findElement(By.id("nameInput")));
//        WebElement authorField = driver.findElement(By.id("authorInput"));
//        WebElement priceField = driver.findElement(By.id("priceInput"));
//        WebElement submitButton = driver.findElement(By.id("submitButton"));

        nameField.sendKeys("Harry Potter");
        authorField.sendKeys("J.K. Rowling");
        priceField.sendKeys("600");
        submitButton.click();

        //find in book.html (ซึ่งได้หน้านี้หลังจากการกด submitButton.click())
        WebElement name = wait.until(webDriver -> webDriver.findElement(By.xpath("//table/tbody/tr[1]/td[1]")));
        WebElement author = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
        WebElement price = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]"));

        assertEquals("Harry Potter", name.getText());
        assertEquals("J.K. Rowling", author.getText());
        assertEquals("600.00", price.getText());
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class EbayCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\yoges\\OneDrive\\Desktop\\TestingMobile\\sept7\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void verifyAddToCart() {

        //open URL
        driver.get("https://www.ebay.com");

        //  Search for 'book'
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys("book");
        WebElement searchButton = driver.findElement(By.id("gh-btn"));
        searchButton.click();

        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".s-item a.s-item__link"))).click();

      
        WebElement quantityDropdown = driver.findElement(By.id("qtyTextBox"));
        quantityDropdown.clear();
        quantityDropdown.sendKeys("2");

        WebElement addToCartButton = driver.findElement(By.id("atcRedesignId_btn"));
        addToCartButton.click();

        //  Verify the cart has been updated with 2 items
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#gh-cart-n span[class*='gh-cart-count']")));
        WebElement cartCount = driver.findElement(By.cssSelector("#gh-cart-n span[class*='gh-cart-count']"));

        String cartCountText = cartCount.getText().trim();
        Assert.assertEquals(cartCountText, "2", "Cart count does not match the expected value.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

package test.UnitTest;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SampleTest {

    public static void main(String[] args) {

        //This downloads the chromedriver and makes it available for the script
        WebDriverManager.chromedriver().setup();

        //To create the object of chromedriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try
        {
            //To navigate to search engine
            driver.get("https://www.google.com");

            //To find the textbox, and search for the value
            WebElement searchbox = driver.findElement(By.xpath("//textarea[@id='APjFqb']"));
            searchbox.sendKeys("science", Keys.ENTER);
            Thread.sleep(5000);

            //To find all the links on the results page
            List<WebElement> links = driver.findElements(By.xpath("//div/span/a[contains(@href, 'science')]"));
            System.out.println(links.size());
            Thread.sleep(5000);

            //To click on the first link
            if (!links.isEmpty()) {

                //To get the first link WebElement
                WebElement firstLink = links.get(0);

                //To Check if the first link is not null
                if (firstLink != null) {

                    //To click the first link from the returned result
                    firstLink.click();

                    //To create a WebDriverWait instance with a timeout of 10 seconds
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    //To wait until the page is loaded completely
                    wait.until((ExpectedCondition<Boolean>) webDriver ->
                    {
                        assert webDriver != null;
                        return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete");
                    });

                    //To validate the title
                    String actualTitle = driver.getTitle().toLowerCase();
                    Assert.isTrue(actualTitle.contains("science"), "Page title doesn't contain the expected substring");
                    System.out.println("Assert Successful");
                }
                else {
                    System.out.println("First link is null");
                }
            }
            else {
                System.out.println("No links found on the page");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally {
            driver.quit();
            System.out.println("Test Successful");
        }
    }
}

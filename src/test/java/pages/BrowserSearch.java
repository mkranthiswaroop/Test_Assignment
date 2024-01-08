package pages;

import base.BaseTest;
import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BrowserSearch extends BaseTest {

    private static WebElement element;
    private static String textbox_xpath = "//textarea[@id='APjFqb']";
    private static String links_xpath = "//div[@id='search']//div/span/a";
    private static String searchbtn_xpath= "(//input[@name='btnK'])[2]";

    public static void NavigateToSearchEngine(WebDriver driver, String searchEngine) {
        switch (searchEngine.toLowerCase()) {
            case "google":
                driver.get("https://www.google.com");
                break;
            case "bing":
                driver.get("https://www.bing.com");
                break;
            case "yahoo":
                driver.get("https://www.yahoo.com");
                break;
            default:
                throw new IllegalArgumentException("Unsupported search engine: " + searchEngine);
        }
    }
    public static WebElement WebSearch() {
        element = driver.findElement(By.xpath(textbox_xpath));
        return element;
    }
    public static WebElement ClickSearchButton(){
        element = driver.findElement(By.xpath(searchbtn_xpath));
        return element;
    }
    public static List<WebElement> GetAllTheLinks() {
        try
        {
            List<WebElement> links = driver.findElements(By.xpath(links_xpath));
            logger.info(links.size());
            return links;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String ClickTheFirstLink(List<WebElement> links) {
        String currUrl = null;
        if (!links.isEmpty()) {

            //To get the first link WebElement
            WebElement firstLink = links.get(0);

            //To Check if the first link is not null
            if (firstLink != null) {
                firstLink.click();
                currUrl =driver.getCurrentUrl();
            } else {
                logger.info("First link is null");
            }
        }
        return currUrl;
    }
    public static void WaitUntilThePageIsLoaded() {
        // To create a WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // To wait until the page is loaded completely
        wait.until((ExpectedCondition<Boolean>) webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
    public static String GetUrlOfTheLink (WebElement ele){
        //To get the title of the web page
        String hrefValue = ele.getAttribute("href");
        return hrefValue;
    }
    public static boolean ValidateTheUrl(String expUrl) {
        //To compare the values
        String actUrl = driver.getCurrentUrl();
        boolean isURLValid = actUrl.equals(expUrl);
        Assert.isTrue(isURLValid, "Page URL doesn't match");
        return isURLValid;
    }
}
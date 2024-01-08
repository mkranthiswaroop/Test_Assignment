package test.TestNG;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import pages.BrowserSearch;

public class BrowserSearchTest extends BaseTest {

    @Test(dataProvider = "testdata")
    public static void browseTest(String search_text) {
        try
        {
            logger.info("Test Started");

            //To navigate to search engine
            BrowserSearch.NavigateToSearchEngine(driver,"google");
            BrowserSearch.WaitUntilThePageIsLoaded();

            //To find the textbox, and search for the value
            WebElement searchbox = BrowserSearch.WebSearch();
            searchbox.sendKeys(search_text);
            WebElement searchbtn = BrowserSearch.ClickSearchButton();
            searchbtn.click();
            BrowserSearch.WaitUntilThePageIsLoaded();
            Reporter.log("Search for the term");

            //To find all the links on the results page
            List<WebElement> links = BrowserSearch.GetAllTheLinks();
            BrowserSearch.WaitUntilThePageIsLoaded();
            Reporter.log("Get All the links");

            String actualUrl=null;

            //To click on the first link
            if (links != null && !links.isEmpty())
            {
                actualUrl = BrowserSearch.ClickTheFirstLink(links);
                BrowserSearch.WaitUntilThePageIsLoaded();
                Reporter.log("Clicked the first link");
            }
            else {
                System.out.println("No links found on the page");
            }

            //To validate the page url
            boolean textComp = BrowserSearch.ValidateTheUrl(actualUrl);
            Assert.isTrue(textComp, "Page title doesn't contain the expected substring");
            test.log(Status.PASS, "Search performed successfully");

            logger.info("Test completed");
        }
        catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Search Test failed with error:" + e.getMessage());
        }
    }

    @DataProvider(name = "testdata")
    public Object[][] tData(){
        return new Object[][]{
                {"Mathematics"}
        };
    }
}

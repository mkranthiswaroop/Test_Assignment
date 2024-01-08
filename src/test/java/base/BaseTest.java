package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static Properties prop = new Properties();
    public static Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() throws IOException {
        // Initialize WebDriver, Logger, and other setup
        if (driver == null) {
            FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/test/resources/config/config.properties");
            prop.load(fr);
            String browser = prop.getProperty("browser").toLowerCase();
            initializeWebDriver(browser);
        }

        // Extent Reports setup
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        // Start ExtentTest
        test = extent.createTest(getClass().getSimpleName());
        // Start Logger
        logger.info("Test Started");
    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log the test status in the Extent Report
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else {
            test.skip("Test skipped");
        }
        // Close the browser and perform cleanup
        if (driver != null) {
            driver.quit();
        }
        // End ExtentTest
        extent.flush();
    }
    public void initializeWebDriver(String browser) {
        try {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser specified: " + browser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize the WebDriver due to: " + e.getMessage());
        }
    }
}

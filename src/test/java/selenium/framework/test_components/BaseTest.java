package selenium.framework.test_components;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import selenium.framework.page_objects.WelcomePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;;
import java.util.Properties;


public class BaseTest {
    public WebDriver driver;
    public WelcomePage welcomePage;

    public WebDriver initTest() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//selenium//framework//resources//GlobalData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String browserWanted = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
        ChromeOptions chromeOptions = new ChromeOptions();
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (browserWanted.equalsIgnoreCase("firefox")) {
            // Add the headless option for Firefox
//            firefoxOptions.addArguments("--headless");
            driver = new FirefoxDriver(/*firefoxOptions*/);
        } else if (browserWanted.equalsIgnoreCase("chrome")) {
//            System.setProperty("webdriver.chrome.driver","//usr//bin//chromedriver");
            // Add the headless option for Chrome
//            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(/*chromeOptions*/);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        return driver;
    }

    public String getScreenshotOfAllPage(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(screenshotFile, dest);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }

    public String getScreenshotOfElement(WebElement element, String testCaseName) throws IOException {
        File screenshotFile = element.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(screenshotFile, dest);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }

//    @BeforeMethod(alwaysRun = true)
    public WelcomePage launchApplication()  {
        driver = initTest();
        welcomePage = new WelcomePage(driver);
        welcomePage.goToPage();
        return welcomePage;
    }
    public WelcomePage launchApplication(WebDriver driver)  {
        welcomePage = new WelcomePage(driver);
        welcomePage.goToPage();
        return welcomePage;
    }
    public WelcomePage initDriver(){
        driver = initTest();
        return new WelcomePage(driver);
    }
    public String getWhichBrowserExecuted(){
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//selenium//framework//resources//GlobalData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
    }



    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        driver.close();
    }
}

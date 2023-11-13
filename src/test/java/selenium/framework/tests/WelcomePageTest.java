package selenium.framework.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v117.network.Network;
import org.openqa.selenium.devtools.v117.network.model.Response;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.framework.data.DataReader;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;
import selenium.framework.test_components.Retry;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WelcomePageTest extends BaseTest {

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    public void greetingWelcomePageTest() {
        String expectedGreeting = "Hi, nice to see you in our internet-magazine";
        String markedNameExpected = "Market";
        WelcomePage welcomePage = launchApplication();
        String actualGreeting = welcomePage.getGreeting();
        String actualMarketName = welcomePage.getMarketName();

        Assert.assertEquals(actualGreeting, expectedGreeting);
        Assert.assertEquals(actualMarketName, markedNameExpected);
    }

    @Test(dataProvider = "getDataLoginFields", groups = {"smock"}, retryAnalyzer = Retry.class)
    public void loginFieldsText(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        boolean usernameIsDisplayed = welcomePage.getUsernameField().isDisplayed();
        boolean passwordIsDisplayed = welcomePage.getPasswordField().isDisplayed();
        Assert.assertTrue(usernameIsDisplayed);
        Assert.assertTrue(passwordIsDisplayed);

        welcomePage.setLoginField(inputData.get("username"));
        welcomePage.setPasswordField(inputData.get("password"));

        String usernameFromField = welcomePage.getUsernameField().getAttribute("value");
        String passwordFromField = welcomePage.getPasswordField().getAttribute("value");

        Assert.assertEquals(usernameFromField, inputData.get("username"));
        Assert.assertEquals(passwordFromField, inputData.get("password"));
    }

    @Test(groups = {"smock"})
    public void loginBtnTest() {
        WelcomePage welcomePage = launchApplication();
        WebElement loginBtn = welcomePage.getLoginBtn();
        boolean isLoginBtnDisplayed = loginBtn.isDisplayed();

        Assert.assertTrue(isLoginBtnDisplayed);
    }

    @Test(groups = {"smock"})
    public void loginBtnHooverTest() {
        WelcomePage welcomePage = launchApplication();
        WebElement loginBtn = welcomePage.getLoginBtn();

        Actions actions = new Actions(driver);
        actions.moveToElement(loginBtn).build().perform();
        boolean isBtnOutlinePresent = loginBtn.getAttribute("class").contains("btn-outline-warning");
        Assert.assertTrue(isBtnOutlinePresent);
    }

    @Test(groups = {"smock"})
    public void registerLinkTest() {
        WelcomePage welcomePage = launchApplication();
        boolean isRegisterLinkDisplayed = welcomePage.getRegisterLink().isDisplayed();
        Assert.assertTrue(isRegisterLinkDisplayed);
    }

    @Test(groups = {"smock"})
    public void registerLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getRegisterLink().getAttribute("href").contains("/registration");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock"})
    public void productsLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getProductsLink().getAttribute("href").contains("/store");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock"})
    public void marketLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getMarketLink().getAttribute("href").contains("/");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock"})
    public void cartLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getCartLink().getAttribute("href").contains("/cart");
        Assert.assertTrue(containsEndpoint);
    }

    @DataProvider
    public Object[][] getDataLoginFields() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//login.json");
        return new Object[][]{{jsonData.get(0)}};
    }

}

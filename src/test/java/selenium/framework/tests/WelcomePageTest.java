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

    @Test(groups = {"smock", "regression"}, retryAnalyzer = Retry.class)
    public void greetingWelcomePageTest() {
        String expectedGreeting = "Hi, nice to see you in our internet-magazine";
        String markedNameExpected = "Market";
        WelcomePage welcomePage = launchApplication();
        String actualGreeting = welcomePage.getGreeting();
        String actualMarketName = welcomePage.getMarketName();

        Assert.assertEquals(actualGreeting, expectedGreeting);
        Assert.assertEquals(actualMarketName, markedNameExpected);
    }

    @Test(dataProvider = "getDataLoginFields", groups = {"smock", "regression"}, retryAnalyzer = Retry.class)
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

    @Test(groups = {"smock", "regression"})
    public void loginBtnTest() {
        WelcomePage welcomePage = launchApplication();
        WebElement loginBtn = welcomePage.getLoginBtn();
        boolean isLoginBtnDisplayed = loginBtn.isDisplayed();

        Assert.assertTrue(isLoginBtnDisplayed);
    }

    @Test(groups = {"smock", "regression"})
    public void loginBtnHooverTest() {
        WelcomePage welcomePage = launchApplication();
        WebElement loginBtn = welcomePage.getLoginBtn();

        Actions actions = new Actions(driver);
        actions.moveToElement(loginBtn).build().perform();
        boolean isBtnOutlinePresent = loginBtn.getAttribute("class").contains("btn-outline-warning");
        Assert.assertTrue(isBtnOutlinePresent);
    }

    @Test(groups = {"smock", "regression"})
    public void registerLinkTest() {
        WelcomePage welcomePage = launchApplication();
        boolean isRegisterLinkDisplayed = welcomePage.getRegisterLink().isDisplayed();
        Assert.assertTrue(isRegisterLinkDisplayed);
    }

    @Test(groups = {"smock", "regression"})
    public void registerLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getRegisterLink().getAttribute("href").contains("/registration");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock", "regression"})
    public void productsLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getProductsLink().getAttribute("href").contains("/store");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock", "regression"})
    public void marketLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getMarketLink().getAttribute("href").contains("/");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock", "regression"})
    public void cartLinkEndpointTest() {
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getCartLink().getAttribute("href").contains("/cart");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(dataProvider = "getDataLogin", groups = {"login", "smock", "regression"})
    public void loginToAppTest(HashMap<String, String> inputData) throws InterruptedException {
        WelcomePage welcomePage;
        String browser = getWhichBrowserExecuted();
        if (browser.equalsIgnoreCase("chrome")) {
            System.out.println("Browser running: " + browser);
            welcomePage = initDriver();
            DevTools devTools = ((ChromiumDriver) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.clearBrowserCookies());
            welcomePage = launchApplication(driver);
            welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));

            AtomicBoolean is200Flag = new AtomicBoolean(false);
            AtomicReference<String> body = new AtomicReference<>("");
            // Add a listener to intercept network responses
            devTools.addListener(Network.responseReceived(), responseReceived -> {
                Response response = responseReceived.getResponse();
                // Check if the response is related to your "auth" request
                String url = response.getUrl();
                if (url.contains(inputData.get("endpoint"))) {
                    int statusCode = response.getStatus();
                    if (statusCode >= 200 && statusCode < 300) {
                        // Response status is in the 2xx range, which is generally considered a success.
                        // You can further validate the response as needed.
                        boolean is200 = true;
                        is200Flag.set(is200);
                    } else {
                        // Response status is not in the 2xx range, indicating an error.
                        // Handle the error scenario as needed.
                        boolean isNot200 = false;
                        is200Flag.set(isNot200);
                        body.set(devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody());
                    }
                }
            });

            Thread.sleep(2000);
            if (is200Flag.get()) {
                Assert.assertFalse(welcomePage.getLoginBtn().isDisplayed());
                Assert.assertTrue(welcomePage.getLogoutBtn().isDisplayed());
                // Inject JavaScript to read and validate data from local storage
                Object returnedObject = ((JavascriptExecutor) driver).executeScript("var data = localStorage.getItem('ngStorage-masterMarketUser');" +
                        "return data;");
                if (returnedObject != null) {
                    String localStorageUser = returnedObject.toString();
                    Assert.assertTrue(localStorageUser.contains(inputData.get("username")));
                    Assert.assertTrue(localStorageUser.contains("token"));
                }
                welcomePage.getLogoutBtn().click();
                String pageURL = welcomePage.getPageURL();
                String[] split = pageURL.split("!");
                Assert.assertTrue(split[1].equalsIgnoreCase("/"));
            } else {
                String receivedBody = body.get();
                Assert.assertTrue(receivedBody.contains("CHECK_TOKEN_ERROR"));
                Assert.assertTrue(receivedBody.contains("Invalid login or password"));
            }
        } else {
            System.out.println("Browser running: " + browser);
            welcomePage = launchApplication();
            welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
            if (!welcomePage.getLoginBtn().isDisplayed()) {
                Assert.assertTrue(welcomePage.getLogoutBtn().isDisplayed());
            } else {
                Assert.assertTrue(welcomePage.getLoginBtn().isDisplayed());
                Assert.assertTrue(welcomePage.getRegisterLink().isDisplayed());
            }
        }
    }

    @DataProvider
    public Object[][] getDataLogin() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//login.json");
        return new Object[][]{{jsonData.get(0)}, {jsonData.get(1)}, {jsonData.get(2)}, {jsonData.get(3)}};
    }

    @DataProvider
    public Object[][] getDataLoginFields() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//login.json");
        return new Object[][]{{jsonData.get(0)}};
    }

}

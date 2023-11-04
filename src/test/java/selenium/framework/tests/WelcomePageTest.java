package selenium.framework.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;
import selenium.framework.test_components.Retry;

public class WelcomePageTest extends BaseTest {

    @Test(groups = {"smock", "regression"}, retryAnalyzer = Retry.class)
    public void greetingWelcomePageTest() {
        String expectedGreeting = "Hi, nice to see you in our internet-magazine";
        String markedNameExpected = "Market";
        WelcomePage welcomePage = launchApplication();
        String actualGreeting = welcomePage.getGreeting();
        String actualMarketName = welcomePage.getMarketName();

        Assert.assertEquals(actualGreeting,expectedGreeting);
        Assert.assertEquals(actualMarketName,markedNameExpected);
    }

    @Test(groups = {"smock", "regression"}, retryAnalyzer = Retry.class)
    public void loginFieldsText() {
        WelcomePage welcomePage = launchApplication();
        String testLogin = "testLogin";
        String testPasswd = "testpasswd";

        boolean usernameIsDisplayed = welcomePage.getUsernameField().isDisplayed();
        boolean passwordIsDisplayed = welcomePage.getPasswordField().isDisplayed();
        Assert.assertTrue(usernameIsDisplayed);
        Assert.assertTrue(passwordIsDisplayed);

        welcomePage.setLoginField(testLogin);
        welcomePage.setPasswordField(testPasswd);

        String usernameFromField = welcomePage.getUsernameField().getAttribute("value");
        String passwordFromField = welcomePage.getPasswordField().getAttribute("value");

        Assert.assertEquals(usernameFromField,testLogin);
        Assert.assertEquals(passwordFromField,testPasswd);
    }

    @Test(groups = {"smock", "regression"})
    public void loginBtnTest(){
        WelcomePage welcomePage = launchApplication();
        WebElement loginBtn = welcomePage.getLoginBtn();
        boolean isLoginBtnDisplayed = loginBtn.isDisplayed();

        Assert.assertTrue(isLoginBtnDisplayed);
    }

    @Test(groups = {"smock", "regression"})
    public void loginBtnHooverTest(){
        WelcomePage welcomePage = launchApplication();
        WebElement loginBtn = welcomePage.getLoginBtn();

        Actions actions = new Actions(driver);
        actions.moveToElement(loginBtn).build().perform();
        boolean isBtnOutlinePresent = loginBtn.getAttribute("class").contains("btn-outline-warning");
        Assert.assertTrue(isBtnOutlinePresent);
    }

    @Test(groups = {"smock", "regression"})
    public void registerLinkTest(){
        WelcomePage welcomePage = launchApplication();
        boolean isRegisterLinkDisplayed = welcomePage.getRegisterLink().isDisplayed();
        Assert.assertTrue(isRegisterLinkDisplayed);
    }

    @Test(groups = {"smock", "regression"})
    public void registerLinkEndpointTest(){
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getRegisterLink().getAttribute("href").contains("/registration");
        Assert.assertTrue(containsEndpoint);
    }

    @Test(groups = {"smock", "regression"})
    public void productsLinkEndpointTest(){
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getProductsLink().getAttribute("href").contains("/store");
        Assert.assertTrue(containsEndpoint);
    }
    @Test(groups = {"smock", "regression"})
    public void marketLinkEndpointTest(){
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getMarketLink().getAttribute("href").contains("/");
        Assert.assertTrue(containsEndpoint);
    }
    @Test(groups = {"smock", "regression"})
    public void cartLinkEndpointTest(){
        WelcomePage welcomePage = launchApplication();
        boolean containsEndpoint = welcomePage.getCartLink().getAttribute("href").contains("/cart");
        Assert.assertTrue(containsEndpoint);
    }

}

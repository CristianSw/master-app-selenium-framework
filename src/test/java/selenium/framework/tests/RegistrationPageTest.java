package selenium.framework.tests;

import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v117.network.Network;
import org.openqa.selenium.devtools.v117.network.model.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.framework.data.DataReader;
import selenium.framework.page_objects.RegistrationPage;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;
import selenium.framework.test_components.Retry;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class RegistrationPageTest extends BaseTest {


    @Test(dataProvider = "getRegistrationDataMessages", groups = {"smock", "login"})
    public void validateRegistrationFormTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();
        Assert.assertTrue(registrationPage.getUserUsername().isDisplayed());
        Assert.assertTrue(registrationPage.getUserEmail().isDisplayed());
        Assert.assertTrue(registrationPage.getUserPassword().isDisplayed());
        Assert.assertTrue(registrationPage.getUserConfirmPassword().isDisplayed());
        Assert.assertTrue(registrationPage.getUsernameHelp().isDisplayed());
        Assert.assertTrue(registrationPage.getEmailHelp().isDisplayed());
        Assert.assertEquals(registrationPage.getEmailHelpMessage(), inputData.get("emailHelp"));
        Assert.assertEquals(registrationPage.getUsernameHelpMessage(), inputData.get("usernameHelp"));
    }


    @Test(dataProvider = "getRegistrationDataPlaceholders", groups = {"smock", "login"})
    public void validateRegistrationPlaceholdersTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();
        registrationPage.getUserUsername().getAttribute("placeholder");
        Assert.assertEquals(registrationPage.getUserUsername().getAttribute("placeholder"), inputData.get("usernamePlaceholder"));
        Assert.assertEquals(registrationPage.getUserEmail().getAttribute("placeholder"), inputData.get("emailPlaceholder"));
        Assert.assertEquals(registrationPage.getUserPassword().getAttribute("placeholder"), inputData.get("passwordPlaceholder"));
        Assert.assertEquals(registrationPage.getUserConfirmPassword().getAttribute("placeholder"), inputData.get("confirmPasswordPlaceholder"));
    }

    @Test(dataProvider = "getRegistrationDataFields", groups = {"smock", "login"})
    public void validateFieldsTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();

        registrationPage.getUserUsername().sendKeys(inputData.get("username"));
        registrationPage.getUserEmail().sendKeys(inputData.get("email"));
        registrationPage.getUserPassword().sendKeys(inputData.get("password"));
        registrationPage.getUserConfirmPassword().sendKeys(inputData.get("confirmPassword"));

        Assert.assertEquals(registrationPage.getUserUsername().getAttribute("value"), inputData.get("username"));
        Assert.assertEquals(registrationPage.getUserEmail().getAttribute("value"), inputData.get("email"));
        Assert.assertEquals(registrationPage.getUserPassword().getAttribute("value"), inputData.get("password"));
        Assert.assertEquals(registrationPage.getUserConfirmPassword().getAttribute("value"), inputData.get("confirmPassword"));

    }

    @Test(dataProvider = "getRegistrationUsers", groups = {"smock", "db"})
    public void registerValidUsersTest(HashMap<String, String> inputData) throws ClassNotFoundException, SQLException, InterruptedException {

        String host = "localhost";
        String port = "5434";
        String username = "postgres-auth";
        String password = "postgres-auth";
        Class.forName("org.postgresql.Driver");
        //jdbc:postgresql://localhost:5434/postgres-auth
        Connection connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/postgres-auth", username, password);
        Statement statement = connection.createStatement();

        WelcomePage welcomePage = launchApplication();
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();

        registrationPage.getUserUsername().sendKeys(inputData.get("username"));
        registrationPage.getUserEmail().sendKeys(inputData.get("email"));
        registrationPage.getUserPassword().sendKeys(inputData.get("password"));
        registrationPage.getUserConfirmPassword().sendKeys(inputData.get("confirmPassword"));

        registrationPage.getRegisterBtn().click();
        Thread.sleep(1000);
        String usernameToSearch = inputData.get("username");
        ResultSet resultSet = statement.executeQuery("select username, password, email from users where username = '" + usernameToSearch + "'");
        Thread.sleep(2000);
        String usernameFromDB = "";
        String passwordFromDB = "";
        String emailFromDB = "";
        while (resultSet.next()) {
            usernameFromDB = resultSet.getString("username");
            passwordFromDB = resultSet.getString("password");
            emailFromDB = resultSet.getString("email");

            System.out.println("username : " + usernameFromDB);
            System.out.println("password : " + passwordFromDB);
            System.out.println("email : " + emailFromDB);
        }
        connection.close();
        Assert.assertEquals(usernameFromDB, inputData.get("username"));
        Assert.assertNotEquals(passwordFromDB, inputData.get("password"));
        Assert.assertEquals(emailFromDB, inputData.get("email"));
        String pageURL = registrationPage.getPageURL();
        String s = Arrays.asList(pageURL.split("!")).get(1);
        Assert.assertEquals(s, "/");
    }


    @Test(groups = {"smock", "login","chromeOnly"})
    public void invalidFieldProvidedTest() throws InterruptedException {
        WelcomePage welcomePage = launchApplication();
        DevTools devTools = ((ChromiumDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.clearBrowserCookies());
        welcomePage = launchApplication(driver);
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();

        AtomicBoolean is400Flag = new AtomicBoolean(false);
        AtomicReference<String> body = new AtomicReference<>("");
        // Add a listener to intercept network responses
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            // Check if the response is related to your "auth" request
            String url = response.getUrl();
            if (url.contains(("register"))) {
                int statusCode = response.getStatus();

                // Response status is in the 2xx range, which is generally considered a success.
                // You can further validate the response as needed.
                boolean is500 = true;
                is400Flag.set(is500);
                body.set(devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody());
            }
        });
        registrationPage.getUserEmail().sendKeys("email");
        registrationPage.getRegisterBtn().click();
        Thread.sleep(3000);

        if (is400Flag.get()) {
            String receivedBody = body.get();
            Assert.assertTrue(receivedBody.contains("400"));
            Assert.assertTrue(receivedBody.contains("/market-auth/register"));
            Assert.assertTrue(receivedBody.contains("Bad Request"));
        }
    }


    @Test(groups = {"smock", "login", "chromeOnly"})
    public void invalidEmailOnlyProvidedTest() throws InterruptedException {
        WelcomePage welcomePage = launchApplication();
        DevTools devTools = ((ChromiumDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.clearBrowserCookies());
        welcomePage = launchApplication(driver);
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();

        AtomicBoolean is500Flag = new AtomicBoolean(false);
        AtomicReference<String> body = new AtomicReference<>("");
        // Add a listener to intercept network responses
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            // Check if the response is related to your "auth" request
            String url = response.getUrl();
            if (url.contains(("register"))) {
                int statusCode = response.getStatus();

                // Response status is in the 2xx range, which is generally considered a success.
                // You can further validate the response as needed.
                boolean is500 = true;
                is500Flag.set(is500);
                body.set(devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody());
            }
        });
        registrationPage.getUserUsername().sendKeys("Azalia");
        registrationPage.getUserEmail().sendKeys("email");
        registrationPage.getUserPassword().sendKeys("passwd");
        registrationPage.getUserConfirmPassword().sendKeys("passwd");
        registrationPage.getRegisterBtn().click();
        Thread.sleep(3000);

        if (is500Flag.get()) {
            String receivedBody = body.get();
            Assert.assertTrue(receivedBody.contains("500"));
            Assert.assertTrue(receivedBody.contains("/market-auth/register"));
            Assert.assertTrue(receivedBody.contains("Internal Server Error"));
        }
    }

    @Test(groups = {"smock", "login", "chromeOnly"}, retryAnalyzer = Retry.class)
    public void userWithUsernameAlreadyRegisteredTest() throws InterruptedException {
        WelcomePage welcomePage = launchApplication();
        DevTools devTools = ((ChromiumDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.clearBrowserCookies());
        welcomePage = launchApplication(driver);
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();

        AtomicBoolean is400Flag = new AtomicBoolean(false);
        AtomicReference<String> body = new AtomicReference<>("");
        // Add a listener to intercept network responses
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            // Check if the response is related to your "auth" request
            String url = response.getUrl();
            if (url.contains(("register"))) {
                int statusCode = response.getStatus();

                // Response status is in the 2xx range, which is generally considered a success.
                // You can further validate the response as needed.
                boolean is400 = true;
                is400Flag.set(is400);
                body.set(devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody());
            }
        });
        registrationPage.getUserUsername().sendKeys("cristiandolinta");
        registrationPage.getUserEmail().sendKeys("cristian@example.com");
        registrationPage.getUserPassword().sendKeys("testpassword123");
        registrationPage.getUserConfirmPassword().sendKeys("testpassword123");
        registrationPage.getRegisterBtn().click();
        Thread.sleep(3000);

        if (is400Flag.get()) {
            String receivedBody = body.get();
            Assert.assertTrue(receivedBody.contains("400"));
            Assert.assertTrue(receivedBody.contains("User with this username already present."));
        }
    }

    @Test(groups = {"smock", "login", "chromeOnly"})
    public void passwordMissmatchTest() throws InterruptedException {
        WelcomePage welcomePage = launchApplication();
        DevTools devTools = ((ChromiumDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.clearBrowserCookies());
        welcomePage = launchApplication(driver);
        RegistrationPage registrationPage = welcomePage.goToRegistrationPage();

        AtomicBoolean is400Flag = new AtomicBoolean(false);
        AtomicReference<String> body = new AtomicReference<>("");
        // Add a listener to intercept network responses
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            // Check if the response is related to your "auth" request
            String url = response.getUrl();
            if (url.contains(("register"))) {
                int statusCode = response.getStatus();

                // Response status is in the 2xx range, which is generally considered a success.
                // You can further validate the response as needed.
                boolean is400 = true;
                is400Flag.set(is400);
                body.set(devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody());
            }
        });
        registrationPage.getUserUsername().sendKeys("cristiandolinta");
        registrationPage.getUserEmail().sendKeys("cristian@example.com");
        registrationPage.getUserPassword().sendKeys("testpassword123");
        registrationPage.getUserConfirmPassword().sendKeys("testpassword1234");
        registrationPage.getRegisterBtn().click();
        Thread.sleep(3000);

        if (is400Flag.get()) {
            String receivedBody = body.get();
            Assert.assertTrue(receivedBody.contains("400"));
            Assert.assertTrue(receivedBody.contains("Passwords dont match."));
        }
    }

    @DataProvider
    public Object[][] getRegistrationDataMessages() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//registerMessages.json");
        return new Object[][]{{jsonData.get(0)}};
    }

    @DataProvider
    public Object[][] getRegistrationDataPlaceholders() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//registerPlaceholders.json");
        return new Object[][]{{jsonData.get(0)}};
    }

    @DataProvider
    public Object[][] getRegistrationDataFields() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//registerFieldsValidation.json");
        return new Object[][]{{jsonData.get(0)}, {jsonData.get(1)}, {jsonData.get(2)}, {jsonData.get(3)}, {jsonData.get(4)}};
    }

    @DataProvider
    public Object[][] getRegistrationUsers() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//registration.json");
        return new Object[][]{{jsonData.get(0)}, {jsonData.get(1)}, {jsonData.get(2)}};
    }
}


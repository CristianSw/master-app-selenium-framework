package selenium.framework.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.framework.data.DataReader;
import selenium.framework.page_objects.CartPage;
import selenium.framework.page_objects.OrdersPage;
import selenium.framework.page_objects.OrdersPayPage;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrdersTest extends BaseTest {

    @Test(groups = {"smock"})
    public void unauthorizedUserTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getCartLink());
        WebElement element = driver.findElement(By.xpath("//ul/li[@class='nav-item'][4]"));
        Assert.assertFalse(element.getText().contains("orders"));
    }

    @Test
    public void authorizedUserTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToAppear(welcomePage.getOrdersLink());
        Assert.assertTrue(welcomePage.getOrdersLink().isDisplayed());
    }

    @Test
    public void ordersHeaderTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        Assert.assertEquals(ordersPage.getPageName(), "Orders");
    }

    @Test(dataProvider = "getOrdersTableHeaderValues")
    public void ordersTableHeaderTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        List<WebElement> orderTableHeader = ordersPage.getOrderTableHeader();
        Assert.assertEquals(orderTableHeader.get(0).getText(), inputData.get("orderId"));
        Assert.assertEquals(orderTableHeader.get(1).getText(), inputData.get("deliveryAddress"));
        Assert.assertEquals(orderTableHeader.get(2).getText(), inputData.get("phone"));
        Assert.assertEquals(orderTableHeader.get(3).getText(), inputData.get("price"));
    }

    @Test(dataProvider = "getOrdersTableHeaderValues")
    public void orderPayBtnTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        OrdersPayPage ordersPayPage = ordersPage.clickPayBtn(0);
        Assert.assertTrue(driver.getCurrentUrl().contains("order_pay"));
    }

    @Test(dataProvider = "getOrdersTableHeaderValues")
    public void directOrdersAccessTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = initDriver();
        driver.get("http://localhost:3000/#!/orders");
        Assert.assertTrue(driver.getCurrentUrl().contains("/orders"));

        OrdersPage ordersPage = new OrdersPage(driver);
        Assert.assertTrue(ordersPage.getButtonPay().isEmpty());
    }

    @DataProvider
    public Object[][] getOrdersTableHeaderValues() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//ordersTitles.json");
        return new Object[][]{{jsonData.get(0)}};
    }
}

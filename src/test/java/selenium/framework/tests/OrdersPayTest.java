package selenium.framework.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.framework.data.DataReader;
import selenium.framework.page_objects.OrdersPage;
import selenium.framework.page_objects.OrdersPayPage;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;
import selenium.framework.test_components.Retry;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrdersPayTest extends BaseTest {
    @Test(groups = {"smock"},dataProvider = "getOrderPayData")
    public void endpointTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        List<String> orderDetailsByOrderId = ordersPage.getOrderDetailsByOrderId(inputData.get("orderId"));

        OrdersPayPage ordersPayPage = ordersPage.clickPayBtn(0);
        Assert.assertTrue(driver.getCurrentUrl().contains(orderDetailsByOrderId.get(0)));
        Assert.assertTrue(driver.getCurrentUrl().contains(inputData.get("endpoint")));
    }

    @Test(dataProvider = "getOrderPayData")
    public void pageNameTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        OrdersPayPage ordersPayPage = ordersPage.clickPayBtn(Integer.parseInt(inputData.get("orderId")));
        Assert.assertEquals(ordersPayPage.getPageName(), inputData.get("pageName"));
    }

    @Test(dataProvider = "getOrderPayData",retryAnalyzer = Retry.class)
    public void orderDetailsTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        List<String> orderDetailsByOrderId = ordersPage.getOrderDetailsByOrderId(inputData.get("orderId"));
        OrdersPayPage ordersPayPage = ordersPage.clickPayBtn(0);
        ordersPayPage.waitForElementToAppear(ordersPayPage.getOrderIdElement());
        Assert.assertTrue(ordersPayPage.getOrderId().contains(orderDetailsByOrderId.get(0)));
        Assert.assertTrue(ordersPayPage.getOrderPrice().contains(orderDetailsByOrderId.get(3)));
        Assert.assertTrue(ordersPayPage.getOrderAddress().contains(orderDetailsByOrderId.get(1)));
        Assert.assertTrue(ordersPayPage.getOrderPhone().contains(orderDetailsByOrderId.get(2)));
    }

    @Test(dataProvider = "getOrderPayData")
    public void paySectionTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("username"), inputData.get("password"));
        welcomePage.waitForElementToBeClickable(welcomePage.getOrdersLink());
        OrdersPage ordersPage = welcomePage.goToOrdersPage();
        OrdersPayPage ordersPayPage = ordersPage.clickPayBtn(Integer.parseInt(inputData.get("orderId")));
        Assert.assertEquals(ordersPayPage.getPayText(), inputData.get("paySectionDelimiter"));
    }

    @DataProvider
    public Object[][] getOrderPayData() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//orderPayData.json");
        return new Object[][]{{jsonData.get(0)}};
    }
}

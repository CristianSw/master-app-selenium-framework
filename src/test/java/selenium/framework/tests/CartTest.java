package selenium.framework.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.framework.data.DataReader;
import selenium.framework.page_objects.CartPage;
import selenium.framework.page_objects.OrdersPage;
import selenium.framework.page_objects.ProductListPage;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CartTest extends BaseTest {
    @Test(groups = {"smock"}, dataProvider = "getCartTableHeader")
    public void cartTableHeader(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getCartLink());
        CartPage cartPage = welcomePage.goToCartPage();
        List<WebElement> tableHeader = driver.findElements(By.xpath("//thead/tr/td"));
        List<String> tableHeaderNames = new ArrayList<>();
        for (WebElement e : tableHeader) {
            String columnName = e.getText();
            tableHeaderNames.add(columnName);
        }
        Assert.assertEquals(tableHeaderNames.get(0), inputData.get("nameColumnName"));
        Assert.assertEquals(tableHeaderNames.get(1), inputData.get("countColumnName"));
        Assert.assertEquals(tableHeaderNames.get(2), inputData.get("priceColumnName"));
    }

    @Test(groups = {"smock"})
    public void cartHeaderTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getCartLink());
        CartPage cartPage = welcomePage.goToCartPage();
        String pageName = cartPage.getPageName();
        Assert.assertEquals(pageName, "Cart");
    }

    @Test(groups = {"smock"}, dataProvider = "getCartProductsNames")
    public void productsAddedToCartDisplayedTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen5"));
        productListPage.addProductToCar(inputData.get("zen7"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(2));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(2));

        WebElement zen3 = cartPage.findProductByName("zen3");
        WebElement zen5 = cartPage.findProductByName("zen5");
        WebElement zen7 = cartPage.findProductByName("zen7");

        String zen3Count = zen3.findElement(By.xpath("following-sibling::td[@class='ng-binding']")).getText();
        String zen5Count = zen5.findElement(By.xpath("following-sibling::td[@class='ng-binding']")).getText();
        String zen7Count = zen7.findElement(By.xpath("following-sibling::td[@class='ng-binding']")).getText();
        Assert.assertTrue(zen3Count.contains(inputData.get("count")));
        Assert.assertTrue(zen5Count.contains(inputData.get("count")));
        Assert.assertTrue(zen7Count.contains(inputData.get("count")));
    }

    @Test(groups = {"smock"})
    public void emptyCartTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getCartLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getCartLink());
        CartPage cartPage = welcomePage.goToCartPage();
        cartPage.waitForElementToAppear(By.xpath("//tr/td[4][@class='ng-binding']"));
        Assert.assertEquals(cartPage.getCartTotalPrice(), "");
    }


    @Test(groups = {"smock"})
    public void phoneNumberTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getCartLink());
        CartPage cartPage = welcomePage.goToCartPage();
        Assert.assertTrue(cartPage.getPhoneNumber().isDisplayed());
        String placeholder = cartPage.getPhoneNumber().getAttribute("placeholder");
        Assert.assertEquals(placeholder, "Phone Number");
        cartPage.setPhoneNumber("+373123456789");
        Assert.assertEquals(cartPage.getPhoneNumber().getAttribute("value"), "+373123456789");
    }

    @Test(groups = {"smock"})
    public void deliveryAddressTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getCartLink());
        CartPage cartPage = welcomePage.goToCartPage();
        Assert.assertTrue(cartPage.getDeliveryAddress().isDisplayed());
        String placeholder = cartPage.getDeliveryAddress().getAttribute("placeholder");
        Assert.assertEquals(placeholder, "Delivery Address");
        cartPage.setDeliveryAddress("some delivery address");
        Assert.assertEquals(cartPage.getDeliveryAddress().getAttribute("value"), "some delivery address");
    }

    @Test(groups = {"smock"})
    public void orderDetailsTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getCartLink());
        CartPage cartPage = welcomePage.goToCartPage();
        Assert.assertTrue(cartPage.getOrderDetails().isDisplayed());
        Assert.assertEquals(cartPage.getOrderDetails().getText(), "Order details");
    }

    @Test(dataProvider = "getCartProductsNames")
    public void removeFromCartBtnTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen5"));
        productListPage.addProductToCar(inputData.get("zen7"));

        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(2));

        String zen3 = cartPage.findProductByName("zen3").getText();
        String zen5 = cartPage.findProductByName("zen5").getText();
        String zen7 = cartPage.findProductByName("zen7").getText();

        Assert.assertTrue(zen3.contains(inputData.get("zen3")));
        Assert.assertTrue(zen5.contains(inputData.get("zen5")));
        Assert.assertTrue(zen7.contains(inputData.get("zen7")));

        cartPage.getRemoveFromCartBtn().get(1).click();
        cartPage.waitForElementToDisappear(cartPage.getRemoveFromCartBtn().get(1));
        WebElement nullExpected = cartPage.findProductByName("zen5");
        Assert.assertNull(nullExpected);
    }


    @Test(groups = {"smock"}, dataProvider = "getCartProductsNames")
    public void clearCartTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen5"));
        productListPage.addProductToCar(inputData.get("zen7"));

        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(2));

        WebElement zen3 = cartPage.findProductByName("zen3");
        WebElement zen5 = cartPage.findProductByName("zen5");
        WebElement zen7 = cartPage.findProductByName("zen7");

        Assert.assertNotNull(zen3);
        Assert.assertNotNull(zen5);
        Assert.assertNotNull(zen7);

        cartPage.getClearCartBtn().click();
        cartPage.waitForElementToDisappear(zen3);

        zen3 = cartPage.findProductByName("zen3");
        zen5 = cartPage.findProductByName("zen5");
        zen7 = cartPage.findProductByName("zen7");

        Assert.assertNull(zen3);
        Assert.assertNull(zen5);
        Assert.assertNull(zen7);
    }

    @Test(groups = {"smock"}, dataProvider = "getCartProductsNames")
    public void increaseProductCountTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(0));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(0));

        WebElement zen3 = cartPage.findProductByName("zen3");
        WebElement zen3CountCell = zen3.findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
        String zen3CountText = zen3CountCell.getText();
        Assert.assertTrue(zen3CountText.contains(inputData.get("count")));


        cartPage.getProductIncrementBtn().get(0).click();

        boolean isIncBtnStale = ExpectedConditions.stalenessOf(zen3CountCell).apply(driver);
        if (isIncBtnStale) {
            //update count cell after its changed its value
            zen3CountCell = driver.findElements(By.xpath("//td[1][@class='ng-binding']")).get(0)
                    .findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
            zen3CountText = zen3CountCell.getText();
            Assert.assertTrue(zen3CountText.contains(inputData.get("countIncreased")));
        }
    }

    @Test(groups = {"smock"}, dataProvider = "getCartProductsNames")
    public void decreaseProductCountTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen3"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(0));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(0));

        WebElement zen3 = cartPage.findProductByName("zen3");
        WebElement zen3CountCell = zen3.findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
        String zen3CountText = zen3CountCell.getText();
        Assert.assertTrue(zen3CountText.contains(inputData.get("countSecond")));


        cartPage.getProductDecrementBtn().get(0).click();

        boolean isIncBtnStale = ExpectedConditions.stalenessOf(zen3CountCell).apply(driver);
        if (isIncBtnStale) {
            //update count cell after its changed its value
            zen3CountCell = driver.findElements(By.xpath("//td[1][@class='ng-binding']")).get(0)
                    .findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
            zen3CountText = zen3CountCell.getText();
            Assert.assertTrue(zen3CountText.contains(inputData.get("countDecreased")));
        }
    }

    @Test(dataProvider = "getCartProductsNames")
    public void minDecreaseValueProductCountTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(0));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(0));

        WebElement zen3 = cartPage.findProductByName("zen3");
        WebElement zen3CountCell = zen3.findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
        String zen3CountText = zen3CountCell.getText();
        Assert.assertTrue(zen3CountText.contains(inputData.get("count")));

        //click on decrease btn when count is 1 to be sure that count will not go under value 1
        cartPage.getProductDecrementBtn().get(0).click();


        boolean isIncBtnStale = ExpectedConditions.stalenessOf(zen3CountCell).apply(driver);
        if (isIncBtnStale) {
            //update count cell after its changed its value
            zen3CountCell = driver.findElements(By.xpath("//td[1][@class='ng-binding']")).get(0)
                    .findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
            zen3CountText = zen3CountCell.getText();
            Assert.assertTrue(zen3CountText.contains(inputData.get("countDecreased")));
        }
    }

    @Test(dataProvider = "getCartProductsNames")
    public void totalPriceCalculation(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        String productPrice = productListPage.getPriceProduct(productListPage.getProductsByName(inputData.get("zen3")).get());
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen3"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(0));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(0));

        WebElement zen3 = cartPage.findProductByName("zen3");
        WebElement zen3CountCell = zen3.findElement(By.xpath("following-sibling::td[@class='ng-binding']"));
        String zen3CountText = zen3CountCell.getText();
        List<String> countArray = Arrays.asList(zen3CountText.split(" "));
        int totalPriceCalculated = Integer.parseInt(productPrice) * Integer.parseInt(countArray.get(1));
        Assert.assertEquals(Integer.parseInt(cartPage.getCartTotalPrice()), totalPriceCalculated);
    }

    @Test(dataProvider = "getCartProductsNames")
    public void createOrderUnauthorized(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen3"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(0));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(0));

        cartPage.createOrder(inputData.get("phoneNumber"), inputData.get("deliveryAddress"));
        String alertText = driver.switchTo().alert().getText();
        Assert.assertEquals(alertText, inputData.get("alertMessage"));
        driver.switchTo().alert().accept();
    }

    @Test(dataProvider = "getCartProductsNames")
    public void createOrderAuthorized(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp(inputData.get("login"), inputData.get("password"));
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getAddToCartBtns().get(4));
        productListPage.waitForElementToBeClickable(productListPage.getAddToCartBtns().get(4));
        productListPage.addProductToCar(inputData.get("zen3"));
        productListPage.addProductToCar(inputData.get("zen3"));
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToAppear(cartPage.getRemoveFromCartBtn().get(0));
        cartPage.waitForElementToBeClickable(cartPage.getRemoveFromCartBtn().get(0));
        WebElement zen3 = cartPage.findProductByName("zen3");
        String totalPrice = cartPage.getCartTotalPrice();
        cartPage.createOrder(inputData.get("phoneNumber"), inputData.get("deliveryAddress"));
        cartPage.waitForElementToDisappear(zen3);
        zen3 = cartPage.findProductByName("zen3");
        Assert.assertNull(zen3);
        OrdersPage ordersPage = cartPage.goToOrdersPage();
        List<String> orderDetailsByOrderId = ordersPage.getOrderDetailsByOrderId("1");
        orderDetailsByOrderId.forEach(System.out::println);
        Assert.assertEquals(orderDetailsByOrderId.get(1), inputData.get("deliveryAddress"));
        Assert.assertEquals(orderDetailsByOrderId.get(2), inputData.get("phoneNumber"));
        Assert.assertEquals(orderDetailsByOrderId.get(3), totalPrice);
    }


    @DataProvider
    public Object[][] getCartTableHeader() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//cartTitles.json");
        return new Object[][]{{jsonData.get(0)}};
    }

    @DataProvider
    public Object[][] getCartProductsNames() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//cartTitles.json");
        return new Object[][]{{jsonData.get(1)}};
    }
}

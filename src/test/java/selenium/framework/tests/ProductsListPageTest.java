package selenium.framework.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.framework.data.DataReader;
import selenium.framework.page_objects.CartPage;
import selenium.framework.page_objects.ProductListPage;
import selenium.framework.page_objects.WelcomePage;
import selenium.framework.test_components.BaseTest;
import selenium.framework.test_components.Retry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductsListPageTest extends BaseTest {

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    private void productsCountOnPageTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        long count = productListPage.getProductsOnPage().size();
        Assert.assertEquals(count, 5);
    }

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    private void productsCountOnPageNotLoggedInTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        long count = productListPage.getProductsOnPage().size();
        Assert.assertEquals(count, 5);
    }

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    public void pageNavigationPresentTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        int size = productListPage.getPagesBtns().size();
        Assert.assertEquals(size, 3);
    }

    @Test(retryAnalyzer = Retry.class)
    public void pageNavigationPresentNotLoggedInTest() {
        WelcomePage welcomePage = launchApplication();
        ProductListPage productListPage = welcomePage.goToProductsPage();
        int size = productListPage.getPagesBtns().size();
        Assert.assertEquals(size, 3);
    }

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    public void productTitleFilterTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle("hdd");
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 1);
    }

    @Test(retryAnalyzer = Retry.class)
    public void productTitleFilterNotLoggedInTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle("hdd");
        productListPage.getApplyFiltersButton().click();
        productListPage.waitForElementToBeClickable(By.xpath("//td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 1);
    }

    @Test(retryAnalyzer = Retry.class)
    public void productTitleFilterPartTest() throws InterruptedException {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        Thread.sleep(500);
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle("zen");
        productListPage.getApplyFiltersButton().click();
        productListPage.waitForElementToBeClickable(By.xpath("//tr/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        System.out.println(size);
        Assert.assertEquals(size, 4);
    }

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    public void productTitleFilterPartNotLoggedInTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle("zen");
        productListPage.getApplyFiltersButton().click();
        productListPage.waitForElementToAppear(By.xpath("//tr/td[2][@class='ng-binding']"));
        productListPage.waitForElementToBeClickable(By.xpath("//tr/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        System.out.println(size);
        Assert.assertEquals(size, 4);
    }

    @Test(retryAnalyzer = Retry.class)
    public void productTitleFilterMixedTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(productListPage.getFilterTitle());
        productListPage.setFilterTitle("ZeN");
        productListPage.applyFilters();
        productListPage.waitForElementToAppear(By.xpath("//tr/td[2][@class='ng-binding']"));
        productListPage.waitForElementToBeClickable(By.xpath("//tr/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 4);
    }

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    public void productTitleFilterMixedNotLoggedInTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle("ZeN");
        productListPage.getApplyFiltersButton().click();
        productListPage.waitForElementToAppear(By.xpath("//tr/td[2][@class='ng-binding']"));
        productListPage.waitForElementToBeClickable(By.xpath("//tr/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 4);
    }

    @Test(dataProvider = "getProductPriceFilter", retryAnalyzer = Retry.class)
    public void productMinPriceLoggedInTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(By.id("filterMinPrice"));
        productListPage.setFilterMinPrice(inputData.get("price"));
        productListPage.applyFilters();
        productListPage.waitForElementToAppear(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 5);
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price >= Integer.parseInt(inputData.get("price")));
    }

    @Test(dataProvider = "getProductPriceFilter", groups = {"smock"}, retryAnalyzer = Retry.class)
    public void productMinPriceNotLoggedInTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(By.id("filterMinPrice"));
        productListPage.setFilterMinPrice(inputData.get("price"));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 5);
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price >= Integer.parseInt(inputData.get("price")));
    }


    @Test(dataProvider = "getProductPriceFilter", retryAnalyzer = Retry.class)
    public void productMaxPriceLoggedInTest(HashMap<String, String> inputData) throws InterruptedException {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(By.id("filterMaxPrice"));
        productListPage.setFilterMaxPrice(inputData.get("price"));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 5);
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price <= Integer.parseInt(inputData.get("price")));

    }

    @Test(dataProvider = "getProductPriceFilter", groups = {"smock"}, retryAnalyzer = Retry.class)
    public void productMaxPriceNotLoggedInTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToAppear(By.id("filterMaxPrice"));
        productListPage.setFilterMaxPrice(inputData.get("price"));
        productListPage.applyFilters();
        productListPage.waitForElementToAppear(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        int size = productListPage.getProductsOnPage().size();
        Assert.assertEquals(size, 5);
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price <= Integer.parseInt(inputData.get("price")));
    }

    @Test(dataProvider = "getProductPriceWithTextFilter", retryAnalyzer = Retry.class)
    public void minPriceTextValuesTestLoggedIn(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(By.id("filterMinPrice"));
        productListPage.setFilterMinPrice(inputData.get("givenValue"));
        String value = productListPage.getFilterMinPrice().getAttribute("value");
        Assert.assertTrue(value.equalsIgnoreCase(inputData.get("expectedValue")));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price >= Integer.parseInt(inputData.get("price")));
    }

    @Test(dataProvider = "getProductPriceWithTextFilter", groups = {"smock"}, retryAnalyzer = Retry.class)
    public void minPriceTextValuesTestNotLoggedIn(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(By.id("filterMinPrice"));
        productListPage.setFilterMinPrice(inputData.get("givenValue"));
        String value = productListPage.getFilterMinPrice().getAttribute("value");
        Assert.assertTrue(value.equalsIgnoreCase(inputData.get("expectedValue")));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price >= Integer.parseInt(inputData.get("price")));
    }


    @Test(dataProvider = "getProductPriceWithTextFilter", retryAnalyzer = Retry.class)
    public void maxPriceTextValuesTestLoggedIn(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.loginToApp("bob", "100");
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(By.id("filterMaxPrice"));
        productListPage.setFilterMaxPrice(inputData.get("givenValue"));
        String value = productListPage.getFilterMaxPrice().getAttribute("value");
        Assert.assertTrue(value.equalsIgnoreCase(inputData.get("expectedValue")));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price <= Integer.parseInt(inputData.get("price")));
    }

    @Test(dataProvider = "getProductPriceWithTextFilter", groups = {"smock"}, retryAnalyzer = Retry.class)
    public void maxPriceTextValuesTestNotLoggedIn(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(By.id("filterMaxPrice"));
        productListPage.setFilterMaxPrice(inputData.get("givenValue"));
        String value = productListPage.getFilterMaxPrice().getAttribute("value");
        Assert.assertTrue(value.equalsIgnoreCase(inputData.get("expectedValue")));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price <= Integer.parseInt(inputData.get("price")));
    }

    @Test(groups = {"smock"})
    public void pageTitleTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToAppear(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        Assert.assertTrue(productListPage.getPageName().equalsIgnoreCase("Products list"));
    }

    @Test(dataProvider = "getProductsTableHeader", groups = {"smock"}, retryAnalyzer = Retry.class)
    public void productsTableHeader(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        List<WebElement> tableHeader = driver.findElements(By.xpath("//thead/tr/td"));
        List<String> tableHeaderNames = new ArrayList<>();
        for (WebElement e : tableHeader) {
            String columnName = e.getText();
            tableHeaderNames.add(columnName);
        }
        Assert.assertEquals(tableHeaderNames.get(0), inputData.get("idColumnName"));
        Assert.assertEquals(tableHeaderNames.get(1), inputData.get("nameColumnName"));
        Assert.assertEquals(tableHeaderNames.get(2), inputData.get("priceColumnName"));
    }

    @Test(retryAnalyzer = Retry.class)
    public void paginationLinksValidationTest() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        List<WebElement> pagesBtns = productListPage.getPagesBtns();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[5]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String pageOneFirstElementName = firstElement.getText();
        productListPage.waitForElementToBeClickable(pagesBtns.get(1));
        pagesBtns.get(1).click();
        productListPage.waitForElementToBeClickable(pagesBtns.get(1));
        productListPage.waitForElementToBeClickable(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        WebElement secondElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String pageTwoFirstElementName = secondElement.getText();
        productListPage.waitForElementToBeClickable(pagesBtns.get(2));
        pagesBtns.get(2).click();
        productListPage.waitForElementToBeClickable(pagesBtns.get(2));
        productListPage.waitForElementToBeClickable(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        WebElement thirdElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String pageThreeFirstElementName = thirdElement.getText();
        Assert.assertNotEquals(pageOneFirstElementName, pageTwoFirstElementName);
        Assert.assertNotEquals(pageTwoFirstElementName, pageThreeFirstElementName);
    }

    @Test(groups = {"smock"}, retryAnalyzer = Retry.class)
    public void addProductToCart() {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.addProductToCar("zen3");
        productListPage.waitForElementToAppear(productListPage.getCartLink());
        productListPage.waitForElementToBeClickable(productListPage.getCartLink());
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.waitForElementToBeClickable(By.xpath("//td[1][@class='ng-binding']"));
        List<WebElement> cartProductsName = cartPage.getCartProductsName();
        WebElement cartProduct = cartProductsName.stream().filter(p -> p.getText().contains("zen3"))
                .findFirst()
                .orElse(null);
        Assert.assertTrue(cartProduct.getText().contains("zen3"));
    }

    @Test(dataProvider = "getMixedFiltersTitleMinData", retryAnalyzer = Retry.class)
    public void mixedFiltersTextMinTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle(inputData.get("title"));
        productListPage.setFilterMinPrice(inputData.get("minPrice"));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        String productName = productListPage.getProductsByName(inputData.get("title")).get().getText();
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price >= Integer.parseInt(inputData.get("minPrice")));
        Assert.assertTrue(productName.contains(inputData.get("title")));
    }

    @Test(dataProvider = "getMixedFiltersTitleMaxData", retryAnalyzer = Retry.class)
    public void mixedFiltersTextMaxTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterTitle());
        productListPage.setFilterTitle(inputData.get("title"));
        productListPage.setFilterMaxPrice(inputData.get("maxPrice"));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        String productName = productListPage.getProductsByName(inputData.get("title")).get().getText();
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price <= Integer.parseInt(inputData.get("maxPrice")));
        Assert.assertTrue(productName.contains(inputData.get("title")));
    }

    @Test(dataProvider = "getMixedFiltersMinMaxData", retryAnalyzer = Retry.class)
    public void mixedFiltersMinMaxTest(HashMap<String, String> inputData) {
        WelcomePage welcomePage = launchApplication();
        welcomePage.waitForElementToBeClickable(welcomePage.getProductsLink());
        ProductListPage productListPage = welcomePage.goToProductsPage();
        productListPage.waitForElementToBeClickable(productListPage.getFilterMinPrice());
        productListPage.setFilterMinPrice(inputData.get("minPrice"));
        productListPage.setFilterMaxPrice(inputData.get("maxPrice"));
        productListPage.applyFilters();
        productListPage.waitForElementToBeClickable(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        WebElement firstElement = driver.findElement(By.xpath("//tr[1]/td[2][@class='ng-binding']"));
        String priceProduct = productListPage.getPriceProduct(firstElement);
        int price = Integer.parseInt(priceProduct);
        Assert.assertTrue(price <= Integer.parseInt(inputData.get("maxPrice")));
        Assert.assertTrue(price >= Integer.parseInt(inputData.get("minPrice")));
    }


    @DataProvider
    public Object[][] getMixedFiltersTitleMinData() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//productPriceFilter.json");
        return new Object[][]{{jsonData.get(3)}};
    }

    @DataProvider
    public Object[][] getMixedFiltersTitleMaxData() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//productPriceFilter.json");
        return new Object[][]{{jsonData.get(4)}};
    }

    @DataProvider
    public Object[][] getMixedFiltersMinMaxData() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//productPriceFilter.json");
        return new Object[][]{{jsonData.get(5)}};
    }


    @DataProvider
    public Object[][] getProductPriceFilter() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//productPriceFilter.json");
        return new Object[][]{{jsonData.get(0)}};
    }

    @DataProvider
    public Object[][] getProductPriceWithTextFilter() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//productPriceFilter.json");
        return new Object[][]{{jsonData.get(1)}};
    }

    @DataProvider
    public Object[][] getProductsTableHeader() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> jsonData = dataReader.getJSONData(System.getProperty("user.dir") + "//src//test//java//selenium//framework//data//jsons//productPriceFilter.json");
        return new Object[][]{{jsonData.get(2)}};
    }

}

package selenium.framework.abstract_components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.framework.page_objects.*;

import java.time.Duration;

public class AbstractComponents {
    WebDriver driver;

    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "navLoginField")
    WebElement usernameField;

    @FindBy(id = "navPasswordField")
    WebElement passwordField;

    @FindBy(id = "navLoginSubmit")
    WebElement loginBtn;

    @FindBy(xpath = "//ul/li[@class='nav-item'][5]")
    WebElement registerLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][1]")
    WebElement welcomeLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][2]")
    WebElement productsListLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][3]")
    WebElement cartLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][4]")
    WebElement ordersLink;
    @FindBy(xpath = "//ul/li[@class='nav-item'][5]")
    WebElement logoutBtn;

    public void waitForElementToAppear(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public CartPage goToCartPage(){
        cartLink.click();
        CartPage cartPage = new CartPage(driver);
        return cartPage;
    }
    public WelcomePage goToWelcomePage(){
        welcomeLink.click();
        WelcomePage welcomePage = new WelcomePage(driver);
        return welcomePage;
    }

    public OrdersPage goToOrdersPage(){
        ordersLink.click();
        OrdersPage ordersPage = new OrdersPage(driver);
        return ordersPage;
    }
    public ProductListPage goToProductsPage(){
        productsListLink.click();
        ProductListPage productListPage = new ProductListPage(driver);
        return productListPage;
    }

    public RegistrationPage goToRegistrationPage(){
        registerLink.click();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        return registrationPage;
    }

    public WelcomePage loginToApp(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();
        return new WelcomePage(driver);
    }
    public void logout(){
        logoutBtn.click();
    }

}

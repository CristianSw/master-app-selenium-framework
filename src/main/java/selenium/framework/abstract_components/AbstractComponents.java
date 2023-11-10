package selenium.framework.abstract_components;

import lombok.Getter;
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
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "navLoginField")
    WebElement usernameField;

    @FindBy(id = "navPasswordField")
    WebElement passwordField;

    @FindBy(id = "navLoginSubmit")
    WebElement loginBtn;

    @FindBy(xpath = "//ul/li[@class='nav-item'][5]/a")
    WebElement registerLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][1]/a")
    WebElement welcomeLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][2]/a")
    WebElement productsListLink;

    @FindBy(xpath = "//ul/li[@class='nav-item'][3]/a")
    WebElement cartLink;

    @Getter
    @FindBy(xpath = "//ul/li[@class='nav-item'][4]/a")
    WebElement ordersLink;
    @FindBy(xpath = "//ul/li[@class='nav-item'][5]")
    WebElement logoutBtn;

    public void waitForElementToAppear(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToAppear(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }
    public void waitForElementToBeClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public void waitForElementToBeClickable(By locator){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementToDisappear(By locator){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public void waitForElementToDisappear(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForTextToBePresent(By locator, String value){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.textToBePresentInElementValue(locator,value));
    }
    public void waitForTextToBePresent(WebElement element, String value){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.textToBePresentInElementValue(element,value));
    }

    public CartPage goToCartPage() {
        cartLink.click();
        return new CartPage(driver);
    }

    public WelcomePage goToWelcomePage() {
        welcomeLink.click();
        return new WelcomePage(driver);
    }

    public OrdersPage goToOrdersPage() {
        ordersLink.click();
        return new OrdersPage(driver);
    }

    public ProductListPage goToProductsPage() {
        productsListLink.click();
        return new ProductListPage(driver);
    }

    public RegistrationPage goToRegistrationPage() {
        registerLink.click();
        return new RegistrationPage(driver);
    }

    public WelcomePage loginToApp(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();
        return new WelcomePage(driver);
    }

    public void logout() {
        logoutBtn.click();
    }

    public void setLoginField(String username) {
        usernameField.sendKeys(username);
    }

    public void setPasswordField(String password) {
        passwordField.sendKeys(password);
    }

    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }

    public WebElement getProductsLink() {
        return productsListLink;
    }
    public WebElement getMarketLink() {
        return welcomeLink;
    }
    public WebElement getCartLink() {
        return cartLink;
    }
    public WebElement getRegisterLink() {
        return registerLink;
    }

    public WebElement getLogoutBtn(){
        return logoutBtn;
    }

    public String getPageURL(){
        return driver.getCurrentUrl();
    }

}

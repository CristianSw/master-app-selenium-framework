package selenium.framework.page_objects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

import java.util.List;

public class CartPage extends AbstractComponents {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h3")
    private WebElement pageName;

    @Getter
    @FindBy(xpath = "//tr/td[2]/button[1]")
    private List<WebElement> productDecrementBtn;

    @Getter
    @FindBy(xpath = "//tr/td[2]/button[2]")
    private List<WebElement> productIncrementBtn;

    @Getter
    @FindBy(id = "clear-cart-btn")
    private WebElement clearCartBtn;

    @Getter
    @FindBy(id = "remove-from-cart")
    private List<WebElement> removeFromCartBtn;

    @Getter
    @FindBy(id = "phone-number")
    private WebElement phoneNumber;

    @Getter
    @FindBy(id = "delivery-address")
    private WebElement deliveryAddress;

    @Getter
    @FindBy(id = "create-order-enabled")
    private WebElement createOrderAuthBtn;

    @Getter
    @FindBy(id = "create-order-disabled")
    private WebElement createOrderUnAuthBtn;

    @Getter
    @FindBy(xpath = "//td[1][@class='ng-binding']")
    private List<WebElement> cartProductsName;

    @FindBy(xpath = "//tr/td[4][@class='ng-binding']")
    private WebElement cartTotalPrice;

    @Getter
    @FindBy(xpath = "//h3[2]")
    private WebElement orderDetails;

    private final By incrementBy = By.xpath("//td[2]/button[2]");
    private final By decrementBy = By.xpath("//td[2]/button[1]");
    private final By removeFromCartBy = By.xpath("//td[4]/button[@id='remove-from-cart']");

    public String getPageName() {
        return pageName.getText();
    }

    public WebElement findProductByName(String productName) {
        List<WebElement> elements = driver.findElements(By.xpath("//td[1][@class='ng-binding']"));
        return elements.stream()
                .filter(p -> p.getText().contains(productName))
                .findFirst()
                .orElse(null);
    }

    public void incrementProductCountByName(String productName) {
        WebElement product = findProductByName(productName);
        product.findElement(incrementBy).click();
    }

    public void decrementProductCountByName(String productName) {
        WebElement product = findProductByName(productName);
        product.findElement(decrementBy).click();
    }

    public void clearCart() {
        clearCartBtn.click();
    }

    public void removeFromCartByproductName(String productName) {
        WebElement product = findProductByName(productName);
        product.findElement(removeFromCartBy).click();
    }

    public void setPhoneNumber(String phoneNumberToSet) {
        phoneNumber.sendKeys(phoneNumberToSet);
    }

    public void setDeliveryAddress(String deliveryAddressToSet) {
        deliveryAddress.sendKeys(deliveryAddressToSet);
    }

    public void createOrderClick() {
        boolean auth = createOrderAuthBtn.isDisplayed();
        boolean unAuth = createOrderUnAuthBtn.isDisplayed();
        if (auth){
            createOrderAuthBtn.click();
        }else if (unAuth){
            createOrderUnAuthBtn.click();
        }
    }

    public void createOrder(String phone, String address) {
        setPhoneNumber(phone);
        setDeliveryAddress(address);
        createOrderClick();
    }

    public String getCartTotalPrice(){
        return cartTotalPrice.getText();
    }


}

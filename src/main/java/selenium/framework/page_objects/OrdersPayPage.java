package selenium.framework.page_objects;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

public class OrdersPayPage extends AbstractComponents {
    WebDriver driver;

    public OrdersPayPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName =  "h3")
    private WebElement pageName;
    @FindBy(id = "order-id")
    private WebElement orderId;
    @FindBy(id = "order-price")
    private WebElement orderPrice;
    @FindBy(id = "order-address")
    private WebElement orderAddress;
    @FindBy(id = "order-phone")
    private WebElement orderPhone;
    @FindBy(xpath = "//h3[2]")
    private WebElement payText;

    @Getter
    @FindBy(css = "div[class*='paypal-button-number-0']")
    private WebElement payPalBtn;

    @Getter
    @FindBy(css = ".paypal-button-text")
    private WebElement debitOrCreditCardBtn;

    @Getter
    @FindBy(id = "buttons-container")
    private WebElement buttonContainer;

    public String getPageName(){
        return pageName.getText();
    }
    public String getOrderId(){
        return orderId.getText();
    }
    public String getOrderPrice(){
        return orderPrice.getText();
    }
    public String getOrderAddress(){
        return orderAddress.getText();
    }
    public String getOrderPhone(){
        return orderPhone.getText();
    }
    public String getPayText(){
        return payText.getText();
    }
    public void clickPayPalBtn(){
        payPalBtn.click();
    }
    public void clickDebitOrCreditCardBtn(){
        debitOrCreditCardBtn.click();
    }

}

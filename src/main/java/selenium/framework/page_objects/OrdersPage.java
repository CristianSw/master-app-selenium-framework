package selenium.framework.page_objects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

import java.util.ArrayList;
import java.util.List;

public class OrdersPage extends AbstractComponents {
    WebDriver driver;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h3")
    private WebElement pageName;
    @Getter
    @FindBy(id = "button-pay")
    private List<WebElement> buttonPay;

    @FindBy(xpath = "//tr/td[1][@class='ng-binding']")
    private List<WebElement> ordersById;

    @Getter
    @FindBy(xpath = "//thead/tr/td")
    private List<WebElement> orderTableHeader;

    private final By deliveryAddressBy = By.xpath("//td[2][@class='ng-binding']");
    private final By phoneBy = By.xpath("//td[3][@class='ng-binding']");
    private final By priceBy = By.xpath("//td[4][@class='ng-binding']");

    public String getPageName() {
        return pageName.getText();
    }

    public OrdersPayPage clickPayBtn(int id) {
        buttonPay.get(id).click();
        return new OrdersPayPage(driver);
    }

    public List<String> getOrderDetailsByOrderId(String orderId) {
        WebElement order = ordersById.stream()
                .filter(o -> o.getText().equalsIgnoreCase(orderId))
                .findFirst()
                .orElse(null);
        List<String> orderDetails = new ArrayList<>();
        if (order != null) {
            orderDetails.add(order.getText());
            orderDetails.add(order.findElement(deliveryAddressBy).getText());
            orderDetails.add(order.findElement(phoneBy).getText());
            orderDetails.add(order.findElement(priceBy).getText());
        }
        return orderDetails;
    }

}



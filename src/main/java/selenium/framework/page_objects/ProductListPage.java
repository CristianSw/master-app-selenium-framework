package selenium.framework.page_objects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

import java.util.List;
import java.util.Optional;

public class ProductListPage extends AbstractComponents {
    WebDriver driver;

    public ProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Getter
    @FindBy(tagName = "h3")
    private WebElement pageName;

    @Getter
    @FindBy(id = "filterTitle")
    private WebElement filterTitle;

    @Getter
    @FindBy(id = "filterMinPrice")
    private WebElement filterMinPrice;

    @Getter
    @FindBy(id = "filterMaxPrice")
    private WebElement filterMaxPrice;

    @Getter
    @FindBy(id = "apply-filters")
    private WebElement applyFiltersButton;

    @Getter
    @FindBy(xpath = "//tr/td[2][@class='ng-binding']")
    private List<WebElement> productsOnPage;

    @Getter
    @FindBy(css = ".btn-primary")
    private List<WebElement> addToCartBtns;

    @Getter
    @FindBy(xpath = "//li/button[@class='page-link ng-binding']")
    private List<WebElement> pagesBtns;


    private final By priceBy = By.xpath("//tr/td[2][@class='ng-binding']/following-sibling::td[@class='ng-binding']");
    private final By addToCartBy = By.xpath("//td[4]/button");

    public String getPageName() {
        return pageName.getText();
    }

    public void setFilterTitle(String title) {
        filterTitle.sendKeys(title);
    }

    public void setFilterMinPrice(String price) {
        filterMinPrice.sendKeys(price);
    }

    public void setFilterMaxPrice(String price) {
        filterMaxPrice.sendKeys(price);
    }

    public void applyFilters() {
        applyFiltersButton.click();
    }

    public Optional<WebElement> getProductsByName(String productName) {
        List<WebElement> productNameList = driver.findElements(By.xpath("//td[2][@class='ng-binding']"));
        Optional<WebElement> element = productNameList.stream()
                .filter(s -> s.getText().contains(productName))
                .findFirst();
        return element;
    }

    public String getPriceProduct(WebElement s) {
        return s.findElement(By.xpath("following-sibling::td[1]")).getText();
    }

    public void addProductToCar(String productName) {
        WebElement webElement = getProductsByName(productName).get();
        webElement.findElement(By.xpath("following-sibling::td[2]/button")).click();
    }


    public void navigateToPage(String pageIndex) {
        pagesBtns.stream()
                .filter(p -> p.getText().equalsIgnoreCase(pageIndex))
                .findFirst().ifPresent(WebElement::click);
    }


}

package selenium.framework.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

import java.util.List;

public class ProductListPage extends AbstractComponents {
    WebDriver driver;

    public ProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h3")
    private WebElement pageName;
    @FindBy(id = "filterTitle")
    private WebElement filterTitle;
    @FindBy(id = "filterMinPrice")
    private WebElement filterMinPrice;
    @FindBy(id = "filterMaxPrice")
    private WebElement filterMaxPrice;
    @FindBy(css = ".btn-outline-warning")
    private WebElement applyFiltersButton;
    @FindBy(xpath = "//tr/td[2][@class='ng-binding']")
    private List<WebElement> productsOnPage;

    @FindBy(css = ".btn-primary")
    private List<WebElement> addToCartBtns;
    @FindBy(xpath = "//li/button[@class='page-link ng-binding']")
    private List<WebElement> pagesBtns;


    private final By priceBy = By.xpath("//following-sibling::td[@class='ng-binding']");
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

    public WebElement getProductsByName(String productName) {
        return productsOnPage.stream()
                .filter(p -> p.getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);
    }

    public String getProductPriceByName(String productName) {
        WebElement product = getProductsByName(productName);
        return product.findElement(priceBy).getText();
    }

    public void addProductToCartByName(String productName) {
        WebElement product = getProductsByName(productName);
        product.findElement(addToCartBy).click();
    }

    public void navigateToPage(String pageIndex) {
        pagesBtns.stream()
                .filter(p -> p.getText().equalsIgnoreCase(pageIndex))
                .findFirst().ifPresent(WebElement::click);
    }
}

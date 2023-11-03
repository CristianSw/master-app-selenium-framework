package selenium.framework.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

public class WelcomePage extends AbstractComponents {
    WebDriver driver;

    public WelcomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "greeting")
    private WebElement greeting;

    @FindBy(id = "market-name")
    private WebElement marketName;

    public String getMarketName() {
        return marketName.getText();
    }

    public String getGreeting() {
        return greeting.getText();
    }

    public void goToPage(){
        driver.get("http://localhost:3000");
    }
}

package selenium.framework.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.framework.abstract_components.AbstractComponents;

public class RegistrationPage extends AbstractComponents {
    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userUsername")
    private WebElement userUsername;
    @FindBy(id = "usernameHelp")
    private WebElement usernameHelp;
    @FindBy(id = "userEmail")
    private WebElement userEmail;
    @FindBy(id = "emailHelp")
    private WebElement emailHelp;
    @FindBy(id = "userPasswd1")
    private WebElement userPassword;
    @FindBy(id = "userConfirmPasswd")
    private WebElement userConfirmPassword;
    @FindBy(css = ".btn-primary")
    private WebElement registerBtn;


    public void setUserUsername(String username) {
        userUsername.sendKeys(username);
    }

    public void setUserPassword(String password) {
        userPassword.sendKeys(password);
    }

    public void setUserEmail(String email) {
        userEmail.sendKeys(email);
    }

    public void setUserConfirmPassword(String userConfirmPasswd) {
        userConfirmPassword.sendKeys(userConfirmPasswd);
    }

    public void clickRegisterBtn() {
        registerBtn.click();
    }

    public String getUsernameHelpMessage() {
        return usernameHelp.getText();
    }

    public String getEmailHelpMessage() {
        return emailHelp.getText();
    }

    public WebElement getUsernameHelp() {
        return usernameHelp;
    }

    public WebElement getEmailHelp() {
        return emailHelp;
    }

    public WebElement getUserEmail(){
        return userEmail;
    }

    public WebElement getUserUsername() {
        return userUsername;
    }

    public WebElement getUserPassword() {
        return userPassword;
    }

    public WebElement getUserConfirmPassword() {
        return userConfirmPassword;
    }

    public WebElement getRegisterBtn() {
        return registerBtn;
    }

    public void register(String username, String password, String confirmPassword, String email) {
        setUserUsername(username);
        setUserPassword(password);
        setUserConfirmPassword(confirmPassword);
        setUserEmail(email);
        clickRegisterBtn();
    }
}

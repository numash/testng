package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by numash on 30.11.2016.
 */
public class LoginPage extends BasePage{

    @FindBy(id = "username")
    private WebElement usernameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "logIn")
    private WebElement loginBtn;
    @FindBy(xpath = "//*[@id='username-element']/ul[@class='errors']/li")
    private WebElement usernameErrorMessageField;
    @FindBy(xpath = "//*[@id='password-element']/ul[@class='errors']/li")
    private WebElement passwordErrorMessageField;

    public LoginPage(WebDriver driver){
        super(driver);
    }

    protected String getRelativeUrl() {
        return "auth/login";
    }

    //initialize a new instance and open page
    public static LoginPage loginPage(WebDriver driver){
        LoginPage page = new LoginPage(driver);
        page.open();

        return page;
    }

    public void open(){
        driver.get(getFullUrl());
    }

    public void login(String username, String password){
        setUsername(username);
        setPassword(password);
        clickOnLogin();
    }

    public void setUsername(String username){
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickOnLogin() {
        loginBtn.click();
    }

    public String getUsernameErrorMessage(){
        return usernameErrorMessageField.getText();
    }

    public String getPasswordErrorMessage(){
        return passwordErrorMessageField.getText();
    }
}

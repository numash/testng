package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by numash on 30.11.2016.
 */
public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }

    protected String getRelativeUrl() {
        return "auth/login";
    }

    public void open(){
        driver.get(getFullUrl());
    }

    public String getUrl() {
        return getFullUrl();
    }

    public void login(String username, String password){
        setUsername(username);
        setPassword(password);
        clickOnLogin();
    }

    public void setUsername(String username){
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickOnLogin() {
        WebElement loginBtn = driver.findElement(By.id("logIn"));
        loginBtn.click();
    }

    /*public String getErrorMessage(){
        WebElement errorField = driver.findElement(By.xpath("//ul[@class='errors']/li"));
        return errorField.getText();
    }*/

    public String getUsernameErrorMessage(){
        WebElement errorField = driver.findElement(By.xpath("//*[@id='username-element']/ul[@class='errors']/li"));
        return errorField.getText();
    }

    public String getPasswordErrorMessage(){
        WebElement errorField = driver.findElement(By.xpath("//*[@id='password-element']/ul[@class='errors']/li"));
        return errorField.getText();
    }
}

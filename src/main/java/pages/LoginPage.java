package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by numash on 30.11.2016.
 */
public class LoginPage {

    private WebDriver driver;
    public static final String URL = "http://80.92.229.236:81/auth/login";

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void open(){
        driver.get(URL);
    }

    public void login(String username, String password){

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

    public String getErrorMessage(){
        WebElement errorField = driver.findElement(By.xpath("//ul[@class='errors']/li"));
        return errorField.getText();
    }
}

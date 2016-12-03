package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by numash on 30.11.2016.
 */
public class LoginTests {
    //declare global var
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void beforeMethod(){
        //open poker url
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test
    public void positiveTest(){
        loginPage.login("admin", "123");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after login");
        Assert.assertNotEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "Wrong URL after login");
    }

    @Test
    public void negativeTestWrongPassword(){
        loginPage.login("admin", "321");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering wrong password");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Invalid username or password",
                "Validation error message is not valid when password is wrong.");
    }

    @Test
    public void negativeTestWrongLogin(){

        loginPage.login("notadmin", "123");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering wrong login");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Invalid username or password",
                "Validation error message is not valid when login is wrong.");

    }

    @Test
    public void negativeTestEmptyUsernameField(){

        loginPage.login("", "123");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering empty value in login field");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Value is required and can't be empty",
                "Validation error message is not valid when username field is empty..");

    }

    @Test
    public void negativeTestEmptyPasswordField(){

        loginPage.login("admin", "");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering empty value in password field");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(),
                "Value is required and can't be empty",
                "Validation error message is not valid when password field is empty.");

    }

    @Test
    public void negativeTestEmptyFields(){

        loginPage.login("", "");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering empty values in fields");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Value is required and can't be empty",
                "Validation error message is not valid when username field is empty.");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(),
                "Value is required and can't be empty",
                "Validation error message is not valid when password field is empty.");

    }


    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}

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
    //declare global driver var
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Precondition:
     * 1. Open application Login page
     */
    @BeforeMethod
    public void beforeMethod(){
        //open poker url
        loginPage = LoginPage.loginPage(driver);
    }

    /**
     * Steps to reproduce:
     * 1. Set username to "admin"
     * 2. Set password to "123"
     * 3. Click "Login" button
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Login page URL
     */
    @Test
    public void positiveLoginTest(){
        loginPage.login("admin", "123");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after login.");
        Assert.assertNotEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are still on Login page.");
    }

    /**
     * Steps to reproduce:
     * 1. Set username to "admin"
     * 2. Set password to "321"
     * 3. Click "Login" button
     * 4. Verify that the URL equals to Login page URL
     * 5. Verify that title of the page equals to "Login"
     * 6. Verify that error message "Invalid username or password" appears
     */
    @Test
    public void negativeTestWrongPassword(){
        loginPage.login("admin", "321");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering wrong password.");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Invalid username or password",
                "Validation error message is not valid when password is wrong.");
    }

    /**
     * Steps to reproduce:
     * 1. Set username to "notadmin"
     * 2. Set password to "123"
     * 3. Click "Login" button
     * 4. Verify that the URL equals to Login page URL
     * 5. Verify that title of the page equals to "Login"
     * 6. Verify that error message "Invalid username or password" appears
     */
    @Test
    public void negativeTestWrongLogin(){

        loginPage.login("notadmin", "123");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering wrong login");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Invalid username or password",
                "Validation error message is not valid when login is wrong.");

    }

    /**
     * Steps to reproduce:
     * 1. Set username to ""
     * 2. Set password to "123"
     * 3. Click "Login" button
     * 4. Verify that the URL equals to Login page URL
     * 5. Verify that title of the page equals to "Login"
     * 6. Verify that error message "Value is required and can't be empty" appears
     */
    @Test
    public void negativeTestEmptyUsernameField(){

        loginPage.login("", "123");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering empty value in login field");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Value is required and can't be empty",
                "Validation error message is not valid when username field is empty..");

    }

    /**
     * Steps to reproduce:
     * 1. Set username to "admin"
     * 2. Set password to ""
     * 3. Click "Login" button
     * 4. Verify that the URL equals to Login page URL
     * 5. Verify that title of the page equals to "Login"
     * 6. Verify that error message "Value is required and can't be empty" appears
     */
    @Test
    public void negativeTestEmptyPasswordField(){

        loginPage.login("admin", "");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after entering empty value in password field");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(),
                "Value is required and can't be empty",
                "Validation error message is not valid when password field is empty.");

    }

    /**
     * Steps to reproduce:
     * 1. Set username to ""
     * 2. Set password to ""
     * 3. Click "Login" button
     * 4. Verify that the URL equals to Login page URL
     * 5. Verify that title of the page equals to "Login"
     * 6. Verify that error message "Value is required and can't be empty" under username field appears
     * 7. 6. Verify that error message "Value is required and can't be empty" under password field appears
     */
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

    /**
     * Postcondition:
     * 1. Close browser.
     */
    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}

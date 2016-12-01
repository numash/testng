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
        loginPage.setUsername("admin");
        loginPage.setPassword("123");
        loginPage.clickOnLogin();

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after login");
        Assert.assertNotEquals(driver.getCurrentUrl(), LoginPage.URL, "Wrong URL after login");
    }

    @Test
    public void negativeTestWrongPassword(){
        loginPage.setUsername("admin");
        loginPage.setPassword("321");
        loginPage.clickOnLogin();

        Assert.assertEquals(driver.getCurrentUrl(), LoginPage.URL, "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after unsuccessful login");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password", "Validation error message is not valid.");
    }

    @Test
    public void negativeTestWrong(){
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}

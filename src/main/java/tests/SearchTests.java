package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by numash on 03.12.2016.
 */
public class SearchTests {
    //declare global driver var
    private WebDriver driver;
    private SoftAssert softAssert;

    /**
     * Precondition:
     * 1. Login to the system with "admin" login and "123" password.
     */
    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();

        LoginPage loginPage = LoginPage.loginPage(driver);
        loginPage.login("admin", "123");

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Precondition:
     * 1. Create new instance of SoftAssert
     */
    @BeforeMethod
    public void beforeMethod(){
        softAssert = new SoftAssert();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Player" field to player's username
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void positiveSearchPlayerByUsername(){
        //TODO implement, maybe create player before test?
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Email" field to player's email
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void positiveSearchPlayerByEmail(){
        //TODO implement
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Country" field to player's country
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void positiveSearchPlayerByCountry(){
        //TODO implement
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "City" field to player's city
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void positiveSearchPlayerByCity(){
        //TODO implement
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "First name" field to player's first name
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void positiveSearchPlayerByFirstName(){
        //TODO implement
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Last name" field to player's last name
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void positiveSearchPlayerByLastName(){
        //TODO implement
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

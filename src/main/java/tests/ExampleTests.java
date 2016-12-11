package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by numash on 07.12.2016.
 */
public class ExampleTests extends BaseTests{
    private static WebDriver driver;
    private static LoginPage loginPage;

    @BeforeSuite(alwaysRun = true)
    public void beforeTest(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeMethod (alwaysRun = true)
    public void beforeMethod(){
        //open poker url
        loginPage = LoginPage.loginPage(driver);
    }

    @DataProvider
    public Object[][] loginData(){
        return new Object[][]{
                {"admin", "1234", "Login", "Wrong title after entering wrong password."},
                {"admin2", "123", "Login", "Wrong title after entering wrong password."},
        };
    }

    @Parameters({"usernameParameter", "passwordParameter", "titleParameter"})
    @Test
    public void loginTest(String username, String password, String title){
        loginPage.login(username, password);

        Assert.assertEquals(driver.getTitle(), title, "Wrong title after login.");
        Assert.assertNotEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are still on Login page.");
    }

    @Test (dataProvider = "loginData")
    public void negativeTestWrongPassword(String username, String password, String title, String expectedMessage){
        loginPage.login(username, password);

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), title, expectedMessage);
        Assert.assertEquals(loginPage.getUsernameErrorMessage(),
                "Invalid username or password",
                "Validation error message is not valid when password is wrong.");
    }

    @AfterSuite(alwaysRun = true)
    public void afterTest(){
        driver.quit();
    }
}

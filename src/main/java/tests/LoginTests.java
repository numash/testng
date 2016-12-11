package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by numash on 30.11.2016.
 */
public class LoginTests extends BaseTests {
    private LoginPage loginPage;
    private SoftAssert softAssert;

    /**
     * Precondition:
     * 1. Open application Login page
     */
    @BeforeMethod (alwaysRun = true)
    public void beforeMethod(){
        //open poker url
        loginPage = LoginPage.loginPage(driver);
        softAssert = new SoftAssert();
    }

    /**
     * Steps to reproduce:
     * 1. Set username to "admin"
     * 2. Set password to "123"
     * 3. Click "Login" button
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Login page URL
     */
    @Parameters({"usernameParameter", "passwordParameter", "titleParameter"})
    @Test (groups="positiveLogin", dependsOnGroups = "negativeLogin")
    public void positiveLoginTest(String username, String password, String title){
        loginPage.login(username, password);

        Assert.assertEquals(driver.getTitle(), title, "Wrong title after login.");
        Assert.assertNotEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are still on Login page.");
    }

    @DataProvider
    public Object[][] negativeLoginData(){
        return new Object[][]{
                {"admin", "321", "Login", "Invalid username or password", null},
                {"notadmin", "123", "Login", "Invalid username or password", null},
                {"admin", "", "Login", null, "Value is required and can't be empty"},
                {"", "123", "Login", "Value is required and can't be empty", null},
                {"", "", "Login", "Value is required and can't be empty", "Value is required and can't be empty"}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Fill username field
     * 2. Fill password field
     * 3. Click "Login" button
     * 4. Verify that the URL equals to Login page URL
     * 5. Verify that title of the page equals to "Login"
     * 6. Verify that error message appears
     */
    @Test (groups = "negativeLogin", dataProvider = "negativeLoginData")
    public void negativeLoginTest(String username, String password, String expectedTitle, String expectedUsernameMessage, String expectedPasswordMessage){
        loginPage.login(username, password);

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getFullUrl(), "You are not on login page.");
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after negative login.");

        if (expectedUsernameMessage != null) {
            softAssert.assertEquals(loginPage.getUsernameErrorMessage(),
                    expectedUsernameMessage,
                    "Username validation error message is not valid.");
        }

        if(expectedPasswordMessage != null){
            softAssert.assertEquals(loginPage.getPasswordErrorMessage(),
                    expectedPasswordMessage,
                    "Password validation  error message is not valid.");
        }

        softAssert.assertAll();
    }
}

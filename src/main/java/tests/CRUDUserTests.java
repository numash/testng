package tests;

import entities.PokerPlayer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.InsertOrEditPlayerPage;
import pages.LoginPage;
import pages.PlayersPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by numash on 02.12.2016.
 */
public class CRUDUserTests {
    //declare global var
    private WebDriver driver;
    private SoftAssert softAssert;

    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();
        softAssert = new SoftAssert();

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void beforeMethod(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("admin", "123");
    }

    @Test
    public void positiveCreatePlayerTest(){
        InsertOrEditPlayerPage editPlayerPage = new InsertOrEditPlayerPage(driver, InsertOrEditPlayerPage.insertPlayerUrl);
        editPlayerPage.openInsertPlayerPage();

        PokerPlayer player = PokerPlayer.CreateRandomPokerPlayer();
        editPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating user");
        Assert.assertNotEquals(driver.getCurrentUrl(), InsertOrEditPlayerPage.insertPlayerUrl,
                "Wrong URL after creating user. You are still on the Player-Insert page");

        //create PlayersPage class object
        PlayersPage playersPage = new PlayersPage(driver);
        //open "Players" page
        playersPage.open();
        //search user by username
        playersPage.searchPlayerByUsername(player.getUsername());
        //check user presence in result table
        Assert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()),
                "Table doesn't contain user after creating");

        playersPage.openEditPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = createPokerPlayerFromPage(editPlayerPage);

        //verify user data
        softAssert.assertEquals(actualPlayer, player, "Wrong data after creating player");
        softAssert.assertAll();
    }

    @Test
    public void positiveUpdatePlayerTest(){
        //create player
        InsertOrEditPlayerPage editPlayerPage = new InsertOrEditPlayerPage(driver, InsertOrEditPlayerPage.insertPlayerUrl);
        editPlayerPage.openInsertPlayerPage();
        PokerPlayer player = PokerPlayer.CreateRandomPokerPlayer();
        editPlayerPage.createPlayer(player, "pass_Word68");

        //edit player
        PlayersPage playersPage = new PlayersPage(driver);
        //open "Players" page
        playersPage.open();
        //search user by username
        playersPage.searchPlayerByUsername(player.getUsername());
        //check user presence in result table
        playersPage.openEditPlayerPage(player.getUsername());

        PokerPlayer updatedPlayer = PokerPlayer.CreateRandomPokerPlayer();
        //I can't change username
        updatedPlayer.setUsername(player.getUsername());
        editPlayerPage.updatePlayer(updatedPlayer);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after updating user");
        Assert.assertEquals(driver.getCurrentUrl(), playersPage.getUrl(), "Wrong title after updating user");

        //open "Players" page
        playersPage.open();
        //search user by username
        playersPage.searchPlayerByUsername(updatedPlayer.getUsername());
        //check user presence in result table
        Assert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()),
                "Table doesn't contain user after updating");
        playersPage.openEditPlayerPage(updatedPlayer.getUsername());
        PokerPlayer actualPlayer = createPokerPlayerFromPage(editPlayerPage);

        //verify user data
        softAssert.assertEquals(actualPlayer, updatedPlayer, "Wrong data after updating player");
        softAssert.assertNotEquals(actualPlayer, player, "Player data didn't change");
        softAssert.assertAll();
    }

    @Test
    public void positiveDeletePlayerTest(){
        InsertOrEditPlayerPage createPlayerPage = new InsertOrEditPlayerPage(driver, InsertOrEditPlayerPage.insertPlayerUrl);
        createPlayerPage.openInsertPlayerPage();

        PokerPlayer player = PokerPlayer.CreateRandomPokerPlayer();
        createPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = new PlayersPage(driver);
        playersPage.searchPlayerByUsername(player.getUsername());
        playersPage.deletePlayer(player.getUsername());

        //check flash message
        softAssert.assertNotNull(playersPage.getFlashMessage(), "No flash message after deleting (probably player wasn't deleted)");

        //check result table
        playersPage.searchPlayerByUsername(player.getUsername());
        softAssert.assertFalse(playersPage.doesTableContainPlayer(player.getUsername()), "Player wasn't deleted");
        softAssert.assertAll();
    }

    private PokerPlayer createPokerPlayerFromPage(InsertOrEditPlayerPage page){
        PokerPlayer player = new PokerPlayer(
                page.getUsernameFieldValue(),
                page.getEmailFieldValue(),
                page.getFirstNameFieldValue(),
                page.getLastNameFieldValue(),
                page.getCityFieldValue(),
                page.getCountryValue(),
                page.getAddressFieldValue(),
                page.getPhoneFieldValue(),
                page.getGenderValue(),
                page.getBirthdayFieldValue()
        );
        return player;
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}

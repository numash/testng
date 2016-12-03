package tests;

import entities.PokerPlayer;
import helpers.RandomManager;
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

    /**
     * Test precondition:
     * 1. User "admin" is logged in.
     */
    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("admin", "123");

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Precondition:
     * 1. Open application "Players" page URL
     */
    @BeforeMethod
    public void beforeMethod(){
        softAssert = new SoftAssert();

       // playersPage = new PlayersPage(driver);
        //playersPage.open();
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Player-Edit page URL
     * 6. On the Players page fill the "Player" field with username from step 1. Click "Search"
     * 7. Verify result table contains player.
     * 8. Open player edit page.
     * 9. Verify player data equals to data from step 1.
     */

    @Test
    public void positiveCreatePlayerIsCorrectlySaved(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        /*Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after user creating.");
        Assert.assertNotEquals(driver.getCurrentUrl(), insertPlayerPage.getFullUrl(),
                "Wrong URL after user creating. You are still on the Player-Insert page.");
        */

        //open "Players" page
        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        //search user by username
        //playersPage.searchPlayerByUsername(player.getUsername());

        //Assert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Table doesn't contain user after creating.");


        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = editPlayerPage.readPokerPlayerFromPage();

        softAssert.assertEquals(actualPlayer, player, "Wrong data after creating player.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Open player edit window
     * 3. Update player with new random data
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Player-Edit page URL
     * 6. On the Players page fill the "Player" field with player username. Click "Search"
     * 7. Verify result table contains player.
     * 7. Open player edit page.
     * 8. Verify player data equals to new data.
     */

    @Test
    public void positiveUpdatePlayerTestIsCorrectlySaved(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        updatePlayerData(player);
        editPlayerPage.updatePlayer(player);

        /*Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after updating user.");
        Assert.assertEquals(driver.getCurrentUrl(), playersPage.getFullUrl(), "Wrong url after updating user (you probably still on Player-Edit page).");
*/
        playersPage.refresh();

        //Assert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Table doesn't contain user after updating.");

        editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = editPlayerPage.readPokerPlayerFromPage();

        softAssert.assertEquals(actualPlayer, player, "Wrong data after updating player.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. On the Players page fill the "Player" field with player username. Click "Search"
     * 3. Click on player's "Delete" link.
     * 4. Verify message "Player has been deleted" has appeared
     * 5. On the Players page fill the "Player" field with player username. Click "Search"
     * 6. Verify table doesn't contain player.
     */

    @Test
    public void positiveDeletePlayerTest(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername());

        /*
        //check flash message
        softAssert.assertEquals(playersPage.getFlashMessage(), "Player has been deleted", "No flash message after deleting (probably player wasn't deleted).");
        */

        //check result table
        softAssert.assertFalse(playersPage.doesTableContainPlayer(player.getUsername()), "Player wasn't deleted.");
        softAssert.assertAll();
    }

    /**
     * Test postcondition:
     * 1. Close browser.
     */
    @AfterTest
    public void afterTest(){
        driver.quit();
    }



    //fills poker player fields with random data
    private PokerPlayer createRandomPokerPlayer() {

        RandomManager randomManager = new RandomManager();
        String randomString = randomManager.getRandomString(5);

        return new PokerPlayer(
                "user68_" + randomString,
                "user68_" + randomString + "@gmail.com",
                "first",
                "last",
                "City.",
                "UKRAINE",
                "Address68, " + randomString,
                "+312345678, 890",
                "Male",
                "10-10-1990");
    }

    private void updatePlayerData(PokerPlayer player){
        PokerPlayer randomPlayer = createRandomPokerPlayer();

        player.setEmail(randomPlayer.getEmail());
        player.setFirstname(randomPlayer.getFirstname());
        player.setLastname(randomPlayer.getLastname());
        player.setAddress(randomPlayer.getAddress());
        player.setCity(randomPlayer.getCity());
        player.setCountry(randomPlayer.getCountry());
        player.setPhone(randomPlayer.getPhone());
        player.setGender(randomPlayer.getGender());
        player.setBirthday(randomPlayer.getBirthday());
    }
}

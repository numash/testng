package tests;

import entities.PokerPlayer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
 * Created by numash on 03.12.2016.
 */
public class SearchTests extends BaseTests{
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
    public void searchPlayerByUsername(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByUsername(player.getUsername());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by username returned no results.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Email" field to player's email
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void searchPlayerByEmail(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByEmail(player.getEmail());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getEmail()), "Search by email returned no results.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "City" field to player's city
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void searchPlayerByCity(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByCity(player.getCity());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by city returned no results.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "First name" field to player's first name
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void searchPlayerByFirstName(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByFirstName(player.getFirstname());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by first name returned no results.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Last name" field to player's last name
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Test (groups = "search")
    public void searchPlayerByLastName(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByLastName(player.getLastname());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by last name returned no results.");
    }

    /**
     * Steps to reproduce:
     * 1. Fill the fields on search form with random data
     * 2. Click on "Reset" button
     * 3. Verify all fields are empty
     */
    @Test
    public void resetButtonClearsAllFields(){
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

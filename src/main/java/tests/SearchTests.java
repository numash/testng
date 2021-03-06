package tests;

import entities.PokerPlayer;
import org.testng.annotations.*;
import pages.InsertOrEditPlayerPage;
import pages.LoginPage;
import pages.PlayersPage;

/**
 * Created by numash on 03.12.2016.
 */
public class SearchTests extends BaseTests{
    /**
     * Precondition:
     * 1. Login to the system with "admin" login and "123" password.
     */
    @Parameters({"usernameParameter", "passwordParameter"})
    @BeforeTest (alwaysRun = true, dependsOnGroups = "positiveLogin")
    public void beforeTest(String username, String password){
        LoginPage loginPage = LoginPage.loginPage(driver);
        loginPage.login(username, password);
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Player" field to player's username
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Parameters({"playerPasswordParameter"})
    @Test (groups = "search")
    public void searchPlayerByUsername(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByUsername(player.getUsername());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by username returned no results.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Email" field to player's email
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Parameters({"playerPasswordParameter"})
    @Test (groups = "search")
    public void searchPlayerByEmail(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByEmail(player.getEmail());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getEmail()), "Search by email returned no results.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "City" field to player's city
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Parameters({"playerPasswordParameter"})
    @Test (groups = "search")
    public void searchPlayerByCity(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByCity(player.getCity());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by city returned no results.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "First name" field to player's first name
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Parameters({"playerPasswordParameter"})
    @Test (groups = "search")
    public void searchPlayerByFirstName(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByFirstName(player.getFirstname());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by first name returned no results.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Set "Last name" field to player's last name
     * 3. Click "Search"
     * 4. Verify result table contains player
     */
    @Parameters({"playerPasswordParameter"})
    @Test (groups = "search")
    public void searchPlayerByLastName(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.searchPlayerByLastName(player.getLastname());

        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Search by last name returned no results.");
        softAssert.assertAll();
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
}

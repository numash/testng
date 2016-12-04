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
 *
 * Minimum "Username" field value is 3
 * Maximum "Username" field value is 12
 * Allowed characters are: digits (0-9), letters (A-Z and a-z), underscores, hyphens and periods
 * Minimum "Password" field value is 6
 *
 * To create a user:
 * 1. Open Player-Insert page.
 * 2. Fill the fields with valid random data.
 * 3. Click "Save" button
 */
public class CRUDUserTests {
    //declare global driver var
    private WebDriver driver;
    private SoftAssert softAssert;
    private RandomManager randomManager;

    /**
     * Precondition:
     * 1. Login to the system with "admin" login and "123" password.
     */
    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();
        randomManager = new RandomManager();

        LoginPage loginPage = LoginPage.loginPage(driver);
        loginPage.login("admin", "123");

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //TODO wait until clickable
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
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Player-Edit page URL
     */
    @Test (groups = "createPlayer")
    public void createPlayerRedirectsToPlayersPage(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player.");
        Assert.assertNotEquals(driver.getCurrentUrl(), insertPlayerPage.getFullUrl(),
                "Wrong URL after player creating. You are still on the Player-Insert page.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. On the Players page fill the "Player" field with username from step 2. Click "Search"
     * 5. Verify result table contains player.
     */
    @Test (groups = "createPlayer")
    public void createdPlayerInsertsInPlayersTable(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        //open "Players" page
        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        Assert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Table doesn't contain player after creating.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. On the Players page fill the "Player" field with username from step 2. Click "Search"
     * 5. Open player edit page.
     * 6. Verify player data equals to data from step 2.
     */

    @Test (groups = "createPlayer")
    public void createdPlayerIsCorrectlySaved(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = editPlayerPage.readPokerPlayerFromPage();

        softAssert.assertEquals(actualPlayer, player, "Wrong data after creating player.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 3 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithMinimumUsernameLength(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaAndNumberString(3));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with minimum username length.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 12 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithMaximumUsernameLength(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaAndNumberString(12));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with maximum username length.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains alpha characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithUsernameContainsAlphaCharactersOnly(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaString(8));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with username that contains alpha characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithUsernameContainsNumbersOnly(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomNumberString(8));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with username that contains numbers only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithUsernameContainsAllowedSpecialCharactersOnly(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAllowedSpecialCharacterString(8));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with username that contains allowed special characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value 6 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithMinimumPasswordLength(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(6);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with minimum username length.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Open player edit window
     * 3. Update player with new random data
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Player-Edit page URL
     */
    @Test (groups = "updatePlayer")
    public void positiveUpdatePlayerRedirectsToPlayersPage(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        updatePlayerData(player);
        editPlayerPage.updatePlayer(player);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after updating player.");
        Assert.assertEquals(driver.getCurrentUrl(), playersPage.getFullUrl(), "Wrong url after updating player (you probably still on Player-Edit page).");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Open player edit window
     * 3. Update player with new random data
     * 4. On the Players page fill the "Player" field with player username. Click "Search"
     * 5. Verify result table contains player.
     */

    @Test (groups = "updatePlayer")
    public void positiveUpdatePlayerKeepsPlayerInPlayersTable(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        updatePlayerData(player);
        editPlayerPage.updatePlayer(player);

        playersPage.refresh();
        Assert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Table doesn't contain player after updating.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Open player edit window
     * 3. Update player with new random data
     * 4. On the Players page fill the "Player" field with player username. Click "Search"
     * 5. Open player edit page.
     * 6. Verify player data equals to new data.
     */
    @Test (groups = "updatePlayer")
    public void positiveUpdatePlayerIsCorrectlySaved(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        updatePlayerData(player);
        editPlayerPage.updatePlayer(player);

        playersPage.refresh();
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
     */

    @Test (groups = "deletePlayer")
    public void positiveDeletePlayerShowsMessage(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername());

        //check flash message
        softAssert.assertEquals(playersPage.getFlashMessage(), "Player has been deleted", "No flash message after deleting player (probably player wasn't deleted).");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. On the Players page fill the "Player" field with player username. Click "Search"
     * 3. Click on player's "Delete" link
     * 6. On the Players page fill the "Player" field with player username. Click "Search"
     * 7. Verify table doesn't contain player.
     */
    @Test (groups = "deletePlayer")
    public void positiveDeletePlayerDeletesPlayerFromPlayersTable(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername());
        playersPage.refresh();

        //check result table
        softAssert.assertFalse(playersPage.doesTableContainPlayer(player.getUsername()), "Player wasn't deleted.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 2 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Username is less than 3 characters long" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithUsernameLengthLessThanMinimum(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaAndNumberString(2));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with less than minimum username length (probably player was created).");
        String validationMessage = "Username: '" + player.getUsername() + "' is less than 3 characters long";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating user"
            + "with 2 character username (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 13 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Username is more than 12 characters long" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithUsernameLengthGreaterThanMaximum(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaAndNumberString(13));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with greater than maximum username length (probably player was created).");
        String validationMessage = "Username: '" + player.getUsername() + "' is more than 12 characters long";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating player"
                + "with 13 character username (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 13 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Username is more than 12 characters long" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmptyUsernameField(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername("");

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with empty username (probably player was created).");
        String validationMessage = "Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating player"
                + "with empty username field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains special character
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithUsernameContainsNotAllowedCharacters(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomNotAllowedSpecialCharacterString(8));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with username that contains special characters.");
        String validationMessage = "Username: Contains invalid characters. Allowed ones are: digits (0-9), letters (A-Z and a-z), underscores, hyphens and periods";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating player"
                + "with username contains not allowed characters (probably player was created).");
    }



    /**
     * Postcondition:
     * 1. Close browser.
     */
    @AfterTest
    public void afterTest(){
        driver.quit();
    }

    //fills poker player fields with random data
    private PokerPlayer createRandomPokerPlayer() {

        return new PokerPlayer(
                "user68_" + randomManager.getRandomAlphaAndNumberString(5),
                "user68_" + randomManager.getRandomAlphaAndNumberString(5) + "@gmail.com",
                "first" + randomManager.getRandomAlphaAndNumberString(5),
                "last" + randomManager.getRandomAlphaAndNumberString(5),
                "City.",
                "UKRAINE",
                "Address68, " + randomManager.getRandomAlphaAndNumberString(5),
                "+312345678, 890",
                "Male",
                //"10-10-1990");
                randomManager.getRandomDate());
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

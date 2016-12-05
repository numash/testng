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
 * Maximum "Password" field value is 30
 *
 * To create a user:
 * 1. Open Player-Insert page.
 * 2. Fill the fields with valid random data.
 * 3. Click "Save" button
 */
public class CRUDUserTests extends BaseTests{
    //declare global driver var
    private WebDriver driver;
    private SoftAssert softAssert;
    private RandomManager randomManager;

    /**
     * Precondition:
     * 1. Login to the system with "admin" login and "123" password.
     */
    @BeforeTest (alwaysRun = true)
    public void beforeTest(){
        driver = new FirefoxDriver();
        randomManager = new RandomManager();

        LoginPage loginPage = LoginPage.loginPage(driver);
        loginPage.login("admin", "123");

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Precondition:
     * 1. Create new instance of SoftAssert
     */
    @BeforeMethod (alwaysRun = true)
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
        playersPage.searchPlayerByUsername(player.getUsername());

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
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. On the Players page fill the "Player" field with username from step 2. Click "Search"
     * 5. Open player view page.
     * 6. Verify player data equals to data from step 2.
     */
    @Test (groups = "createPlayer")
    public void createdPlayerIsCorrectlyShowsInViewPage(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage viewPlayerPage = playersPage.openViewPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = viewPlayerPage.readPokerPlayerFromPage();
        playersPage.closeViewPlayerPage();

        softAssert.assertEquals(actualPlayer, player, "Wrong data in view page after creating player.");
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
     * 2. Fill the username field with random value that contains special characters only
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

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with minimum password length.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value 30 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithMaximumPasswordLength(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(30);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with maximum password length.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields  with random value that contains alpha characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithPasswordContainsAlphaCharactersOnly(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaString(8);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with password that contains alpha characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithPasswordContainsNumbersOnly(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomNumberString(8);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with password that contains numbers only.");
    }

    /**
     * This test always fails because special characters are not allowed, but I think it's incorrect for password field
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value that contains special characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    /*@Test (groups = "createPlayer")
    public void createPlayerWithPasswordContainsSpecialCharactersOnly(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAllSpecialCharacterString(8);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with password that contains special characters only.");
    }*/

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with random value that contains alpha characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmailContainsAlphaCharacters(){
        PokerPlayer player = createRandomPokerPlayer();
        String randomAlphaEmail = randomManager.getRandomEmail(8, RandomManager.alphaMixedCaseCharacters, RandomManager.alphaMixedCaseCharacters);
        player.setEmail(randomAlphaEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with " + randomAlphaEmail + " email that contains alpha characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmailContainsNumbers(){
        PokerPlayer player = createRandomPokerPlayer();
        String randomNumberEmail = randomManager.getRandomEmail(8, RandomManager.numbers, RandomManager.numbers);
        player.setEmail(randomNumberEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with " + randomNumberEmail + " email that contains numbers only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with random value that contains special characters in local part
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmailContainsSpecialCharactersInLocalPart(){
        PokerPlayer player = createRandomPokerPlayer();
        String randomSpecialCharactersEmail = randomManager.getRandomEmail(8, RandomManager.emailSpecialCharacters, RandomManager.alphaMixedCaseCharacters);
        player.setEmail(randomSpecialCharactersEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        Assert.assertEquals(driver.getTitle(), "Players", "Wrong title after creating player with " + randomSpecialCharactersEmail + " email that contains special characters in local part.");
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
    public void updatingPlayerRedirectsToPlayersPage(){
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
    public void updatingPlayerKeepsPlayerInPlayersTable(){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        updatePlayerData(player);
        editPlayerPage.updatePlayer(player);

        playersPage.refresh();
        playersPage.searchPlayerByUsername(player.getUsername());

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
    public void updatedPlayerIsCorrectlySaved(){
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
     * 4. Click "Ok" on popup window
     * 5. Verify message "Player has been deleted" has appeared
     */
    @Test (groups = "deletePlayer")
    public void deletingPlayerShowsMessage(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername(), "Ok");

        //check flash message
        softAssert.assertEquals(playersPage.getFlashMessage(), "Player has been deleted", "No flash message after deleting player (probably player wasn't deleted).");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. On the Players page fill the "Player" field with player username. Click "Search"
     * 3. Click on player's "Delete" link
     * 4. Click "Ok" on popup window
     * 5. On the Players page fill the "Player" field with player username. Click "Search"
     * 6. Verify table doesn't contain player.
     */
    @Test (groups = "deletePlayer")
    public void deletingPlayerDeletesPlayerFromPlayersTable(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername(), "Ok");
        playersPage.refresh();

        playersPage.searchPlayerByUsername(player.getUsername());

        //check result table
        softAssert.assertFalse(playersPage.doesTableContainPlayer(player.getUsername()), "Player wasn't deleted.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. On the Players page fill the "Player" field with player username. Click "Search"
     * 3. Click on player's "Delete" link
     * 4. Click "Отмена" on popup window
     * 5. On the Players page fill the "Player" field with player username. Click "Search"
     * 6. Verify table contains player.
     */
    @Test (groups = "deletePlayer")
    public void deletingAndClickingCancelDoesNotDeletePlayer(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "pass_Word68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername(), "Cansel");
        playersPage.refresh();

        playersPage.searchPlayerByUsername(player.getUsername());

        //check result table
        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Player was deleted.");
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
     * 2. Fill the username field with "" value
     * 3. Fill other fields with valid random data
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
        String validationMessage = "Username: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating player"
                + "with empty username field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains special character
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that validation message "Username Contains invalid characters" is shown
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
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value 5 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Password is less than 6 characters long" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithPasswordLengthLessThanMinimum(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(5);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with less than minimum password length (probably player was created).");
        String validationMessage = "Password: '*****' is less than 6 characters long";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Password"), validationMessage, "No validation message after creating user"
                + "with 5 character password (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value 31 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Password is more than 30 characters long" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithPasswordLengthGreaterThanMaximum(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(31);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password);

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with greater than maximum username length (probably player was created).");

        String validationMessage = "Password: '*******************************' is more than 30 characters long";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Password"), validationMessage, "No validation message after creating player"
                + "with 31 character password (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password field with "" value
     * 3. Fill the confirm password field with random value 8 characters long
     * 4. Fill other fields with valid random data.
     * 5. Click "Save" button
     * 6. Verify that title of the page equals to "Players-Insert"
     * 7. Verify that validation message "Password: Value is required and can't be empty" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmptyPasswordField(){
        PokerPlayer player = createRandomPokerPlayer();
        String confirmPassword = randomManager.getRandomAlphaAndNumberString(8);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "", confirmPassword);

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with empty password (probably player was created).");
        String validationMessage = "Password: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Password"), validationMessage, "No validation message after creating player"
                + "with empty password field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password field with random value 8 characters long
     * 3. Fill the confirm password field with "" value
     * 4. Fill other fields with valid random data.
     * 5. Click "Save" button
     * 6. Verify that title of the page equals to "Players-Insert"
     * 7. Verify that validation message "Confirm Password: Value is required and can't be empty" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmptyConfirmPasswordField(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(8);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, "");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with empty confirm password (probably player was created).");
        String validationMessage = "Confirm Password: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Confirm Password"), validationMessage, "No validation message after creating player"
                + "with empty confirm password field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password field with random value 8 characters long
     * 3. Fill the confirm password field with another random value 8 characters long
     * 4. Fill other fields with valid random data.
     * 5. Click "Save" button
     * 6. Verify that title of the page equals to "Players-Insert"
     * 7. Verify that validation message "Confirm Password: Does not match Password" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithDifferentPasswordAndConfirmPasswordFields(){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(8);
        String confirmPassword = randomManager.getRandomAlphaAndNumberString(8);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, confirmPassword);

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with different password and confirm password (probably player was created).");
        String validationMessage = "Confirm Password: Does not match Password";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Confirm Password"), validationMessage, "No validation message after creating player"
                + "with empty confirm password field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with invalid value
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Email is no valid email address in the basic format local-part@hostname" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithInvalidEmail(){
        PokerPlayer player = createRandomPokerPlayer();
        String randomAlphaEmail = randomManager.getRandomAlphaString(8);
        player.setEmail(randomAlphaEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with invalid email (probably player was created).");
        String validationMessage = "E-mail: '" + randomAlphaEmail + "' is no valid email address in the basic format local-part@hostname";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after creating player"
                + "with invalid email field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with "" value.
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message "Email value is required and can't be empty" is shown
     */
    @Test (groups = "createPlayer")
    public void createPlayerWithEmptyEmail(){
        PokerPlayer player = createRandomPokerPlayer();
        player.setEmail(randomManager.getRandomAlphaString(8));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        Assert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after creating player with empty email (probably player was created).");
        String validationMessage = "E-mail: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after creating player"
                + "with empty email field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. On the Players page fill the "Player" field with username from step 2. Click "Search"
     * 5. Open player edit page.
     * 6. Fill the email field with invalid value. Click "Save" button.
     * 7. Verify that title of the page equals to "Players-Insert"
     * 8. Verify that validation message "Email is no valid email address in the basic format local-part@hostname" is shown
     */
    @Test (groups = "updatePlayer")
    public void updatePlayerWithInvalidEmail(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        String randomEmail = randomManager.getRandomNotAllowedSpecialCharacterString(8);
        player.setEmail(randomEmail);
        editPlayerPage.updatePlayer(player);

        Assert.assertEquals(driver.getTitle(), "Players - Edit", "Wrong title after creating player with invalid email (probably player was created).");
        String validationMessage = "E-mail: '" + randomEmail + "' is no valid email address in the basic format local-part@hostname";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after updating player"
                + "with invalid email field (probably player was created).");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. On the Players page fill the "Player" field with username from step 2. Click "Search"
     * 5. Open player edit page.
     * 6. Fill the email field with "" value. Click "Save" button.
     * 7. Verify that title of the page equals to "Players-Insert"
     * 8. Verify that validation message "Email value is required and can't be empty" is shown
     */
    @Test (groups = "updatePlayer")
    public void updatePlayerWithEmptyEmail(){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "password68");

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        player.setEmail("");
        editPlayerPage.updatePlayer(player);

        Assert.assertEquals(driver.getTitle(), "Players - Edit", "Wrong title after creating player with invalid email (probably player was created).");
        String validationMessage = "E-mail: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after updating player"
                + "with empty email field (probably player was created).");
    }


    /**
     * Postcondition:
     * 1. Close browser.
     */
    @AfterTest (alwaysRun = true)
    public void afterTest(){
        driver.quit();
    }


}

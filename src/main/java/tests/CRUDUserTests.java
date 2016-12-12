package tests;

import entities.PokerPlayer;
import helpers.RandomManager;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.InsertOrEditPlayerPage;
import pages.LoginPage;
import pages.PlayersPage;

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
     * 1. Open Player-Insert page.
     * 2. Fill the fields with valid random data.
     * 3. Click "Save" button
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Player-Edit page URL
     */
    @Parameters({"playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerRedirectsToPlayersPage(String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player.");
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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createdPlayerInsertsInPlayersTable(String password){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createdPlayerIsCorrectlySaved(String password){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = editPlayerPage.readPokerPlayerFromPage();

        softAssert.assertEquals(actualPlayer, player, "Wrong data after creating player.");
        softAssert.assertAll();             //never in afterMethod!
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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createdPlayerIsCorrectlyShowsInViewPage(String password){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage viewPlayerPage = playersPage.openViewPlayerPage(player.getUsername());
        PokerPlayer actualPlayer = viewPlayerPage.readPokerPlayerFromPage();
        playersPage.closeViewPlayerPage();

        softAssert.assertEquals(actualPlayer, player, "Wrong data in view page after creating player.");
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] usernameLengthBounds(){
        return new Object[][]{
                {3, "Players", "pass_Word68", "Wrong title after creating player with minimum username length."},
                {12, "Players", "pass_Word68", "Wrong title after creating player with maximum username length."}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 3 and 12 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = {"createPlayer", "positiveTests"}, dataProvider = "usernameLengthBounds")
    public void createPlayerWithBoundaryUsernameLength(int length, String expectedTitle, String password, String message){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaAndNumberString(length));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, message);
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains alpha characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "usernameLength", "playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithUsernameContainsAlphaCharactersOnly(int usernameLength, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaString(usernameLength));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with username that contains alpha characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "usernameLength", "playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithUsernameContainsNumbersOnly(int usernameLength, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomNumberString(usernameLength));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with username that contains numbers only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains special characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "usernameLength", "playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithUsernameContainsAllowedSpecialCharactersOnly(int usernameLength, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAllowedSpecialCharacterString(usernameLength));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with username that contains allowed special characters only.");
    }

    @DataProvider
    public Object[][] passwordLengthBounds(){
        return new Object[][]{
                {6, "Players", "Wrong title after creating player with minimum password length."},
                {30, "Players", "Wrong title after creating player with maximum password length."}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value 6 and 30 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Test (groups = {"createPlayer", "positiveTests"}, dataProvider = "passwordLengthBounds")
    public void createPlayerWithBoundaryPasswordLength(int length, String expectedTitle, String message){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, message);
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields  with random value that contains alpha characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "passwordLength", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithPasswordContainsAlphaCharactersOnly(int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with password that contains alpha characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "passwordLength", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithPasswordContainsNumbersOnly(int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomNumberString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with password that contains numbers only.");
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
    @Parameters({ "passwordLength", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithPasswordContainsSpecialCharactersOnly(int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAllSpecialCharacterString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        softAssert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with password that contains special characters only.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with random value that contains alpha characters only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "emailLocalLength", "playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithEmailContainsAlphaCharacters(int emailLocalLength, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String randomAlphaEmail = randomManager.getRandomEmail(emailLocalLength, RandomManager.alphaMixedCaseCharacters, RandomManager.alphaMixedCaseCharacters);
        player.setEmail(randomAlphaEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with " + randomAlphaEmail + " email that contains alpha characters only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with random value that contains numbers only
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "emailLocalLength", "playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithEmailContainsNumbers(int emailLocalLength, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String randomNumberEmail = randomManager.getRandomEmail(emailLocalLength, RandomManager.numbers, RandomManager.numbers);
        player.setEmail(randomNumberEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with " + randomNumberEmail + " email that contains numbers only.");
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the email field with random value that contains special characters in local part
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players"
     */
    @Parameters({ "emailLocalLength", "playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"createPlayer", "positiveTests"})
    public void createPlayerWithEmailContainsSpecialCharactersInLocalPart(int emailLocalLength, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String randomSpecialCharactersEmail = randomManager.getRandomEmail(emailLocalLength, RandomManager.emailSpecialCharacters, RandomManager.alphaMixedCaseCharacters);
        player.setEmail(randomSpecialCharactersEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with " + randomSpecialCharactersEmail + " email that contains special characters in local part.");
    }

    /**
     * Steps to reproduce:
     * 1. Create player
     * 2. Open player edit window
     * 3. Update player with new random data
     * 4. Verify that title of the page equals to "Players"
     * 5. Verify that the URL not equals to Player-Edit page URL
     */
    @Parameters({"playerPasswordParameter", "positiveExpectedTitle"})
    @Test (groups = {"updatePlayer", "positiveTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void updatingPlayerRedirectsToPlayersPage(String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        updatePlayerData(player);
        editPlayerPage.updatePlayer(player);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after updating player.");
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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"updatePlayer", "positiveTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void updatingPlayerKeepsPlayerInPlayersTable(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"updatePlayer", "positiveTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void updatedPlayerIsCorrectlySaved(String password){
        //create player
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"deletePlayer", "positiveTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void deletingPlayerShowsMessage(String password){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername(), "Ok");

        //check flash message
        softAssert.assertEquals(playersPage.getFlashMessage(), "Player has been deleted", "No flash message after deleting player (probably player wasn't deleted).");
        softAssert.assertAll();
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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"deletePlayer", "positiveTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void deletingPlayerDeletesPlayerFromPlayersTable(String password){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

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
    @Parameters({"playerPasswordParameter"})
    @Test (groups = {"deletePlayer", "positiveTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void deletingAndClickingCancelDoesNotDeletePlayer(String password){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);
        playersPage.deletePlayer(player.getUsername(), "Cancel");
        playersPage.refresh();

        playersPage.searchPlayerByUsername(player.getUsername());

        //check result table
        softAssert.assertTrue(playersPage.doesTableContainPlayer(player.getUsername()), "Player was deleted.");
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] usernameNegativeLength(){
        return new Object[][]{
                {2, "Players - Insert", "pass_Word68",
                        "Wrong title after creating player with less than minimum username length.",
                        "is less than 3 characters long"},
                {13, "Players - Insert",
                        "pass_Word68", "Wrong title after creating player with maximum username length.",
                        "is more than 12 characters long"}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value 2 and 13 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message is shown
     */
    @Test (groups = {"createPlayer", "negativeTests"}, dataProvider = "usernameNegativeLength")
    public void createPlayerWithInvalidUsernameLength(int length, String expectedTitle, String password, String informationMessage, String validationMessagePart){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomAlphaAndNumberString(length));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, informationMessage);

        String validationMessage = "Username: '" + player.getUsername() + "' " + validationMessagePart;
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "Wrong validation message after creating user"
            + "with invalid username length (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"playerPasswordParameter", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithEmptyUsernameField(String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername("");

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with empty username (probably player was created).");
        String validationMessage = "Username: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating player"
                + "with empty username field (probably player was created).");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the username field with random value that contains special character
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that validation message "Username Contains invalid characters" is shown
     */
    @Parameters({"playerPasswordParameter", "usernameLength", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithUsernameContainsNotAllowedCharacters(String password, int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        player.setUsername(randomManager.getRandomNotAllowedSpecialCharacterString(length));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with username that contains special characters.");
        String validationMessage = "Username: Contains invalid characters. Allowed ones are: digits (0-9), letters (A-Z and a-z), underscores, hyphens and periods";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Username"), validationMessage, "No validation message after creating player"
                + "with username contains not allowed characters (probably player was created).");
        softAssert.assertAll();
    }


    @DataProvider
    public Object[][] passwordNegativeLength(){
        return new Object[][]{
                {5, "Players - Insert",
                        "Wrong title after creating player with less than minimum password length (probably player was created).",
                        "Password: '*****' is less than 6 characters long", },
                {31, "Players - Insert",
                        "Wrong title after creating player with maximum password length.",
                        "Password: '*******************************' is more than 30 characters long"}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Open Player-Insert page.
     * 2. Fill the password and confirm password fields with random value 5 and 31 characters long
     * 3. Fill other fields with valid random data.
     * 4. Click "Save" button
     * 5. Verify that title of the page equals to "Players-Insert"
     * 6. Verify that validation message is shown
     */
    @Test (groups = {"createPlayer", "negativeTests"}, dataProvider = "passwordNegativeLength")
    public void createPlayerWithInvalidPasswordLength(int length, String title, String informationMessage, String validationMessage){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), title, informationMessage);
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Password"), validationMessage, "Wrong validation message after creating user"
                + "with invalid password length (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"passwordLength", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithEmptyPasswordField(int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String confirmPassword = randomManager.getRandomAlphaAndNumberString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, "", confirmPassword);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with empty password (probably player was created).");
        String validationMessage = "Password: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Password"), validationMessage, "No validation message after creating player"
                + "with empty password field (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"passwordLength", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithEmptyConfirmPasswordField(int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, "");

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with empty confirm password (probably player was created).");
        String validationMessage = "Confirm Password: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Confirm Password"), validationMessage, "Wrong validation message after creating player"
                + "with empty confirm password field (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"passwordLength", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithDifferentPasswordAndConfirmPasswordFields(int length, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String password = randomManager.getRandomAlphaAndNumberString(length);
        String confirmPassword = randomManager.getRandomAlphaAndNumberString(length);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, confirmPassword);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with different password and confirm password (probably player was created).");
        String validationMessage = "Confirm Password: Does not match Password";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("Confirm Password"), validationMessage, "Wrong validation message after creating player"
                + " with empty confirm password field (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"emailLocalLength", "playerPasswordParameter", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithInvalidEmail(int length, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        String randomAlphaEmail = randomManager.getRandomAlphaString(length);
        player.setEmail(randomAlphaEmail);

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with invalid email (probably player was created).");
        String validationMessage = "E-mail: '" + randomAlphaEmail + "' is no valid email address in the basic format local-part@hostname";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after creating player"
                + "with invalid email field (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"playerPasswordParameter", "insertNegativeExpectedTitle"})
    @Test (groups = {"createPlayer", "negativeTests"})
    public void createPlayerWithEmptyEmail(String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();
        player.setEmail("");
        //player.setEmail(randomManager.getRandomAlphaString(length));

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with empty email (probably player was created).");
        String validationMessage = "E-mail: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after creating player"
                + "with empty email field (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"emailLocalLength", "playerPasswordParameter", "editNegativeExpectedTitle"})
    @Test (groups = {"updatePlayer", "negativeTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void updatePlayerWithInvalidEmail(int length, String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        String randomEmail = randomManager.getRandomNotAllowedSpecialCharacterString(length);
        player.setEmail(randomEmail);
        editPlayerPage.updatePlayer(player);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with invalid email (probably player was created).");
        String validationMessage = "E-mail: '" + randomEmail + "' is no valid email address in the basic format local-part@hostname";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after updating player"
                + "with invalid email field (probably player was created).");
        softAssert.assertAll();
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
    @Parameters({"playerPasswordParameter", "editNegativeExpectedTitle"})
    @Test (groups = {"updatePlayer", "negativeTests"}, dependsOnGroups = "createPlayer", alwaysRun = true)
    public void updatePlayerWithEmptyEmail(String password, String expectedTitle){
        PokerPlayer player = createRandomPokerPlayer();

        InsertOrEditPlayerPage insertPlayerPage = InsertOrEditPlayerPage.openInsertPlayerPage(driver);
        insertPlayerPage.createPlayer(player, password, password);

        PlayersPage playersPage = PlayersPage.openPlayersPage(driver);

        InsertOrEditPlayerPage editPlayerPage = playersPage.openEditPlayerPage(player.getUsername());
        player.setEmail("");
        editPlayerPage.updatePlayer(player);

        Assert.assertEquals(driver.getTitle(), expectedTitle, "Wrong title after creating player with invalid email (probably player was created).");
        String validationMessage = "E-mail: Value is required and can't be empty";
        softAssert.assertEquals(insertPlayerPage.getFieldValidationMessage("E-mail"), validationMessage, "No validation message after updating player"
                + "with empty email field (probably player was created).");
        softAssert.assertAll();
    }
}

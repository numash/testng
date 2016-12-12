package tests;

import entities.PokerPlayer;
import helpers.RandomManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

/**
 * Created by numash on 04.12.2016.
 */
public class BaseTests {
    protected static RandomManager randomManager;
    protected static SoftAssert softAssert;

    //declare global driver var
    protected static WebDriver driver;
    /**
     * Preconditions:
     * 1. Open browser
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        randomManager = new RandomManager();
    }

    /**
     * Precondition:
     * 1. Create new instance of SoftAssert
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(){
        softAssert = new SoftAssert();
    }

    //fills poker player fields with random data
    protected PokerPlayer createRandomPokerPlayer() {

        return new PokerPlayer(
                "user68_" + randomManager.getRandomAlphaAndNumberString(5),
                "user68_" + randomManager.getRandomAlphaAndNumberString(5) + "@gmail.com",
                "first" + randomManager.getRandomAlphaAndNumberString(5),
                "last" + randomManager.getRandomAlphaAndNumberString(5),
                "City." + randomManager.getRandomAlphaString(5),
                "UKRAINE",
                "Address68, " + randomManager.getRandomAlphaAndNumberString(5),
                "+312345678, 890",
                "Male",
                //"10-10-1990");
                randomManager.getRandomDate());
    }

    protected void updatePlayerData(PokerPlayer player){
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



    /**
     * Postcondition:
     * 1. Close browser.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite(){
        driver.quit();
    }
}
